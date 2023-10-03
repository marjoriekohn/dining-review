package org.marjoriekohn.diningreview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.io.Serializable;

/**
 * UserDTO serves as a Data Transfer Object for transporting user information
 * between client and server. It encapsulates data such as username, city, state,
 * and allergy information. The class also includes validation rules for several fields.
 * <p>
 * Note that this class is serialized and can be used to map objects to JSON and vice versa using serialization mechanisms.
 * </p>
 *
 * @see org.marjoriekohn.diningreview.entity.User
 * @see org.marjoriekohn.diningreview.service.UserService
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO implements Serializable {
	
	/**
	 * The username of the user.
	 * Username must be between 8 and 15 characters long.
	 */
	@Size(message = "Username must be 8-15 characters long", min = 8, max = 15)
	private String username;
	
	/**
	 * The city where the user resides.
	 */
	private String city;
	
	/**
	 * The state where the user resides.
	 */
	private String state;
	
	/**
	 * The user's zip code. It must match the regular expression pattern for valid zip codes.
	 * Valid zip codes are 5 digits, optionally followed by a hyphen and 4 more digits.
	 */
	@Pattern(message = "Invalid Zip Code", regexp = "^[0-9]{5}(?:-[0-9]{4})?$")
	private String zipcode;
	
	/**
	 * A boolean indicating whether the user has peanut allergies.
	 * A true value indicates the user has peanut allergies.
	 */
	private Boolean hasPeanutAllergies;
	
	/**
	 * A boolean indicating whether the user has egg allergies.
	 * A true value indicates the user has egg allergies.
	 */
	private Boolean hasEggAllergies;
	
	/**
	 * A boolean indicating whether the user has dairy allergies.
	 * A true value indicates the user has dairy allergies.
	 */
	private Boolean hasDairyAllergies;
	
	/**
	 * Default constructor for {@code UserDTO}.
	 */
	public UserDTO() {
	}
}