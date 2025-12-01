package com.hazebyte.crate.validation;

/**
 * Validator for Reward objects.
 * Provides both holistic validation and individual field validators.
 * This is a generic interface - implementations should specify the concrete reward type.
 */
public interface RewardValidator<T> {

    /**
     * Validates an entire reward configuration.
     * Aggregates all validation errors into a single result.
     *
     * @param reward the reward to validate
     * @return validation result containing any errors found
     */
    ValidationResult validate(T reward);

    /**
     * Validates that the reward chance/probability is valid.
     * Chance must be >= 0 and <= 100.
     *
     * @param chance the chance percentage to validate
     * @return validation result
     */
    ValidationResult validateChance(double chance);
}
