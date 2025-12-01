package com.hazebyte.crate.cratereloaded.component.impl;

import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.ConfigServiceComponent;
import com.hazebyte.crate.cratereloaded.model.Config;
import com.hazebyte.crate.cratereloaded.util.ConfigConstants;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConfigManagerComponentTest extends BukkitTest {

    private ConfigServiceComponent component;

    @BeforeEach
    public void setup() {
        component = CorePlugin.getJavaPluginComponent().getConfigManagerComponent();
    }

    @Test
    public void canAddNewConfig() throws IOException {
        String testConfigName = "test.yml";
        File newConfig = new File(plugin.getDataFolder(), testConfigName);
        newConfig.createNewFile();
        Config createdConfig = component.addConfig(newConfig);

        Config config = component.getConfigWithName(testConfigName);
        Assertions.assertNotNull(config);
        Assertions.assertEquals(createdConfig, config);
    }

    @Test
    public void canRetrieveCrateConfigs() {
        Collection<Config> configs = component.getConfigsStartingWith(ConfigConstants.CONFIG_CRATE_STORE_SEARCH_INDEX);
        Assertions.assertNotNull(configs);
        Assertions.assertEquals(1, configs.size());
    }

    @Test
    public void canRetrieveClaimConfigs() {
        Collection<Config> configs = component.getConfigsStartingWith(ConfigConstants.CONFIG_CLAIM_STORE_SEARCH_INDEX);
        Assertions.assertNull(configs);
    }

    @Test
    public void canRetrieveSettingsConfigs() {
        Collection<Config> configs = component.getConfigsStartingWith(ConfigConstants.CONFIG_SETTINGS_SEARCH_INDEX);
        Assertions.assertNotNull(configs);
        Assertions.assertEquals(1, configs.size());
    }

    @Test
    public void canRetrieveLocationIndex() {
        Collection<Config> configs =
                component.getConfigsStartingWith(ConfigConstants.CONFIG_LOCATION_STORE_SEARCH_INDEX);
        Assertions.assertNotNull(configs);
        Assertions.assertEquals(1, configs.size());
    }

    @Test
    public void canRetrieveAllConfigs() {
        Collection<Config> configs = component.getConfigsStartingWith("");
        Assertions.assertNotNull(configs);
        Assertions.assertTrue(configs.size() > 3);
    }
}
