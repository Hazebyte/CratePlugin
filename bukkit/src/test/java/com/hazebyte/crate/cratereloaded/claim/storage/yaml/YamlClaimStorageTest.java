package com.hazebyte.crate.cratereloaded.claim.storage.yaml;

import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.claim.ClaimExecutor;
import com.hazebyte.crate.cratereloaded.claim.CrateClaim;
import com.hazebyte.crate.cratereloaded.component.OpenCrateComponent;
import com.hazebyte.crate.cratereloaded.model.Config;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class YamlClaimStorageTest extends BukkitTest {

    private YamlClaimStorage yamlClaimStorage;
    private ClaimExecutor claimExecutor;
    private Player mockPlayer;

    @BeforeEach
    public void setup() {
        mockPlayer = server.addPlayer();
        OpenCrateComponent mockOpenCrateComponent = Mockito.mock(OpenCrateComponent.class);
        Logger mockLogger = Mockito.mock(Logger.class);
        claimExecutor = new ClaimExecutor(plugin, mockOpenCrateComponent, mockLogger);
        yamlClaimStorage = new YamlClaimStorage(plugin, claimExecutor);
    }

    @AfterEach
    public void cleanup() {
        mockPlayer = null;
        yamlClaimStorage = null;
        claimExecutor = null;
    }

    @Test
    public void saveClaim() {
        final Reward reward = new RewardImpl();
        final CrateClaim claim = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .build();
        final UUID uuid = claim.getId();

        Config config = yamlClaimStorage.getConfig(mockPlayer);
        yamlClaimStorage.saveClaim(claim).join();
        checkConfigKeyIsSet(config, YamlClaimConstants.CONFIG_VERSION_KEY, uuid);
        checkConfigKeyIsSet(config, YamlClaimConstants.CONFIG_TIMESTAMP_KEY, uuid);
        checkConfigKeyIsSet(config, YamlClaimConstants.CONFIG_PLAYER_KEY, uuid);
        checkConfigKeyIsSet(config, YamlClaimConstants.CONFIG_REWARDS_KEY, uuid);
    }

    private void checkConfigKeyIsSet(Config config, String key, UUID uuid) {
        String formattedKey = String.format(key, uuid);
        Assertions.assertTrue(config.getConfig().isSet(formattedKey));
    }

    @Test
    public void getClaims() {
        final Reward reward = new RewardImpl();
        final CrateClaim claim = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .build();

        yamlClaimStorage.saveClaim(claim).join();
        final Collection<Claim> claims = yamlClaimStorage.getClaims(mockPlayer).join();

        Assertions.assertNotNull(claims);
        Assertions.assertEquals(claims.size(), 1);
    }

    @Test
    public void deleteClaim() {
        final Reward reward = new RewardImpl();
        final CrateClaim adding = CrateClaim.builder()
                .owner(mockPlayer)
                .rewards(Collections.singletonList(reward))
                .build();

        yamlClaimStorage.saveClaim(adding).join();

        Collection<Claim> claims = yamlClaimStorage.getClaims(mockPlayer).join();
        Assertions.assertTrue(claims.size() >= 1);
        for (Claim claim : claims) {
            yamlClaimStorage.deleteClaim(claim).join();
        }

        claims = yamlClaimStorage.getClaims(mockPlayer).join();

        Assertions.assertNotNull(claims);
        Assertions.assertEquals(claims.size(), 0);
    }
}
