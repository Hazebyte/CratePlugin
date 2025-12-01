package com.hazebyte.crate.cratereloaded.claim;

import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClaimManagerTest extends BukkitTest {
    private Player mockPlayer;

    private Player mockPlayer2;

    private RewardImpl defaultReward = new RewardImpl("dirt 1");

    @BeforeEach
    public void setup() {
        mockPlayer = server.addPlayer();
        mockPlayer2 = server.addPlayer();
    }

    @AfterEach
    public void teardown() {
        mockPlayer = null;
        mockPlayer2 = null;
    }

    @Test
    public void claimManagerIsValid() {
        Assertions.assertNotNull(plugin.getClaimRegistrar());
    }

    @Test
    public void canAddClaim() {
        Reward reward = defaultReward;
        String testCommand = "testCommand";
        reward.setCommands(Arrays.asList(testCommand));
        Claim claim = plugin.getClaimRegistrar()
                .addClaim(mockPlayer, Collections.singletonList(reward))
                .join();
        Assertions.assertNotNull(claim);
        Assertions.assertEquals(claim.getRewards().size(), 1);
        Assertions.assertEquals(claim.getRewards().get(0).getCommands().size(), 1);
        Assertions.assertEquals(claim.getRewards().get(0).getCommands().get(0), testCommand);
    }

    @Test
    public void canAddMultipleClaimsWithSameReward() {
        Reward reward = defaultReward;
        int amount = 5;
        for (int i = 0; i < amount; i++) {
            plugin.getClaimRegistrar()
                    .addClaim(mockPlayer, Collections.singletonList(reward))
                    .join();
        }
        Collection<Claim> claims =
                plugin.getClaimRegistrar().getClaims(mockPlayer).join();
        Assertions.assertEquals(claims.size(), amount);
    }

    @Test
    public void canRemoveClaim() throws IOException {
        Reward reward = defaultReward;
        Claim claim = plugin.getClaimRegistrar()
                .addClaim(mockPlayer, Collections.singletonList(reward))
                .join();
        plugin.getClaimRegistrar().removeClaim(claim).join();

        Optional<Claim> retrieval =
                plugin.getClaimRegistrar().getClaim(mockPlayer, claim.getId()).join();
        Assertions.assertTrue(!retrieval.isPresent());
    }

    @Test
    public void canGetClaimWithUUID() {
        Reward reward = defaultReward;
        Claim claim = plugin.getClaimRegistrar()
                .addClaim(mockPlayer, Collections.singletonList(reward))
                .join();

        Optional<Claim> searchClaim =
                plugin.getClaimRegistrar().getClaim(mockPlayer, claim.getId()).join();

        Assertions.assertTrue(searchClaim.isPresent());
        Assertions.assertEquals(claim, searchClaim.get());
    }

    @Test
    public void canGetClaimByTimestamp() {
        Reward reward = defaultReward;
        Claim claim = plugin.getClaimRegistrar()
                .addClaim(mockPlayer, Collections.singletonList(reward))
                .join();

        Collection<Claim> searchClaim = plugin.getClaimRegistrar()
                .getClaim(mockPlayer, claim.getTimestamp())
                .join();

        Assertions.assertEquals(searchClaim.size(), 1);
        Assertions.assertEquals(claim, searchClaim.stream().findFirst().get());
    }

    @Test
    public void canGetAllClaims() {
        Reward reward = defaultReward;
        int amount = 100;
        for (int i = 0; i < amount; i++) {
            plugin.getClaimRegistrar()
                    .addClaim(mockPlayer, Collections.singletonList(reward))
                    .join();
        }
        Collection<Claim> claims =
                plugin.getClaimRegistrar().getClaims(mockPlayer).join();
        Assertions.assertEquals(claims.size(), amount);
    }

    @Test
    public void canOpenClaimMenu() {
        plugin.getClaimRegistrar().openInventory(mockPlayer);
    }

    @Test
    public void canSpoofyInventory() {
        plugin.getClaimRegistrar().spoofInventory(mockPlayer, mockPlayer2);
    }
}
