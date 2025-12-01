package com.hazebyte.crate.cratereloaded.crate.generator.rules;

import com.hazebyte.crate.api.crate.reward.Reward;
import java.util.function.Predicate;

public class RewardHasChanceRule implements Predicate<Reward> {
    @Override
    public boolean test(Reward reward) {
        return reward.getChance() > 0;
    }
}
