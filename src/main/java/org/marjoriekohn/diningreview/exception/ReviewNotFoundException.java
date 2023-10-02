package org.marjoriekohn.diningreview.exception;

import org.marjoriekohn.diningreview.entity.Review;

/**
 * Thrown to indicate that a review could not be found based on the provided criteria.
 * This exception extends {@link BaseException} and can be thrown in various contexts,
 * such as when searching for a review by ID or by its status.
 *
 * @see BaseException
 * @see org.marjoriekohn.diningreview.repository.ReviewRepository
 * @see org.marjoriekohn.diningreview.service.ReviewService
 */
public class ReviewNotFoundException extends BaseException {
	/**
	 * Constructs a ReviewNotFoundException when no reviews meet the given status criteria.
	 *
	 * @param status the status used for filtering, required
	 */
	public ReviewNotFoundException(Review.Status status) {
		super("No reviews found with " + status + " status.");
	}
	
	/**
	 * Constructs a ReviewNotFoundException when a review with a given ID is not found.
	 *
	 * @param reviewId the ID of the review that could not be found, required
	 */
	public ReviewNotFoundException(Long reviewId) {
		super("Review with ID [" + reviewId + "] not found.");
	}
}
