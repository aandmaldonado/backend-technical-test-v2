package com.tui.proof.exception;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling various exceptions across the application.
 */
@Slf4j
@ControllerAdvice
@Validated
public class GlobalExceptionHandler {

    /**
     * Handles validation errors and constructs a response with the validation error details.
     *
     * @param exception the MethodArgumentNotValidException thrown when validation fails
     * @return a ResponseEntity containing the validation error response and a BAD_REQUEST status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationError(MethodArgumentNotValidException exception) {
        log.error("Validation exception occurred: {}", exception.getMessage());

        ValidationErrorResponse error = new ValidationErrorResponse();
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                error.violations.add(new Violation(fieldError.getField(),
                        fieldError.getDefaultMessage())));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles IllegalStateException, IllegalArgumentException and OrderUpdateNotAllowedException exceptions.
     *
     * @param e the exception thrown
     * @return a ResponseEntity containing the exception message and a BAD_REQUEST status
     */
    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class,
            OrderUpdateNotAllowedException.class})
    public ResponseEntity<Map<String, String>> handleBadRequestExceptions(Exception e) {
        return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles OrderNotFoundException exceptions.
     *
     * @param e the exception thrown
     * @return a ResponseEntity containing the exception message and a NOT_FOUND status
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(Exception e) {
        return new ResponseEntity<>(Map.of("message", e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
