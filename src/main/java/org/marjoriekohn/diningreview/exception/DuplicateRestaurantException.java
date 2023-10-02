package org.marjoriekohn.diningreview.exception;

/**
 * Represents an exception that is thrown when a duplicate restaurant is found.
 * This exception extends {@link BaseException} and is thrown when an attempt is
 * made to create a restaurant with a name and zip code that already exist in the system.
 *
 * @see BaseException
 */
public class DuplicateRestaurantException extends BaseException {
	/**
	 * Constructs a new DuplicateRestaurantException with a detailed message.
	 *
	 * @param name    the name of the restaurant that is already in use, required
	 * @param zipCode the zip code of the restaurant that is already in use, required
	 */
	public DuplicateRestaurantException(String name, String zipCode) {
		super("A restaurant with name [" + name + "] and zip code [" + zipCode + "] already exists.");
	}
}