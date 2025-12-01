package com.hazebyte.crate.cratereloaded.component.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.hazebyte.crate.cratereloaded.model.Config;
import java.util.function.Function;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FilePluginSettingComponentImplTest {

    @Mock
    private Config config;

    @Mock
    private FileConfiguration fileConfiguration;

    @Mock
    private Logger logger;

    private FilePluginSettingComponentImpl pluginSetting;

    private final String DEFAULT_KEY = "defaultKey";
    private final String DEFAULT_STRING_VALUE = "defaultValue";
    private final String DEFAULT_BOOLEAN_VALUE = "true";
    private final boolean EXPECTED_BOOLEAN_VALUE = true;

    @BeforeEach
    public void setup() {
        when(config.getConfig()).thenReturn(fileConfiguration);
        pluginSetting = new FilePluginSettingComponentImpl(config, logger);
    }

    @Test
    void retrieveAndCacheKey_shouldSucceed_whenKeyIsSet() {
        // arrange
        when(fileConfiguration.getString(DEFAULT_KEY)).thenReturn(DEFAULT_STRING_VALUE);

        // act
        String retrievedResult =
                pluginSetting.retrieveAndCacheKey(DEFAULT_KEY, DEFAULT_STRING_VALUE, true, Function.identity());

        // assert
        assertEquals(DEFAULT_STRING_VALUE, retrievedResult);
        assertTrue(pluginSetting.isCached(DEFAULT_KEY));
    }

    @Test
    void retrieveAndCacheKey_shouldSucceed_forConvertedValue() {
        // arrange
        when(fileConfiguration.getString(DEFAULT_KEY)).thenReturn(DEFAULT_BOOLEAN_VALUE);

        // act
        boolean retrievedResult = pluginSetting.retrieveAndCacheKey(DEFAULT_KEY, true, true, Boolean::parseBoolean);

        // assert
        assertEquals(EXPECTED_BOOLEAN_VALUE, retrievedResult);
        assertTrue(pluginSetting.isCached(DEFAULT_KEY));
    }

    @Test
    void retrieveAndCacheKey_shouldSucceed_whenKeyIsNotSet() {
        // arrange
        String expectedValue = "newKey";
        when(fileConfiguration.getString(DEFAULT_KEY)).thenReturn(null);

        // act
        String retrievedResult =
                pluginSetting.retrieveAndCacheKey(DEFAULT_KEY, expectedValue, false, Function.identity());

        // assert
        assertEquals(expectedValue, retrievedResult);
        assertTrue(pluginSetting.isCached(DEFAULT_KEY));
    }
}
