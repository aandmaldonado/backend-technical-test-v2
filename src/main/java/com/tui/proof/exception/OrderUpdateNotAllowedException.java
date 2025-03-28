package com.tui.proof.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an attempt is made to update an order that is not allowed.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderUpdateNotAllowedException extends RuntimeException {
    public OrderUpdateNotAllowedException(String message) {
        super(message);
    }
}
