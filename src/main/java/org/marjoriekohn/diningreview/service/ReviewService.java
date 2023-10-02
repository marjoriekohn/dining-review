package org.marjoriekohn.diningreview.service;

import org.marjoriekohn.diningreview.dto.ReviewDTO;
import org.marjoriekohn.diningreview.dto.AdminReviewAction;
import org.marjoriekohn.diningreview.entity.Restaurant;
import org.marjoriekohn.diningreview.entity.Review;
import org.marjoriekohn.diningreview.entity.User;
import org.marjoriekohn.diningreview.exception.RestaurantNotFoundException;
import org.marjoriekohn.diningreview.exception.ReviewNotFoundException;
import org.marjoriekohn.diningreview.exception.UnauthorizedAccessException;
import org.marjoriekohn.diningreview.exception.UserNotFoundException;
import org.marjoriekohn.diningreview.repository.RestaurantRepository;
import org.marjoriekohn.diningreview.repository.ReviewRepository;
import org.marjoriekohn.diningreview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages operations related to Review entities.
 * This class provides a set of methods for creating, reading, and updating reviews.
 * It interacts with the ReviewRepository, UserRepository, and RestaurantRepository
 * to perform these operations. Client-facing methods use ReviewDTO to ensure
 * separation of concerns between the database entity and the client-side data structure.
 *
 * @see ReviewRepository
 * @see UserRepository
 * @see RestaurantRepository
 * @see ReviewDTO
 * @see Review
 */
@Service
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    
    @Autowired
    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }
    
    /**
     * Creates a new review for a specific restaurant by a user.
     * This method utilizes a ReviewDTO to collect review details, validates the
     * associated user and restaurant, and then saves the review to the database.
     * Note: a user cannot review the same restaurant multiple times.
     *
     * @param reviewDTO the Data transfer object containing review details, required
     * @return the newly created review's DTO
     * @throws UserNotFoundException if the user associated with the review is not found
     * @throws RestaurantNotFoundException if the restaurant associated with the review is not found
     * @throws UnauthorizedAccessException if the user has already reviewed the restaurant
     *
     * @see #convertDTOToEntity(ReviewDTO)
     * @see #convertEntityToDTO(Review)
     * @see UserRepository#findByUsername(String)
     * @see ReviewRepository#findByUserAndRestaurant(User, Restaurant)
     */
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        User user = userRepository.findByUsername(reviewDTO.getUsername())
          .orElseThrow(() -> new UserNotFoundException(reviewDTO.getUsername()));
        
        Restaurant restaurant = restaurantRepository.findById(reviewDTO.getRestaurantId())
          .orElseThrow(() -> new RestaurantNotFoundException(reviewDTO.getRestaurantId()));
        
        if (!reviewRepository.findByUserAndRestaurant(user, restaurant).isEmpty()) {
            throw new UnauthorizedAccessException(user.getUsername(), restaurant.getName());
        }
        Review review = convertDTOToEntity(reviewDTO);
        reviewRepository.save(review);
        return convertEntityToDTO(review);
    }
    
    /**
     * Retrieves a list of all reviews that are pending approval.
     * This method fetches reviews with a 'PENDING' status from the database and
     * returns them as a list of ReviewDTOs. An exception is thrown if no pending reviews are found.
     *
     * @return a list of ReviewDTOs representing pending reviews
     * @throws ReviewNotFoundException if no reviews are pending approval
     *
     * @see #convertEntityToDTO(Review)
     * @see ReviewRepository#findByStatus(Review.Status)
     */
    public List<ReviewDTO> getPendingReviews() {
        List<Review> pendingReviews = reviewRepository.findByStatus(Review.Status.PENDING);
        if (pendingReviews.isEmpty()) {
            throw new ReviewNotFoundException(Review.Status.PENDING);
        }
        return pendingReviews.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }
    
    /**
     * Retrieves a list of all approved reviews for a specific restaurant.
     * Fetches reviews with an 'APPROVED' status for a given restaurant and returns
     * them as a list of ReviewDTOs. Exceptions are thrown if the restaurant or approved reviews are not found.
     *
     * @param restaurantId the unique ID of the restaurant, required
     * @return a list of ReviewDTOs representing approved reviews
     * @throws RestaurantNotFoundException if no restaurant with the provided ID exists
     *
     * @see #convertEntityToDTO(Review)
     * @see ReviewRepository#findByRestaurantAndStatus(Restaurant, Review.Status)
     */
    public List<ReviewDTO> getApprovedReviews(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
          .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
        
        List<Review> approvedReviews = reviewRepository.findByRestaurantAndStatus(restaurant, Review.Status.APPROVED);
        
        return approvedReviews.stream()
          .map(this::convertEntityToDTO)
          .collect(Collectors.toList());
    }
    
    /**
     * Updates the approval status of a review.
     * Takes an AdminReviewAction object that contains the review ID and desired
     * action status, then updates the review's status to either 'APPROVED' or 'REJECTED'.
     *
     * @param adminReviewAction contains the review ID and the desired action status, required
     * @return the updated review's DTO
     * @throws ReviewNotFoundException if no review with the provided ID exists
     * @throws IllegalArgumentException if an invalid action status is provided
     *
     * @see #convertEntityToDTO(Review)
     */
    public ReviewDTO updateReviewStatus(AdminReviewAction adminReviewAction) {
        //TODO: add authentication and authorization so that only admins can update review status
        Review review = reviewRepository.findById(adminReviewAction.getReviewId())
          .orElseThrow(() -> new ReviewNotFoundException(adminReviewAction.getReviewId()));
        
        switch (adminReviewAction.getActionStatus()) {
            case APPROVED:
                review.setStatus(Review.Status.APPROVED);
                break;
            case REJECTED:
                review.setStatus(Review.Status.REJECTED);
                break;
            default:
                throw new IllegalArgumentException("Invalid review action provided: " + adminReviewAction.getActionStatus());
        }
        reviewRepository.save(review);
        return convertEntityToDTO(review);
    }
    
    /**
     * Transforms a Review entity into a ReviewDTO.
     * This method converts the fields of a given Review entity into a ReviewDTO,
     * facilitating the transfer of review data from the server to the client.
     * The transformation ensures that only necessary data is exposed to the client.
     *
     * @param review The Review entity obtained from the database. This parameter is required.
     * @return A ReviewDTO containing the details of the given Review entity.
     *
     * @see #convertDTOToEntity(ReviewDTO)
     * @see Review
     * @see ReviewDTO
     */
    public ReviewDTO convertEntityToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setUsername(review.getUser().getUsername());
        reviewDTO.setRestaurantId(review.getRestaurant().getRestaurantId());
        reviewDTO.setPeanutScore(review.getPeanutScore());
        reviewDTO.setEggScore(review.getEggScore());
        reviewDTO.setDairyScore(review.getDairyScore());
        reviewDTO.setComments(review.getComments());
        reviewDTO.setStatus(review.getStatus().toString());
        return reviewDTO;
    }
    
    /**
     * Transforms a ReviewDTO into a Review entity.
     * This method converts the fields of a given ReviewDTO into a Review entity,
     * making it suitable for database operations. It associates the DTO fields
     * with the corresponding database entity fields, enabling the persistence of client-provided
     * review details to the database. The transformation does not allow the client to
     * directly modify the review ID or status.
     *
     * @param reviewDTO the Data Transfer Object containing review details from the client-side, required
     * @return a Review entity containing the details of the given ReviewDTO
     * @throws IllegalArgumentException if the provided ReviewDTO is invalid or if it references non-existing users or restaurants
     *
     * @see #convertEntityToDTO(Review)
     * @see Review
     * @see ReviewDTO
     */
    public Review convertDTOToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        User user = userRepository.findByUsername(reviewDTO.getUsername()).orElse(null);
        Restaurant restaurant = restaurantRepository.findById(reviewDTO.getRestaurantId()).orElse(null);
        review.setUser(user);
        review.setRestaurant(restaurant);
        review.setPeanutScore(reviewDTO.getPeanutScore());
        review.setEggScore(reviewDTO.getEggScore());
        review.setDairyScore(reviewDTO.getDairyScore());
        review.setComments(reviewDTO.getComments());
        review.setStatus(Review.Status.PENDING);
        return review;
    }
}
