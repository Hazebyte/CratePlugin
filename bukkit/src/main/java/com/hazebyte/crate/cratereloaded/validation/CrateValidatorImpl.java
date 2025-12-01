package com.hazebyte.crate.cratereloaded.validation;

import com.hazebyte.crate.Error;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.cratereloaded.model.CrateV2;
import com.hazebyte.crate.validation.CrateValidator;
import com.hazebyte.crate.validation.ValidationResult;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.bukkit.Material;

/**
 * Implementation of CrateValidator for validating CrateV2 configurations.
 */
@Singleton
public class CrateValidatorImpl implements CrateValidator<CrateV2> {

    private static final int MAX_INVENTORY_SLOTS = 54;
    private static final int MIN_INVENTORY_SLOTS = 9;

    @Inject
    public CrateValidatorImpl() {
        // Injectable constructor for Dagger
    }

    @Override
    public ValidationResult validate(CrateV2 crate) {
        return ValidationResult.builder()
                .combine(validateCost(crate.getSalePrice()))
                .combine(validateRewardBounds(crate.getMinimumRewards(), crate.getMaximumRewards()))
                .combine(validatePreviewSlots(
                        crate.getPreviewRows(),
                        crate.getRewards().size() + crate.getConstantRewards().size()))
                .combine(validateCrateType(crate.getType(), crate.getItem().getType().name()))
                .combine(validateAnimationCompatibility(
                        crate.getAnimationType() != null ? crate.getAnimationType().name() : "NONE",
                        crate.getEndAnimationType() != null ? crate.getEndAnimationType().name() : "BLANK"))
                .build();
    }

    @Override
    public ValidationResult validateCost(double cost) {
        if (cost < 0) {
            return ValidationResult.failure(Error.of("Cost must be >= 0, got: " + cost));
        }
        return ValidationResult.success();
    }

    @Override
    public ValidationResult validateRewardBounds(int min, int max) {
        List<Error> errors = new ArrayList<>();

        if (min < 1) {
            errors.add(Error.of("Minimum rewards must be >= 1, got: " + min));
        }
        if (max < min) {
            errors.add(
                    Error.of(
                            "Maximum rewards ("
                                    + max
                                    + ") must be >= minimum ("
                                    + min
                                    + ")"));
        }

        return errors.isEmpty() ? ValidationResult.success() : ValidationResult.failure(errors);
    }

    @Override
    public ValidationResult validatePreviewSlots(int rows, int rewardCount) {
        int requestedSlots = rows * 9;

        // Slots needed to display all rewards
        int neededSlots = Math.max(requestedSlots, rewardCount);

        // Constrain to Minecraft inventory limits
        if (neededSlots < MIN_INVENTORY_SLOTS) {
            return ValidationResult.failure(
                    Error.of(
                            "Preview slots must be at least "
                                    + MIN_INVENTORY_SLOTS
                                    + ", got: "
                                    + neededSlots));
        }

        if (neededSlots > MAX_INVENTORY_SLOTS) {
            return ValidationResult.failure(
                    Error.of(
                            "Preview slots cannot exceed "
                                    + MAX_INVENTORY_SLOTS
                                    + ", got: "
                                    + neededSlots
                                    + " (rows="
                                    + rows
                                    + ", rewards="
                                    + rewardCount
                                    + ")"));
        }

        return ValidationResult.success();
    }

    @Override
    public ValidationResult validateCrateType(CrateType type, String materialName) {
        if (type == CrateType.SUPPLY) {
            try {
                Material material = Material.valueOf(materialName);
                if (material != Material.CHEST) {
                    return ValidationResult.failure(
                            Error.of(
                                    "Supply crates must use CHEST material, got: "
                                            + materialName));
                }
            } catch (IllegalArgumentException e) {
                return ValidationResult.failure(
                        Error.of("Invalid material name: " + materialName));
            }
        }
        return ValidationResult.success();
    }

    @Override
    public ValidationResult validateAnimationCompatibility(
            String animationType, String endAnimationType) {
        // Most animations are compatible with most end animations
        // Add specific incompatibility checks here if needed

        // Example: ROULETTE animation might not work with certain end animations
        // if ("ROULETTE".equals(animationType) && "DISPLAY".equals(endAnimationType)) {
        //     return ValidationResult.failure(
        //         Error.of("ROULETTE animation is not compatible with DISPLAY end animation")
        //     );
        // }

        return ValidationResult.success();
    }
}
