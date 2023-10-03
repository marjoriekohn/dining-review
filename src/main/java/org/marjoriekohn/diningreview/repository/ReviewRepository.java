package org.marjoriekohn.diningreview.repository;
import org.marjoriekohn.diningreview.entity.Restaurant;
import org.marjoriekohn.diningreview.entity.Review;
import org.marjoriekohn.diningreview.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Manages database interactions for Review entities.
 * The ReviewRepository interface is a Spring Data JPA repository that provides a set of methods
 * for performing database operations on Review entities. It supports fetching reviews based on
 * various criteria such as approval status, associated restaurant, or a combination of both.
 * This interface is crucial for filtering and managing reviews in the system.
 *
 * @see org.marjoriekohn.diningreview.entity.Review
 * @see org.marjoriekohn.diningreview.service.ReviewService
 * @see org.marjoriekohn.diningreview.controller.ReviewController
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	/**
	 * Fetches reviews based on approval status.
	 * This method retrieves a list of Review entities that match a given approval status
	 * from the database. The list may be empty if no reviews with the specified status are found.
	 *
	 * @param status the approval status for filtering reviews, required
	 * @return a list of Review entities that have the specified approval status
	 *
	 * @see org.marjoriekohn.diningreview.entity.Review.Status
	 */
	List<Review> findByStatus(Review.Status status);
	
	/**
	 * Fetches reviews for a specific restaurant with a given approval status.
	 * This method retrieves a list of Review entities associated with a particular
	 * restaurant and having a specific approval status. The list may be empty if no
	 * reviews with the specified criteria are found.
	 *
	 * @param restaurant the Restaurant entity for filtering reviews, required
	 * @param status the approval status for filtering reviews, required
	 * @return a list of Review entities meeting the specified criteria
	 *
	 * @see org.marjoriekohn.diningreview.entity.Restaurant
	 * @see org.marjoriekohn.diningreview.entity.Review.Status
	 */
	List<Review> findByRestaurantAndStatus(Restaurant restaurant, Review.Status status);
	
	/**
	 * Retrieves reviews submitted by a specific user for a particular restaurant.
	 * This method fetches a list of Review entities from the database that were submitted
	 * by a given user for a specific restaurant. The list may be empty if no matching reviews are found.
	 *
	 * @param user the User entity who submitted the reviews, required
	 * @param restaurant the Restaurant entity for which the reviews were submitted, required
	 * @return a list of Review entities that meet the given criteria
	 *
	 * @see org.marjoriekohn.diningreview.entity.User
	 * @see org.marjoriekohn.diningreview.entity.Restaurant
	 */
	List<Review> findByUserAndRestaurant(User user, Restaurant restaurant);
}