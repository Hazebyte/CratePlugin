package com.hazebyte.crate.cratereloaded.crate.generator.rules;

import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UniqueRewardRuleTest {

    @Test
    public void rewardAllowedIfNotUnique() {
        List<Reward> pool = new ArrayList<>();
        Reward reward = new RewardImpl();
        reward.setUnique(false);

        UniqueRewardRule rule = new UniqueRewardRule(pool);

        boolean result = rule.test(reward);

        Assertions.assertTrue(result);
    }

    @Test
    public void rewardAllowedIfUniqueAndNotInPool() {
        List<Reward> pool = new ArrayList<>();
        Reward reward = new RewardImpl();
        reward.setUnique(true);

        UniqueRewardRule rule = new UniqueRewardRule(pool);

        boolean result = rule.test(reward);

        Assertions.assertTrue(result);
    }

    @Test
    public void rewardNotAllowedIfUniqueAndInPool() {
        List<Reward> pool = new ArrayList<>();
        Reward reward = new RewardImpl();
        reward.setUnique(true);
        pool.add(reward);

        UniqueRewardRule rule = new UniqueRewardRule(pool);

        boolean result = rule.test(reward);

        Assertions.assertFalse(result);
    }
}
