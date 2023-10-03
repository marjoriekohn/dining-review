package org.marjoriekohn.diningreview.controller;

import org.marjoriekohn.diningreview.dto.ReviewDTO;
import org.marjoriekohn.diningreview.dto.AdminReviewAction;
import org.marjoriekohn.diningreview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Manages RESTful API endpoints for Review-related CRUD operations.
 * The ReviewController class maps to the {@code /reviews} URI and provides endpoints for
 * creating, updating, and querying reviews. It interacts with the ReviewService to
 * perform these operations, maintaining a clean separation of concerns. It is designed
 * to return appropriate HTTP status codes and messages for each operation.
 *
 * @see org.marjoriekohn.diningreview.service.ReviewService
 * @see org.marjoriekohn.diningreview.dto.ReviewDTO
 */
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    
    /**
     * Creates a new review.
     * This endpoint maps to a POST request at {@code /reviews/create} and is responsible for
     * creating a new review based on the provided ReviewDTO. Errors that occur during the
     * creation process are handled by the ReviewService.
     *
     * @param reviewDTO a Data Transfer Object containing review details, required
     * @return the review that was created with its associated information
     *
     * @see org.marjoriekohn.diningreview.service.ReviewService#createReview(ReviewDTO)
     */
    @PostMapping("/create")
    public ResponseEntity<?> createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }
    
    /**
     * Updates a review's status based on admin action.
     * This endpoint maps to a POST request at {@code /reviews/admin/update-status} and is designed for
     * admin users to update the status of a review. Errors that occur during the update process are
     * handled by the ReviewService.
     *
     * @param adminReviewAction a Data Transfer Object containing the admin action and the review ID, required
     * @return the review that was updated with its associated information
     *
     * @see org.marjoriekohn.diningreview.service.ReviewService#updateReviewStatus(AdminReviewAction)
     */
    @PostMapping("/admin/update-status")
    public ResponseEntity<?> updateReviewStatus(@RequestBody AdminReviewAction adminReviewAction) {
        ReviewDTO updatedReview = reviewService.updateReviewStatus(adminReviewAction);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }
    
    /**
     * Retrieves all reviews pending admin approval.
     * This endpoint maps to a GET request at {@code /reviews/admin/pending} and utilizes the ReviewService
     * to fetch all reviews that are pending approval. Errors that occur during the retrieval process are
     * handled by the ReviewService.

     * @return a list of reviews that have a PENDING status
     *
     * @see org.marjoriekohn.diningreview.service.ReviewService#getPendingReviews()
     */
    @GetMapping("/admin/pending")
    public ResponseEntity<?> getPendingReviews() {
        List<ReviewDTO> pendingReviews = reviewService.getPendingReviews();
        return new ResponseEntity<>(pendingReviews, HttpStatus.OK);
    }
    
    /**
     * Fetches all approved reviews for a specific restaurant.
     * This endpoint maps to a GET request at {@code /reviews/{restaurantId}/approved} and utilizes the
     * ReviewService to fetch all reviews that have been approved for a particular restaurant. Errors that
     * occur during the retrieval process are handled by the ReviewService.
     *
     * @param restaurantId the unique ID of the restaurant, required
     * @return a list of approved reviews for the specified restaurant
     *
     * @see org.marjoriekohn.diningreview.service.ReviewService#getApprovedReviews(Long)
     */
    @GetMapping("/{restaurantId}/approved")
    public ResponseEntity<?> getApprovedReviews(@PathVariable Long restaurantId) {
        List<ReviewDTO> approvedReviews = reviewService.getApprovedReviews(restaurantId);
        return new ResponseEntity<>(approvedReviews, HttpStatus.OK);
    }
}