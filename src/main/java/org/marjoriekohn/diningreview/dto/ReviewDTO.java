package org.marjoriekohn.diningreview.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import java.io.Serializable;

/**
 * ReviewDTO serves as a Data Transfer Object for transporting review information
 * between client and server. It encapsulates data such as review ID, username,
 * restaurant ID, and various allergy scores. The class also includes validation
 * rules for several fields.
 *
 * <p>
 * Note that this class is serialized and can be used to map objects to JSON and vice versa
 * using serialization mechanisms.
 * </p>
 *
 * @see org.marjoriekohn.diningreview.entity.Review
 * @see org.marjoriekohn.diningreview.service.ReviewService
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDTO implements Serializable {
	
	/**
	 * The unique identifier of the review.
	 */
	private Long id;
	
	/**
	 * The username of the user who created the review.
	 * This field must not be {@code null}.
	 */
	@NotNull
	private String username;
	
	/**
	 * The unique identifier of the restaurant being reviewed.
	 * This field must not be {@code null}.
	 */
	@NotNull
	private Long restaurantId;
	
	/**
	 * The peanut allergy score assigned by the user in the review.
	 * Score should be between 1 and 5, inclusive.
	 */
	@Range(min = 1, max = 5)
	private Integer peanutScore;
	
	/**
	 * The egg allergy score assigned by the user in the review.
	 * Score should be between 1 and 5, inclusive.
	 */
	@Range(min = 1, max = 5)
	private Integer eggScore;
	
	/**
	 * The dairy allergy score assigned by the user in the review.
	 * Score should be between 1 and 5, inclusive.
	 */
	@Range(min = 1, max = 5)
	private Integer dairyScore;
	
	/**
	 * Additional comments provided by the user in the review.
	 */
	private String comments;
	
	/**
	 * The status of the review represented by a string.
	 * It is automatically set to "PENDING" when a new review is created.
	 * An admin can update the status to "APPROVED" or "REJECTED".
	 */
	private String status;
	
	/**
	 * Default constructor for {@code ReviewDTO}.
	 */
	public ReviewDTO() {
	}
}