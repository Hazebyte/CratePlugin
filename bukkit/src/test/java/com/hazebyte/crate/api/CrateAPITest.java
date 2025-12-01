package com.hazebyte.crate.api;

import com.hazebyte.crate.BukkitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CrateAPITest extends BukkitTest {

    @Test
    public void verifyGetDataFolder() {
        Assertions.assertNotNull(CrateAPI.getDataFolder());
        Assertions.assertTrue(CrateAPI.getDataFolder().exists());
    }

    @Test
    public void verifyGetCrateRegistrar() {
        Assertions.assertNotNull(CrateAPI.getCrateRegistrar());
    }

    @Test
    public void verifyGetBlockCrateRegistrar() {
        Assertions.assertNotNull(CrateAPI.getBlockCrateRegistrar());
    }

    @Test
    public void verifyGetClaimRegistrar() {
        Assertions.assertNotNull(CrateAPI.getClaimRegistrar());
    }

    @Test
    public void verifyHasImplementation() {
        Assertions.assertTrue(CrateAPI.hasImplementation());
    }

    @Test
    public void verifyGetInstance() {
        Assertions.assertNotNull(CrateAPI.getInstance());
    }
}
