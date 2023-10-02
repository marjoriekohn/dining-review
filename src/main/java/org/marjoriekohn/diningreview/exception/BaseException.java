package org.marjoriekohn.diningreview.exception;

/**
 * Serves as the base exception class for custom exceptions within the application.
 * This class extends the built-in RuntimeException and is intended to be subclassed
 * by other more specific exception types. It provides a simplified way to create
 * new exception types by only needing to pass a message to the constructor.
 */
public class BaseException extends RuntimeException {
	/**
	 * Constructs a new BaseException with a detailed message.
	 *
	 * @param message the message for the exception, required
	 */
	public BaseException(String message) {
		super(message);
	}
}
