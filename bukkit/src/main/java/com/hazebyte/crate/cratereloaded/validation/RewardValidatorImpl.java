package com.hazebyte.crate.cratereloaded.validation;

import com.hazebyte.crate.Error;
import com.hazebyte.crate.cratereloaded.model.RewardV2;
import com.hazebyte.crate.validation.RewardValidator;
import com.hazebyte.crate.validation.ValidationResult;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Implementation of RewardValidator for validating RewardV2 configurations.
 */
@Singleton
public class RewardValidatorImpl implements RewardValidator<RewardV2> {

    @Inject
    public RewardValidatorImpl() {
        // Injectable constructor for Dagger
    }

    @Override
    public ValidationResult validate(RewardV2 reward) {
        return ValidationResult.builder()
                .combine(validateChance(reward.getChance()))
                .build();
    }

    @Override
    public ValidationResult validateChance(double chance) {
        if (chance < 0) {
            return ValidationResult.failure(Error.of("Reward chance must be >= 0, got: " + chance));
        }
        if (chance > 100) {
            return ValidationResult.failure(
                    Error.of("Reward chance must be <= 100, got: " + chance));
        }
        return ValidationResult.success();
    }
}
