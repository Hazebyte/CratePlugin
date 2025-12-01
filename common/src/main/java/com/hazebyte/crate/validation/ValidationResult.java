package com.hazebyte.crate.validation;

import com.hazebyte.crate.Error;
import com.hazebyte.crate.exception.items.ValidationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Represents the result of a validation operation.
 * Can aggregate multiple validation errors.
 */
public class ValidationResult {

    private final List<Error> errors;
    private final boolean valid;

    private ValidationResult(List<Error> errors) {
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
        this.valid = errors.isEmpty();
    }

    /**
     * Creates a successful validation result with no errors.
     */
    public static ValidationResult success() {
        return new ValidationResult(Collections.emptyList());
    }

    /**
     * Creates a failed validation result with one or more errors.
     */
    public static ValidationResult failure(Error... errors) {
        return new ValidationResult(Arrays.asList(errors));
    }

    /**
     * Creates a failed validation result with a list of errors.
     */
    public static ValidationResult failure(List<Error> errors) {
        return new ValidationResult(errors);
    }

    /**
     * Combines this validation result with another.
     * The combined result contains all errors from both results.
     */
    public ValidationResult combine(ValidationResult other) {
        if (this.valid && other.valid) {
            return success();
        }
        List<Error> combined = new ArrayList<>(this.errors);
        combined.addAll(other.errors);
        return new ValidationResult(combined);
    }

    /**
     * Throws a ValidationException if this result is invalid.
     * Does nothing if the result is valid.
     */
    public void throwIfInvalid() throws ValidationException {
        if (!valid) {
            throw new ValidationException(errors);
        }
    }

    /**
     * Returns true if the validation was successful (no errors).
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Returns the list of validation errors.
     * Returns an empty list if validation was successful.
     */
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * Creates a builder for fluent validation result construction.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for fluent validation result construction.
     * Allows combining multiple validation results.
     */
    public static class Builder {
        private final List<Error> errors = new ArrayList<>();

        /**
         * Combines another validation result into this builder.
         */
        public Builder combine(ValidationResult result) {
            errors.addAll(result.getErrors());
            return this;
        }

        /**
         * Adds a single error to this builder.
         */
        public Builder add(Error error) {
            errors.add(error);
            return this;
        }

        /**
         * Builds the final validation result.
         */
        public ValidationResult build() {
            return errors.isEmpty() ? ValidationResult.success() : ValidationResult.failure(errors);
        }
    }
}
