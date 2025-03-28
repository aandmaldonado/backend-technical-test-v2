package com.tui.proof.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a validation error response.
 */
public class ValidationErrorResponse {

    public List<Violation> violations = new ArrayList<>();
}
