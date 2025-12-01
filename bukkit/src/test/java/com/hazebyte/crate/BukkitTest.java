package com.hazebyte.crate;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BukkitTest {

    protected static ServerMock server;
    protected static CorePlugin plugin;

    @BeforeAll
    protected static void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(CorePlugin.class);
    }

    @AfterAll
    protected static void tearDown() {
        MockBukkit.unmock();
    }
}
