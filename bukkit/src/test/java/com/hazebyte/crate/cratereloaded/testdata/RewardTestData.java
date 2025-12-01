package com.hazebyte.crate.cratereloaded.testdata;

import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;

public class RewardTestData {

    public static Reward REWARD_1 = new RewardImpl();
    public static Reward REWARD_2 = new RewardImpl();
    public static Reward REWARD_3 = new RewardImpl();
    public static Reward REWARD_WITH_LARGE_CHANCE = new RewardImpl();
    public static Reward REWARD_WITH_ZERO_CHANCE = new RewardImpl();

    static {
        REWARD_1.setChance(1);
        REWARD_2.setChance(2);
        REWARD_3.setChance(3);
        REWARD_WITH_LARGE_CHANCE.setChance(97);
        REWARD_WITH_ZERO_CHANCE.setChance(0);
    }
}
