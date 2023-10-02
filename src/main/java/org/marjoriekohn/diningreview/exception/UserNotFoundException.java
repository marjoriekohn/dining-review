package org.marjoriekohn.diningreview.exception;

/**
 * Thrown to indicate that a user is not found in the system.
 * This exception extends {@link BaseException} and is specifically used when an
 * operation attempts to access a user by their username and the user does not exist.
 *
 * @see BaseException
 * @see org.marjoriekohn.diningreview.repository.UserRepository
 * @see org.marjoriekohn.diningreview.service.UserService
 */
public class UserNotFoundException extends BaseException {
	/**
	 * Constructs a UserNotFoundException when a user is not found based on their username.
	 *
	 * @param username the username that could not be found, required
	 */
	public UserNotFoundException(String username) {
		super("User [" + username + "] not found.");
	}
}