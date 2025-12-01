package com.hazebyte.crate.cratereloaded.crate.generator.rules;

import com.hazebyte.crate.api.crate.reward.Reward;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class UniqueRewardRule implements Predicate<Reward> {

    // this is the list of rewards that should not add duplicates if it is unique.
    // a set is not used as a DS here since rewards can be equal to each other and not be unique.
    private List<Reward> rewards;

    public UniqueRewardRule(List<Reward> rewards) {
        Objects.requireNonNull(rewards);
        this.rewards = rewards;
    }

    @Override
    public boolean test(Reward reward) {
        if (!reward.isUnique()) {
            return true;
        }

        return !rewards.contains(reward);
    }
}
