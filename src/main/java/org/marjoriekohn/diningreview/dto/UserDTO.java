package org.marjoriekohn.diningreview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.io.Serializable;

/**
 * UserDTO serves as a Data Transfer Object for transporting user information
 * between client and server. It encapsulates data such as username, city, state,
 * and allergy information. The class also includes validation rules for several fields.
 *
 * @see org.marjoriekohn.diningreview.entity.User
 * @see org.marjoriekohn.diningreview.service.UserService
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO implements Serializable {
	@Size(message = "Username must be 8-15 characters long", min = 8, max = 15)
	private String username;
	private String city;
	private String state;
	@Pattern(message = "Invalid Zip Code", regexp = "^[0-9]{5}(?:-[0-9]{4})?$")
	private String zipcode;
	private Boolean hasPeanutAllergies;
	private Boolean hasEggAllergies;
	private Boolean hasDairyAllergies;
}