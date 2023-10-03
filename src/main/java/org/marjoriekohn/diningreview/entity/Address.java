package org.marjoriekohn.diningreview.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents an embeddable Address component within the Restaurant entity.
 * The Address class is used to capture the street, city, state, and zip code information
 * of a restaurant and is marked as embeddable for JPA.
 *
 * @see org.marjoriekohn.diningreview.entity.Restaurant
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Address {
	@Column(name="street")
	private String street;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="zipcode")
	private String zipCode;
	
	// TODO: Add validation for street, city, state, and zip code
	// TODO: Use explicit constructor
}