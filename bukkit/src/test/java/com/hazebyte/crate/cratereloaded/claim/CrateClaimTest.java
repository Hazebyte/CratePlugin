package com.hazebyte.crate.cratereloaded.claim;

import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;
import java.util.Collections;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CrateClaimTest extends BukkitTest {

    private Player mockPlayer;

    @BeforeEach
    public void setup() {
        mockPlayer = server.addPlayer();
    }

    @Test
    public void canCreateClaim() {
        Reward reward = new RewardImpl();
        CrateClaim claim = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .build();

        Assertions.assertNotNull(claim);
        Assertions.assertNotNull(claim.getId());
        Assertions.assertNotNull(claim.getRewards());
        Assertions.assertEquals(claim.getRewards().size(), 1);
        Assertions.assertNotNull(claim.getTimestamp());
    }

    @Test
    public void canOverrideClaimTimestamp() {
        Reward reward = new RewardImpl();
        CrateClaim claim = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .build();

        long originalTimestamp = claim.getTimestamp();
        long newTimestamp = System.currentTimeMillis() + 100;
        claim.setTimestamp(newTimestamp);

        Assertions.assertNotEquals(originalTimestamp, claim.getTimestamp());
        Assertions.assertEquals(newTimestamp, claim.getTimestamp());
    }

    @Test
    public void setExecutorToReturnTrue() {
        Reward reward = new RewardImpl();
        CrateClaim claim = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .executor(c -> true)
                .build();

        Assertions.assertTrue(claim.execute());
    }

    @Test
    public void setExecutorToReturnFalse() {
        Reward reward = new RewardImpl();
        CrateClaim claim = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .executor(c -> false)
                .build();

        Assertions.assertFalse(claim.execute());
    }

    @Test
    public void claimEqualsFunctionIsValid() {
        Reward reward = new RewardImpl();
        CrateClaim claim1 = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .build();
        CrateClaim claim2 = claim1.clone();

        Assertions.assertTrue(claim1 != claim2);
        Assertions.assertEquals(claim1, claim1);
    }

    @Test
    public void claimHashCodeFunctionIsValid() {
        Reward reward = new RewardImpl();
        CrateClaim claim1 = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .build();
        CrateClaim claim2 = claim1.clone();

        Assertions.assertTrue(claim1 != claim2);
        Assertions.assertEquals(claim1.hashCode(), claim1.hashCode());
    }
}
