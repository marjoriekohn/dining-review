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
 * @see org.marjoriekohn.diningreview.entity.Review
 * @see org.marjoriekohn.diningreview.service.ReviewService
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDTO implements Serializable {
	private Long id;
	@NotNull
	private String username;
	@NotNull
	private Long restaurantId;
	@Range(min = 1, max = 5)
	private Integer peanutScore;
	@Range(min = 1, max = 5)
	private Integer eggScore;
	@Range(min = 1, max = 5)
	private Integer dairyScore;
	private String comments;
	private String status;
	}