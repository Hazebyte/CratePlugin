package com.hazebyte.crate.cratereloaded;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.hazebyte.crate.cratereloaded.provider.economy.none.NoEconomyProvider;
import com.hazebyte.crate.cratereloaded.provider.holographic.none.NoHologramProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CorePluginTest {
    private static ServerMock server;
    private static CorePlugin plugin;

    @BeforeAll
    public static void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(CorePlugin.class);
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void verifyEnvironment() {
        Assertions.assertTrue(plugin.getServerVersion().isMockServer());
    }

    @Test
    public void verifyManagers() {
        Assertions.assertNotNull(CorePlugin.getJavaPluginComponent().getConfigManagerComponent());
        Assertions.assertNotNull(plugin.getClaimRegistrar());
        Assertions.assertNotNull(plugin.getCrateRegistrar());
        Assertions.assertNotNull(plugin.getBlockCrateRegistrar());
        Assertions.assertNotNull(plugin.getListenerHandler());
    }

    @Test
    public void verifyExceptionHandlerIsNotNull() {
        Assertions.assertNotNull(plugin.getExceptionHandler());
    }

    @Test
    public void verifyEconomyRegistration() {
        Assertions.assertNotNull(plugin.getEconomyProvider());
        Assertions.assertTrue(plugin.getEconomyProvider() instanceof NoEconomyProvider);
    }

    @Test
    public void verifyHolographicRegistration() {
        Assertions.assertNotNull(plugin.getHolographicProvider());
        Assertions.assertTrue(plugin.getHolographicProvider() instanceof NoHologramProvider);
    }
}
