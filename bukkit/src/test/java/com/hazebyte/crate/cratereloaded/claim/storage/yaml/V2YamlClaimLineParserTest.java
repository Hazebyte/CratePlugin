package com.hazebyte.crate.cratereloaded.claim.storage.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.cratereloaded.claim.ClaimExecutor;
import com.hazebyte.crate.cratereloaded.component.OpenCrateComponent;
import com.hazebyte.crate.cratereloaded.model.Config;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class V2YamlClaimLineParserTest extends BukkitTest {
    private Player mockPlayer;

    private long zeroResultTimestamp = 1000;

    private List<String> zeroResults = Arrays.asList();

    private String zeroResultsKey = UUID.randomUUID().toString();

    private long twoResultTimestamp = 2000;

    private List<String> twoResults = Arrays.asList("chance:(0)", "chance:(0)");

    private String twoResultsKey = UUID.randomUUID().toString();

    private String setKey = "randomKey";

    private V2YamlClaimLineParser parser;

    private Config config;

    @BeforeEach
    public void setup() {
        mockPlayer = server.addPlayer();
        OpenCrateComponent mockOpenCrateComponent = Mockito.mock(OpenCrateComponent.class);
        Logger mockLogger = Mockito.mock(Logger.class);
        ClaimExecutor claimExecutor = new ClaimExecutor(plugin, mockOpenCrateComponent, mockLogger);
        parser = new V2YamlClaimLineParser(claimExecutor);
        FileConfiguration fileConfiguration = Mockito.mock(FileConfiguration.class);
        config = Mockito.mock(Config.class);
        Mockito.when(config.getConfig()).thenReturn(fileConfiguration);
        Mockito.when(fileConfiguration.getString(
                        String.format(YamlClaimConstants.CONFIG_TIMESTAMP_KEY, zeroResultsKey)))
                .thenReturn(String.valueOf(zeroResultTimestamp));
        Mockito.when(fileConfiguration.getString(String.format(YamlClaimConstants.CONFIG_TIMESTAMP_KEY, twoResultsKey)))
                .thenReturn(String.valueOf(twoResultTimestamp));
        Mockito.when(fileConfiguration.getStringList(
                        String.format(YamlClaimConstants.CONFIG_REWARDS_KEY, zeroResultsKey)))
                .thenReturn(zeroResults);
        Mockito.when(fileConfiguration.getStringList(
                        String.format(YamlClaimConstants.CONFIG_REWARDS_KEY, twoResultsKey)))
                .thenReturn(twoResults);
        Mockito.when(fileConfiguration.getString(String.format(YamlClaimConstants.CONFIG_PLAYER_KEY, zeroResultsKey)))
                .thenReturn(mockPlayer.getUniqueId().toString());
        Mockito.when(fileConfiguration.getString(String.format(YamlClaimConstants.CONFIG_PLAYER_KEY, twoResultsKey)))
                .thenReturn(mockPlayer.getUniqueId().toString());
        Mockito.when(config.getConfig().isSet(zeroResultsKey)).thenReturn(true);
        Mockito.when(config.getConfig().isSet(twoResultsKey)).thenReturn(true);
        Mockito.when(config.getConfig().isSet(setKey)).thenReturn(true);
    }

    @Test
    public void returnsClaimWithZeroResults() {
        Claim claim = parser.apply(mockPlayer, config, zeroResultsKey);

        assertNotNull(claim);
        assertEquals(mockPlayer, claim.getOwner());
        assertEquals(zeroResultTimestamp, claim.getTimestamp());
        assertNotNull(claim.getRewards());
        assertEquals(0, claim.getRewards().size());
        assertNotNull(claim.getId());
    }

    @Test
    public void returnsClaimWithResults() {
        Claim claim = parser.apply(mockPlayer, config, twoResultsKey);

        assertNotNull(claim);
        assertEquals(mockPlayer, claim.getOwner());
        assertEquals(twoResultTimestamp, claim.getTimestamp());
        assertNotNull(claim.getRewards());
        assertEquals(2, claim.getRewards().size());
        assertNotNull(claim.getId());
    }

    @Test()
    public void throwsExceptionIfPlayerIsNull() {
        assertThrows(NullPointerException.class, () -> parser.apply(null, config, twoResultsKey));
    }

    @Test()
    public void throwsExceptionIfConfigIsNull() {
        assertThrows(NullPointerException.class, () -> parser.apply(mockPlayer, null, twoResultsKey));
    }

    @Test()
    public void throwsExceptionIfKeyIsNull() {
        assertThrows(NullPointerException.class, () -> parser.apply(mockPlayer, config, null));
    }

    @Test()
    public void throwsExceptionIfKeyIsEmpty() {
        assertThrows(NullPointerException.class, () -> parser.apply(mockPlayer, config, ""));
    }

    @Test()
    public void throwsExceptionIfKeyIsNotFound() {
        assertThrows(
                NullPointerException.class,
                () -> parser.apply(mockPlayer, config, UUID.randomUUID().toString()));
    }

    @Test
    public void throwsExceptionIfKeyIsNotUUID() {
        assertThrows(IllegalArgumentException.class, () -> parser.apply(mockPlayer, config, setKey));
    }
}
