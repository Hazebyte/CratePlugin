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
import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class V1YamlClaimLineParserTest extends BukkitTest {

    private Player mockPlayer;

    private List<String> zeroResults = Arrays.asList();

    private String zeroResultsKey = "1000";

    private List<String> twoResults = Arrays.asList("chance:(0)", "chance:(0)");

    private String twoResultsKey = "2000";

    private static V1YamlClaimLineParser parser;

    private Config config;

    @BeforeEach
    public void setup() {
        mockPlayer = server.addPlayer();
        OpenCrateComponent mockOpenCrateComponent = Mockito.mock(OpenCrateComponent.class);
        Logger mockLogger = Mockito.mock(Logger.class);
        ClaimExecutor claimExecutor = new ClaimExecutor(plugin, mockOpenCrateComponent, mockLogger);
        parser = new V1YamlClaimLineParser(claimExecutor);
        config = Mockito.mock(Config.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(config.getConfig().getStringList(zeroResultsKey)).thenReturn(zeroResults);
        Mockito.when(config.getConfig().getStringList(twoResultsKey)).thenReturn(twoResults);
        Mockito.when(config.getConfig().isSet(zeroResultsKey)).thenReturn(true);
        Mockito.when(config.getConfig().isSet(twoResultsKey)).thenReturn(true);
    }

    @Test
    public void returnsClaimWithZeroResults() {
        Claim claim = parser.apply(mockPlayer, config, zeroResultsKey);

        assertNotNull(claim);
        assertEquals(mockPlayer, claim.getOwner());
        assertEquals(Long.parseLong(zeroResultsKey), claim.getTimestamp());
        assertNotNull(claim.getRewards());
        assertEquals(0, claim.getRewards().size());
        assertNotNull(claim.getId());
    }

    @Test
    public void returnsClaimWithResults() {
        Claim claim = parser.apply(mockPlayer, config, twoResultsKey);

        assertNotNull(claim);
        assertEquals(mockPlayer, claim.getOwner());
        assertEquals(Long.parseLong(twoResultsKey), claim.getTimestamp());
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
        assertThrows(NullPointerException.class, () -> parser.apply(mockPlayer, config, "randomKey"));
    }
}
