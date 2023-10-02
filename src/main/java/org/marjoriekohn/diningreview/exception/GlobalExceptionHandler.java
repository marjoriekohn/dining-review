package org.marjoriekohn.diningreview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
/**
 * Centralizes exception handling across all controllers in the application.
 * The GlobalExceptionHandler class provides a centralized mechanism for handling
 * various types of exceptions that may occur during the execution of the application.
 * It is annotated with @ControllerAdvice to apply its exception-handling methods
 * globally.
 *
 * @see ErrorResponse
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * Constructs an error response object.
	 * This method encapsulates the logic for creating a standardized error response
	 * object that will be returned to the client.
	 *
	 * @param ex the exception that was thrown
	 * @param status the HTTP status code for the error response
	 * @param details additional details about the error
	 * @return a ResponseEntity containing the error response
	 */
	private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, String details) {
		ErrorResponse error = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), details);
		return new ResponseEntity<>(error, status);
	}
	
	/**
	 * Handles UserNotFoundException.
	 * This method is invoked when a UserNotFoundException is thrown in any controller.
	 *
	 * @param ex the UserNotFoundException that was thrown
	 * @return a ResponseEntity containing the error response
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
		return buildErrorResponse(ex, HttpStatus.NOT_FOUND, "User not found");
	}
	
	/**
	 * Handles RestaurantNotFoundException.
	 * This method is invoked when a RestaurantNotFoundException is thrown in any controller.
	 *
	 * @param ex the RestaurantNotFoundException that was thrown
	 * @return a ResponseEntity containing the error response
	 */
	@ExceptionHandler(RestaurantNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
		return buildErrorResponse(ex, HttpStatus.NOT_FOUND, "Restaurant not found");
	}
	
	/**
	 * Handles ReviewNotFoundException.
	 * This method is invoked when a ReviewNotFoundException is thrown in any controller.
	 *
	 * @param ex the ReviewNotFoundException that was thrown
	 * @return a ResponseEntity containing the error response
	 */
	@ExceptionHandler(ReviewNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleReviewNotFoundException(ReviewNotFoundException ex) {
		return buildErrorResponse(ex, HttpStatus.NOT_FOUND, "Review not found");
	}
	
	/**
	 * Handles DuplicateUserException.
	 * This method is invoked when a DuplicateUserException is thrown in any controller.
	 *
	 * @param ex the DuplicateUserException that was thrown
	 * @return a ResponseEntity containing the error response
	 */
	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateUserException(DuplicateUserException ex) {
		return buildErrorResponse(ex, HttpStatus.CONFLICT, "Duplicate user");
	}
	
	/**
	 * Handles DuplicateRestaurantException.
	 * This method is invoked when a DuplicateRestaurantException is thrown in any controller.
	 *
	 * @param ex the DuplicateRestaurantException that was thrown
	 * @return a ResponseEntity containing the error response
	 */
	@ExceptionHandler(DuplicateRestaurantException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateRestaurantException(DuplicateRestaurantException ex) {
		return buildErrorResponse(ex, HttpStatus.CONFLICT, "Duplicate restaurant");
	}
	
	/**
	 * Handles UnauthorizedAccessException.
	 * This method is invoked when a UnauthorizedAccessException is thrown in any controller.
	 *
	 * @param ex the UnauthorizedAccessException that was thrown
	 * @return a ResponseEntity containing the error response
	 */
	@ExceptionHandler(UnauthorizedAccessException.class)
	public ResponseEntity<ErrorResponse> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
		return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, "Unauthorized access");
	}
	
	/**
	 * Handles IllegalArgumentException.
	 * This method is invoked when a IllegalArgumentException is thrown in any controller.
	 *
	 * @param ex the IllegalArgumentException that was thrown
	 * @return a ResponseEntity containing the error response
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
		return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "Invalid input");
	}
	
	/**
	 * Handles MethodArgumentNotValidException for input validation errors.
	 * This method captures validation errors related to input arguments
	 * in API requests and returns a structured error response containing
	 * all validation error messages.
	 *
	 * @param ex the MethodArgumentNotValidException that was thrown
	 * @return a ResponseEntity containing a map of field names and their associated validation errors
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, Object> errors = new HashMap<>();
		errors.put("timestamp", LocalDateTime.now());
		Map<String, String> validationErrors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			validationErrors.put(fieldName, errorMessage);
		});
		errors.put("errors", validationErrors);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handles general, unexpected exceptions.
	 * This method is a catch-all for any types of exceptions that are not caught
	 * by more specific handlers. It returns a generic error response to the client.
	 *
	 * @param ex the general Exception that was thrown
	 * @return a ResponseEntity containing the error response with a status of INTERNAL_SERVER_ERROR
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
		return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
	}
}