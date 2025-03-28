package com.tui.proof.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a validation error violation.
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Data
public class Violation {
    private String fieldName;
    private String message;
}
