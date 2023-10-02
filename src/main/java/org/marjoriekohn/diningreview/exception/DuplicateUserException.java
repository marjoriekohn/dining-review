package org.marjoriekohn.diningreview.exception;

/**
 * Represents an exception that is thrown when a duplicate user is found.
 * This exception extends {@link BaseException} and is thrown when an attempt is
 * made to create a user with a username that already exists in the system.
 *
 * @see BaseException
 */
public class DuplicateUserException extends BaseException {
	/**
	 * Constructs a new DuplicateUserException with a detailed message.
	 *
	 * @param username the username that is already in use, required
	 */
	public DuplicateUserException(String username) {
		super("User with username [" + username + "] already exists.");
	}
}
