package com.hazebyte.crate.cratereloaded.testdata;

import com.hazebyte.crate.cratereloaded.model.RewardV2;

public class RewardV2TestData {

    public static RewardV2 REWARDV2_1 = RewardV2.builder().chance(1).build();

    public static RewardV2 REWARDV2_2 = RewardV2.builder().chance(1).build();

    public static RewardV2 REWARDV2_WITH_NO_CHANCE =
            RewardV2.builder().chance(0).build();
}
