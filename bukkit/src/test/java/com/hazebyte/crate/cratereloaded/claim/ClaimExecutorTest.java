package com.hazebyte.crate.cratereloaded.claim;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.api.event.ClaimEvent;
import com.hazebyte.crate.cratereloaded.component.OpenCrateComponent;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ClaimExecutorTest extends BukkitTest {

    private PlayerMock mockPlayer;
    private ClaimExecutor claimExecutor;
    private OpenCrateComponent mockOpenCrateComponent;
    private Logger mockLogger;

    @BeforeEach
    public void setup() {
        mockPlayer = server.addPlayer();
        mockOpenCrateComponent = Mockito.mock(OpenCrateComponent.class);
        mockLogger = Mockito.mock(Logger.class);
        claimExecutor = new ClaimExecutor(plugin, mockOpenCrateComponent, mockLogger);
    }

    @AfterEach
    public void teardown() {
        mockPlayer = null;
        claimExecutor = null;
        mockOpenCrateComponent = null;
        mockLogger = null;
    }

    @Test
    public void claimEventMustBeValid() {
        Assertions.assertFalse(claimExecutor.isValidEvent(null));
    }

    @Test
    public void claimMustBeValid() {
        ClaimEvent event = new ClaimEvent(null);

        Assertions.assertFalse(claimExecutor.isValidEvent(event));
    }

    @Test
    public void claimOwnerMustBeValid() {
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(claim.getOwner()).thenReturn(null);
        ClaimEvent event = new ClaimEvent(claim);
        Assertions.assertFalse(claimExecutor.isValidEvent(event));
    }

    @Test
    public void claimRewardsMustBeValid() {
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(claim.getOwner()).thenReturn(mockPlayer);
        Mockito.when(claim.getRewards()).thenReturn(null);
        ClaimEvent event = new ClaimEvent(claim);
        Assertions.assertFalse(claimExecutor.isValidEvent(event));
    }

    @Test
    public void claimEventMustNotBeCancelled() {
        Claim claim = Mockito.mock(Claim.class);
        Mockito.when(claim.getOwner()).thenReturn(mockPlayer);
        List<Reward> rewards = Collections.singletonList(new RewardImpl());
        Mockito.when(claim.getRewards()).thenReturn(rewards);
        ClaimEvent event = new ClaimEvent(claim);
        event.setCancelled(true);
        Assertions.assertFalse(claimExecutor.isValidEvent(event));
    }

    @Test
    public void claimPlayerMustBeOnline() {
        Claim claim = Mockito.mock(Claim.class);
        Player mockPlayer2 = Mockito.mock(Player.class);
        Mockito.when(mockPlayer2.isOnline()).thenReturn(false);
        Mockito.when(claim.getOwner()).thenReturn(mockPlayer2);
        List<Reward> rewards = Collections.singletonList(new RewardImpl());
        Mockito.when(claim.getRewards()).thenReturn(rewards);
        ClaimEvent event = new ClaimEvent(claim);
        Assertions.assertFalse(claimExecutor.isValidExecution(event));
    }

    @Test
    public void claimInventoryMustNotBeFull() {}

    @Test
    public void claimExecuted() {
        Reward reward = new RewardImpl();
        Claim claim = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .build();
        ClaimEvent event = new ClaimEvent(claim);
        ClaimEvent spyEvent = Mockito.spy(event);
        claimExecutor.executeEvent(spyEvent);

        Mockito.verify(spyEvent, Mockito.times(3)).getClaim();
    }

    @Test
    public void logEvent() {
        Reward reward = new RewardImpl();
        Claim claim = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .build();
        ClaimEvent event = new ClaimEvent(claim);
        claimExecutor.logEvent(event);
    }

    @Test
    public void applySmokeTest() {
        Reward reward = new RewardImpl();
        Claim claim = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .build();
        Assertions.assertTrue(claimExecutor.apply(claim));
    }
}
