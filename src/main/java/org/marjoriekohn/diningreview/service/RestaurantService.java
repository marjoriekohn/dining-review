package org.marjoriekohn.diningreview.service;

import org.marjoriekohn.diningreview.dto.ReviewDTO;
import org.marjoriekohn.diningreview.entity.Restaurant;
import org.marjoriekohn.diningreview.exception.DuplicateRestaurantException;
import org.marjoriekohn.diningreview.exception.RestaurantNotFoundException;
import org.marjoriekohn.diningreview.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Manages operations related to the Restaurant entity.
 * This class provides a set of methods for creating, reading, and updating restaurants.
 * It interacts with the RestaurantRepository to perform these operations.
 *
 * @see org.marjoriekohn.diningreview.repository.RestaurantRepository
 * @see org.marjoriekohn.diningreview.entity.Restaurant
 */
@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ReviewService reviewService;
    
    @Autowired
    RestaurantService (RestaurantRepository restaurantRepository, ReviewService reviewService) {
        this.restaurantRepository = restaurantRepository;
        this.reviewService = reviewService;
    }
    
    /**
     * Adds a new restaurant to the database.
     * This method takes a Restaurant containing restaurant details, checks
     * for duplicates based on name and zipcode, and then saves the restaurant to the database.
     *
     * @param restaurant the object containing restaurant details, required
     * @return the created restaurant object
     * @throws DuplicateRestaurantException if a restaurant with the same name and zipcode already exists
     *
     */
    @Transactional
    public Restaurant createRestaurant(Restaurant restaurant) {
        List<Restaurant> existingRestaurants = restaurantRepository.findByNameAndZipCode(restaurant.getName(), restaurant.getAddress().getZipCode());
        if (!existingRestaurants.isEmpty()) {
            throw new DuplicateRestaurantException(restaurant.getName(), restaurant.getAddress().getZipCode());
        }
	    return restaurantRepository.save(restaurant);
    }
    
    /**
     * Retrieves a restaurant by its unique identifier.
     * This method takes a restaurant ID, fetches the corresponding restaurant from the database,
     * and returns it as a Restaurant object.
     *
     * @param restaurantId the unique ID of the restaurant, required
     * @return a restaurant object
     * @throws RestaurantNotFoundException if no restaurant with the provided ID exists
     *
     */
    public Restaurant findById(Long restaurantId) {
	    return restaurantRepository.findById(restaurantId)
          .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
    }
    
    public Double getPeanutScore(Long restaurantId) {
        List<ReviewDTO> approvedReviews = reviewService.getApprovedReviews(restaurantId);
        return approvedReviews.stream()
          .mapToDouble(ReviewDTO::getPeanutScore)
          .average()
          .orElse(0.0);
    }
    
    public Double getEggScore(Long restaurantId) {
        List<ReviewDTO> approvedReviews = reviewService.getApprovedReviews(restaurantId);
        return approvedReviews.stream()
          .mapToDouble(ReviewDTO::getEggScore)
          .average()
          .orElse(0.0);
    }
    
    public Double getDairyScore(Long restaurantId) {
        List<ReviewDTO> approvedReviews = reviewService.getApprovedReviews(restaurantId);
        return approvedReviews.stream()
          .mapToDouble(ReviewDTO::getDairyScore)
          .average()
          .orElse(0.0);
    }
    
    public Double getOverallScore(Long restaurantId) {
        return (getPeanutScore(restaurantId)
          + getEggScore(restaurantId)
          + getDairyScore(restaurantId)) / 3.0;
    }
    
    /**
     * Searches for restaurants based on zipcode and allergy criteria.
     * This method finds restaurants within a given zipcode that cater to specific allergies,
     * returning them as a list of Restaurants.
     *
     * @param zipcode the zipcode used for filtering the restaurants, required
     * @param allergy the allergy criteria for filtering, required
     * @return a list of restaurants matching the search criteria
     * @throws RestaurantNotFoundException if no restaurants match the provided criteria
     * @throws IllegalArgumentException if an unrecognized allergy is provided
     *
     */
    public List<Restaurant> searchByZipCodeAndAllergy(String zipcode, String allergy) {
        List<Restaurant> restaurants = restaurantRepository.findByZipCode(zipcode);
        if (restaurants.isEmpty()) {
            throw new RestaurantNotFoundException(zipcode, allergy);
        }
        restaurants.sort((restaurant1, restaurant2) -> switch (allergy) {
		        case "peanut" ->
			        Double.compare(getPeanutScore(restaurant2.getRestaurantId()), getPeanutScore(restaurant1.getRestaurantId()));
		        case "egg" ->
			        Double.compare(getEggScore(restaurant2.getRestaurantId()), getEggScore(restaurant1.getRestaurantId()));
		        case "dairy" ->
			        Double.compare(getDairyScore(restaurant2.getRestaurantId()), getDairyScore(restaurant1.getRestaurantId()));
		        case "overall" ->
			        Double.compare(getOverallScore(restaurant2.getRestaurantId()), getOverallScore(restaurant1.getRestaurantId()));
		        default -> throw new IllegalArgumentException("Unrecognized allergy: " + allergy);
	        });
        return restaurants;
    }
}