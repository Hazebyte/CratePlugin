package com.hazebyte.crate.cratereloaded.claim;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClaimMessageConsumerTest extends BukkitTest {
    private static final String CORE_CLAIM_HOLD = "core.claim_hold";
    private Collection<Claim> claims;

    private PlayerMock playerMock;

    @BeforeEach
    public void setup() {
        playerMock = server.addPlayer();
        claims = new ArrayList<>();
        claims.add(CrateClaim.builder()
                .owner(playerMock)
                .rewards(Collections.singletonList(new RewardImpl()))
                .build());
    }

    @AfterEach
    public void teardown() {}

    @Test
    public void informsWithClaim() {
        Consumer<Collection<Claim>> consumer = new ClaimMessageConsumer(playerMock);
        consumer.accept(claims);
        Assertions.assertTrue(playerMock.nextMessage().contains(CORE_CLAIM_HOLD));
        Assertions.assertNull(playerMock.nextMessage());
    }

    @Test
    public void noOpWithoutClaim() {
        Consumer<Collection<Claim>> consumer = new ClaimMessageConsumer(playerMock);
        consumer.accept(new ArrayList<>());
        Assertions.assertNull(playerMock.nextMessage());
    }
}
