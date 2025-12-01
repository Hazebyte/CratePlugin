package com.hazebyte.crate.cratereloaded.crate.generator.rules;

import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RewardHasChanceRuleTest {

    @Test
    public void rewardNotAllowedIfNilChance() {
        Reward reward = new RewardImpl();
        reward.setChance(0);
        RewardHasChanceRule rule = new RewardHasChanceRule();
        Assertions.assertFalse(rule.test(reward));
    }

    @Test
    public void rewardNotAllowedIfNegativeChance() {
        Reward reward = new RewardImpl();
        reward.setChance(-1);
        RewardHasChanceRule rule = new RewardHasChanceRule();
        Assertions.assertFalse(rule.test(reward));
    }

    @Test
    public void rewardAllowedWithPositiveChance() {
        Reward reward = new RewardImpl();
        reward.setChance(1);
        RewardHasChanceRule rule = new RewardHasChanceRule();
        Assertions.assertTrue(rule.test(reward));
    }

    @Test
    public void rewardAllowedWithDecimaleChance() {
        Reward reward = new RewardImpl();
        reward.setChance(0.01);
        RewardHasChanceRule rule = new RewardHasChanceRule();
        Assertions.assertTrue(rule.test(reward));
    }
}
