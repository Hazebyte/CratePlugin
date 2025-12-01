package com.hazebyte.crate.cratereloaded.component.impl;

import static com.hazebyte.crate.cratereloaded.testdata.RewardTestData.REWARD_1;
import static com.hazebyte.crate.cratereloaded.testdata.RewardTestData.REWARD_2;
import static com.hazebyte.crate.cratereloaded.testdata.RewardTestData.REWARD_3;
import static com.hazebyte.crate.cratereloaded.testdata.RewardTestData.REWARD_WITH_LARGE_CHANCE;
import static com.hazebyte.crate.cratereloaded.testdata.RewardTestData.REWARD_WITH_ZERO_CHANCE;
import static com.hazebyte.crate.cratereloaded.testdata.RewardV2TestData.REWARDV2_1;
import static com.hazebyte.crate.cratereloaded.testdata.RewardV2TestData.REWARDV2_2;
import static com.hazebyte.crate.cratereloaded.testdata.RewardV2TestData.REWARDV2_WITH_NO_CHANCE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.component.RewardServiceComponent;
import com.hazebyte.crate.cratereloaded.model.RewardV2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RewardServiceComponentImplTest {

    private RewardServiceComponent rewardServiceComponent;

    @BeforeEach
    public void setup() {
        rewardServiceComponent = new RewardServiceComponentImpl();
    }

    @Test
    public void createPrizePool_success() {
        // arrange
        List<Reward> rewards = Arrays.asList(REWARD_1, REWARD_WITH_ZERO_CHANCE);
        Predicate<Reward> predicate = (reward) -> reward.getChance() == 0;
        List<Predicate<Reward>> predicates = Arrays.asList(predicate);

        // act
        List<Reward> generatedRewards = rewardServiceComponent.createPrizePool(rewards, predicates);

        // assert
        assertThat(generatedRewards, hasItem(REWARD_WITH_ZERO_CHANCE));
    }

    @Test
    public void generatePrize_success() {
        // arrange
        List<Reward> rewards = Arrays.asList(REWARD_1, REWARD_2, REWARD_3);

        // act
        Reward reward = rewardServiceComponent.generatePrize(rewards);

        // assert
        assertNotNull(reward);
        assertThat(rewards, hasItem(reward));
    }

    @Test
    public void generatePrize_returnsNull_ifRewardHasNoChance() {
        // arrange
        List<Reward> rewards = Arrays.asList(REWARD_WITH_ZERO_CHANCE);

        // act
        Reward reward = rewardServiceComponent.generatePrize(rewards);

        // assert
        assertNull(reward);
    }

    @Test
    public void createPrizePoolV2_success() {
        // arrange
        List<RewardV2> rewards = Arrays.asList(REWARDV2_1, REWARDV2_WITH_NO_CHANCE);
        Predicate<RewardV2> predicate = (reward) -> reward.getChance() == 0;
        List<Predicate<RewardV2>> predicates = Arrays.asList(predicate);

        // act
        List<RewardV2> generatedRewards = rewardServiceComponent.createPrizePoolV2(rewards, predicates);

        // assert
        assertThat(generatedRewards, hasItem(REWARDV2_WITH_NO_CHANCE));
    }

    @Test
    public void generatePrizeV2_success() {
        // arrange
        List<RewardV2> rewards = Arrays.asList(REWARDV2_1, REWARDV2_2);

        // act
        RewardV2 reward = rewardServiceComponent.generatePrizeV2(rewards);

        // assert
        assertNotNull(reward);
        assertThat(rewards, hasItem(reward));
    }

    @Test
    public void generatePrizeV2_returnsNull_ifRewardHasNoChance() {
        // arrange
        List<RewardV2> rewards = Arrays.asList(REWARDV2_WITH_NO_CHANCE);

        // act
        RewardV2 reward = rewardServiceComponent.generatePrizeV2(rewards);

        // assert
        assertNull(reward);
    }

    @Test
    public void generatePrize_throwsException_onNullArgs() {
        assertThrows(NullPointerException.class, () -> rewardServiceComponent.generatePrize(null));
    }

    @Test
    public void generatePrizeV2_throwsException_onNullArgs() {
        assertThrows(NullPointerException.class, () -> rewardServiceComponent.generatePrizeV2(null));
    }

    @Test
    public void createPrizePool_throwsException_onNullArgs() {
        assertThrows(NullPointerException.class, () -> rewardServiceComponent.createPrizePool(null, Arrays.asList()));
        assertThrows(NullPointerException.class, () -> rewardServiceComponent.createPrizePool(Arrays.asList(), null));
    }

    @Test
    public void createPrizePoolV2_throwsException_onNullArgs() {
        assertThrows(NullPointerException.class, () -> rewardServiceComponent.createPrizePoolV2(null, Arrays.asList()));
        assertThrows(NullPointerException.class, () -> rewardServiceComponent.createPrizePoolV2(Arrays.asList(), null));
    }

    @Test
    public void generatePrize_smokeTest_probability() {
        // arrange
        int count = 100000;
        Map<Reward, Integer> rewards = new HashMap() {
            {
                put(REWARD_1, 0);
                put(REWARD_2, 0);
                put(REWARD_3, 0);
                put(REWARD_WITH_LARGE_CHANCE, 0);
            }
        };

        // act
        for (int i = 0; i < count; i++) {
            Reward reward = rewardServiceComponent.generatePrize(
                    rewards.keySet().stream().collect(Collectors.toList()));
            rewards.put(reward, rewards.getOrDefault(reward, 0) + 1);
        }

        // assert
        double total = REWARD_1.getChance()
                + REWARD_2.getChance()
                + REWARD_3.getChance()
                + REWARD_WITH_LARGE_CHANCE.getChance();
        for (Map.Entry<Reward, Integer> entry : rewards.entrySet()) {
            double expectedProbability = entry.getKey().getChance() / total;
            assertTrue(Math.abs((double) entry.getValue() / count) - expectedProbability < 0.01);
        }
    }
}
