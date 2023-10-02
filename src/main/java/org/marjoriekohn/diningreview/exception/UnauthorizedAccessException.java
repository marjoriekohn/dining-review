package org.marjoriekohn.diningreview.exception;

/**
 * Thrown to indicate unauthorized access or operations that a user is not permitted to perform.
 * This exception extends {@link BaseException} and is specifically used when a user attempts
 * to submit multiple reviews for the same restaurant.
 *
 * @see BaseException
 * @see org.marjoriekohn.diningreview.repository.UserRepository
 * @see org.marjoriekohn.diningreview.service.UserService
 */
public class UnauthorizedAccessException extends BaseException {
	/**
	 * Constructs an UnauthorizedAccessException when a user attempts to review a restaurant
	 * that they have already reviewed.
	 *
	 * @param username the username of the user attempting the unauthorized action, required
	 * @param restaurantId the ID of the restaurant being reviewed, required
	 */
	public UnauthorizedAccessException(String username, String restaurantId) {
		super("User [" + username + "] has already reviewed Restaurant [" + restaurantId + "].");
	}
}

