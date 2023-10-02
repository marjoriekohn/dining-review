package org.marjoriekohn.diningreview.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Represents an error response returned from the API.
 * The ErrorResponse class encapsulates information about an API error, including
 * a timestamp, a message detailing the error, and additional details for debugging.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
	private LocalDateTime timestamp;
	private String message;
	private String details;
}
