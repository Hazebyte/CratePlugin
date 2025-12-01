package com.hazebyte.crate.validation;

import com.hazebyte.crate.api.crate.CrateType;

/**
 * Validator for Crate objects.
 * Provides both holistic validation and individual field validators.
 * This is a generic interface - implementations should specify the concrete crate type.
 */
public interface CrateValidator<T> {

    /**
     * Validates an entire crate configuration.
     * Aggregates all validation errors into a single result.
     *
     * @param crate the crate to validate
     * @return validation result containing any errors found
     */
    ValidationResult validate(T crate);

    /**
     * Validates that the crate cost is valid.
     * Cost must be >= 0.
     *
     * @param cost the cost to validate
     * @return validation result
     */
    ValidationResult validateCost(double cost);

    /**
     * Validates minimum and maximum reward bounds.
     * Minimum must be >= 1, and maximum must be >= minimum.
     *
     * @param min minimum rewards
     * @param max maximum rewards
     * @return validation result
     */
    ValidationResult validateRewardBounds(int min, int max);

    /**
     * Validates preview inventory slot configuration.
     * Slots must be between 9 and 54 (Minecraft inventory limits).
     *
     * @param rows preview rows configuration
     * @param rewardCount total number of rewards to display
     * @return validation result
     */
    ValidationResult validatePreviewSlots(int rows, int rewardCount);

    /**
     * Validates crate type compatibility with item material.
     * For example, SUPPLY crates must use CHEST material.
     *
     * @param type the crate type
     * @param materialName the material name
     * @return validation result
     */
    ValidationResult validateCrateType(CrateType type, String materialName);

    /**
     * Validates animation type compatibility with end animation type.
     * Certain animations are not compatible with certain end animations.
     *
     * @param animationType the main animation type
     * @param endAnimationType the end animation type
     * @return validation result
     */
    ValidationResult validateAnimationCompatibility(String animationType, String endAnimationType);
}
