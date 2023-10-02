package org.marjoriekohn.diningreview.exception;

/**
 * Thrown to indicate that a restaurant could not be found based on the provided
 * criteria. This exception extends {@link BaseException} and can be thrown in various
 * contexts, such as when searching for a restaurant by ID, or when applying more complex
 * filtering criteria like zipcode and allergy information.
 *
 * @see BaseException
 * @see org.marjoriekohn.diningreview.repository.RestaurantRepository
 * @see org.marjoriekohn.diningreview.service.RestaurantService
 */
public class RestaurantNotFoundException extends BaseException {
	/**
	 * Constructs a RestaurantNotFoundException when a restaurant with a given ID is not found.
	 *
	 * @param restaurantId the ID of the restaurant that could not be found, required
	 */
	public RestaurantNotFoundException(Long restaurantId) {
		super("Restaurant with ID [" + restaurantId + "] not found.");
	}
	
	/**
	 * Constructs a RestaurantNotFoundException when no restaurants meet the given zipcode
	 * and allergy criteria.
	 *
	 * @param zipCode the zipcode used for filtering, required
	 * @param allergy the allergy criteria used for filtering, required
	 */
	public RestaurantNotFoundException(String zipCode, String allergy) {
		super("No restaurants found with zipcode [" + zipCode + "] and allergy [" + allergy + "].");
	}
	
	/**
	 * Constructs a RestaurantNotFoundException with a detailed custom message.
	 *
	 * @param detailMessage the detailed message explaining the reason for the exception, required
	 */
	public RestaurantNotFoundException(String detailMessage) {
		super(detailMessage);
	}
}
