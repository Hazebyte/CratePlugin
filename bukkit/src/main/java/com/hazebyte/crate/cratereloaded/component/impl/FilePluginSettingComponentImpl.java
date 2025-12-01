package com.hazebyte.crate.cratereloaded.component.impl;

import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.ANIMATION_REWARD_INDICATOR_ITEM;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CLAIM_ACCEPT_BUTTON_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CLAIM_DECLINE_BUTTON_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CLAIM_HANDLING;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CLAIM_ITEM_FORMAT_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CLAIM_JOIN_MESSAGE_ENABLED_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CLAIM_LIMIT_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CLAIM_MENU_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CLAIM_SUCCESS_MESSAGE_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_ANIMATION_CSGO_BOTTOM_ITEM_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_ANIMATION_CSGO_LENGTH_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_ANIMATION_CSGO_TOP_ITEM_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_ANIMATION_EXIT_WHILE_SHUFFLING_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_ANIMATION_ROULETTE_LENGTH_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_ANIMATION_SHUFFLE_DISPLAY_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_ANIMATION_WHEEL_LENGTH_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_CONFIRMATION_MENU_NAME_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_CRAFT_CONTROL_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_HOLOGRAM_X_MODIFIER_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_HOLOGRAM_Y_MODIFIER_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_HOLOGRAM_Z_MODIFIER_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_HOTBAR_CONTROL_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_ITEM_FRAME_CONTROL_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_MATCHER_LEVEL_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_MENU_INTERACTION_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_MENU_NAME_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_PUSHBACK_ENABLED_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_PUSHBACK_X_MODIFIER_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_PUSHBACK_Y_MODIFIER_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_PUSHBACK_Z_MODIFIER_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CRATE_SHULKER_CONTROL_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.CREATIVE_CONTROL_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.DEFAULT_PLUGIN_PREFIX;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.NAVIGATION_CLOSE_CRATE_BUTTON;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.NAVIGATION_DECLINE_OPEN_CRATE_BUTTON;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.NAVIGATION_NEXT_CRATE_BUTTON;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.NAVIGATION_OPEN_CRATE_BUTTON;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.NAVIGATION_PREVIOUS_BUTTON_ITEM;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.PLUGIN_ACTION_COOLDOWN_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.PLUGIN_DATE_FORMAT_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.PLUGIN_DECIMAL_FORMAT_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.PLUGIN_HOLOGRAM_PREFERENCE_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.PLUGIN_LOGGER_ENABLE_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.PLUGIN_LOGGER_LEVEL_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.PLUGIN_PREFIX_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.PLUGIN_TIMEZONE_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.PREVIEW_CLOSE_BUTTON_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.PREVIEW_NEXT_BUTTON_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.PREVIEW_PREVIOUS_BUTTON_KEY;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.SIMPLE_BYPASS;
import static com.hazebyte.crate.cratereloaded.util.ConfigConstants.WHITE_PANE_GLASS;

import com.hazebyte.crate.api.util.Messenger;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.model.Config;
import com.hazebyte.crate.cratereloaded.util.item.ItemParser;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class FilePluginSettingComponentImpl implements PluginSettingComponent {
    private final FileConfiguration fileConfig;
    private final Map<String, Object> settings;
    private final Logger logger;

    public FilePluginSettingComponentImpl(Config fileConfig, Logger logger) {
        this.settings = new HashMap<>();
        this.fileConfig = fileConfig.getConfig();
        this.logger = logger;
    }

    protected <T> T retrieveAndCacheKey(String key, T defaultValue, boolean warn, Function<String, T> converter) {
        if (settings.containsKey(key)) {
            return (T) settings.get(key);
        }

        String configValue = fileConfig.getString(key);
        if (configValue == null || configValue.isEmpty()) {
            if (warn) {
                String errorMessage =
                        String.format("Configuration key, [%s], is not set. Defaulting to [%s].", key, defaultValue);
                logger.warning(errorMessage);
            }
            settings.put(key, defaultValue);
            return defaultValue;
        }

        T convertedValue = converter.apply(configValue);

        settings.put(key, convertedValue);

        return convertedValue;
    }

    public boolean isCached(String key) {
        return settings.containsKey(key);
    }

    /**
     * Clears the settings cache.
     * Should be called when the config is reloaded to force re-reading values from disk.
     */
    public void clearCache() {
        settings.clear();
        logger.info("Settings cache cleared - config values will be re-read from disk");
    }

    public boolean isPremiumUserOtherwiseLog(Optional<CommandSender> sender, String feature) {
        if (!isPremiumUser()) {
            String message = String.format("Feature is not enabled on this version: [%s].", feature);
            if (sender.isPresent()) {
                Messenger.tell(sender.get(), message);
            } else {
                Messenger.info(message);
            }
        }
        return isPremiumUser();
    }

    public boolean isPremiumUser() {
        return SIMPLE_BYPASS;
    }

    public Integer getCrateComparisonLevel() {
        return retrieveAndCacheKey(CRATE_MATCHER_LEVEL_KEY, 0, true, Integer::parseInt);
    }

    public Double getCrateInteractionCooldown() {
        return retrieveAndCacheKey(PLUGIN_ACTION_COOLDOWN_KEY, 1.0, true, Double::parseDouble);
    }

    public String getPluginMessagePrefix() {
        return retrieveAndCacheKey(PLUGIN_PREFIX_KEY, DEFAULT_PLUGIN_PREFIX, true, Function.identity());
    }

    public TimeZone getServerTimeZone() {
        return retrieveAndCacheKey(
                PLUGIN_TIMEZONE_KEY, TimeZone.getTimeZone(ZoneId.systemDefault()), true, TimeZone::getTimeZone);
    }

    public SimpleDateFormat getDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d yyyy HH:mm:ss z");
        return retrieveAndCacheKey(PLUGIN_DATE_FORMAT_KEY, simpleDateFormat, true, SimpleDateFormat::new);
    }

    public Boolean isLogEnabled() {
        return retrieveAndCacheKey(PLUGIN_LOGGER_ENABLE_KEY, true, false, Boolean::parseBoolean);
    }

    public Level getLogLevel() {
        Function<String, String> toUpperCase = String::toUpperCase;
        return retrieveAndCacheKey(PLUGIN_LOGGER_LEVEL_KEY, Level.FINE, true, toUpperCase.andThen(Level::parse));
    }

    public ItemStack getCrateAnimationShuffleDisplay() {
        return retrieveAndCacheKey(CRATE_ANIMATION_SHUFFLE_DISPLAY_KEY, WHITE_PANE_GLASS, false, ItemParser::parse);
    }

    public boolean allowsInventoryExiting() {
        return retrieveAndCacheKey(CRATE_ANIMATION_EXIT_WHILE_SHUFFLING_KEY, false, false, Boolean::parseBoolean);
    }

    public int getCSGOAnimationLength() {
        return retrieveAndCacheKey(CRATE_ANIMATION_CSGO_LENGTH_KEY, 6, true, Integer::parseInt);
    }

    public int getRouletteAnimationLength() {
        return retrieveAndCacheKey(CRATE_ANIMATION_ROULETTE_LENGTH_KEY, 5, true, Integer::parseInt);
    }

    public int getWheelAnimationLength() {
        return retrieveAndCacheKey(CRATE_ANIMATION_WHEEL_LENGTH_KEY, 8, true, Integer::parseInt);
    }

    public ItemStack getCSGOAnimationTopRowItem() {
        return retrieveAndCacheKey(
                CRATE_ANIMATION_CSGO_TOP_ITEM_KEY, ANIMATION_REWARD_INDICATOR_ITEM, true, ItemParser::parse);
    }

    public ItemStack getCSGOAnimationBottomRowItem() {
        return retrieveAndCacheKey(
                CRATE_ANIMATION_CSGO_BOTTOM_ITEM_KEY, ANIMATION_REWARD_INDICATOR_ITEM, true, ItemParser::parse);
    }

    public boolean isMenuInteractionEnabled() {
        return retrieveAndCacheKey(CRATE_MENU_INTERACTION_KEY, true, true, Boolean::parseBoolean);
    }

    public String getPreviewMenuName() {
        return retrieveAndCacheKey(CRATE_MENU_NAME_KEY, "Crate Menu", true, Function.identity());
    }

    public ItemStack getPreviewBackButton() {
        return retrieveAndCacheKey(
                PREVIEW_PREVIOUS_BUTTON_KEY, NAVIGATION_PREVIOUS_BUTTON_ITEM, true, ItemParser::parse);
    }

    public String getConfirmationMenuName() {
        return retrieveAndCacheKey(CRATE_CONFIRMATION_MENU_NAME_KEY, "Open Crate", true, Function.identity());
    }

    public String getClaimMenuName() {
        return retrieveAndCacheKey(CLAIM_MENU_KEY, "Claim", true, Function.identity());
    }

    public int getClaimLimit() {
        return retrieveAndCacheKey(CLAIM_LIMIT_KEY, 30, true, Integer::parseInt);
    }

    public boolean isHandlingClaims() {
        return retrieveAndCacheKey(CLAIM_HANDLING, true, true, Boolean::parseBoolean);
    }

    public boolean getClaimMessageJoinToggle() {
        return retrieveAndCacheKey(CLAIM_JOIN_MESSAGE_ENABLED_KEY, true, true, Boolean::parseBoolean);
    }

    public List<String> getClaimItemFormat() {
        List<String> itemFormat = Arrays.asList("{lore}", "&7Number of Rewards: &6{number}", "&7Received on {date}");
        List<String> parsedData = fileConfig.getStringList(CLAIM_ITEM_FORMAT_KEY);
        if (parsedData != null) {
            return parsedData;
        } else {
            return itemFormat;
        }
    }

    public String getClaimSuccessMessage() {
        return retrieveAndCacheKey(CLAIM_SUCCESS_MESSAGE_KEY, "&a&lClaimed!", true, Function.identity());
    }

    public ItemStack getClaimAcceptButton() {
        return retrieveAndCacheKey(CLAIM_ACCEPT_BUTTON_KEY, NAVIGATION_OPEN_CRATE_BUTTON, true, ItemParser::parse);
    }

    public ItemStack getClaimDeclineButton() {
        return retrieveAndCacheKey(
                CLAIM_DECLINE_BUTTON_KEY, NAVIGATION_DECLINE_OPEN_CRATE_BUTTON, true, ItemParser::parse);
    }

    public ItemStack getPreviewCloseButton() {
        return retrieveAndCacheKey(PREVIEW_CLOSE_BUTTON_KEY, NAVIGATION_CLOSE_CRATE_BUTTON, true, ItemParser::parse);
    }

    public ItemStack getPreviewNextButton() {
        return retrieveAndCacheKey(PREVIEW_NEXT_BUTTON_KEY, NAVIGATION_NEXT_CRATE_BUTTON, true, ItemParser::parse);
    }

    public boolean isCreativeControlEnabled() {
        return retrieveAndCacheKey(CREATIVE_CONTROL_KEY, true, true, Boolean::parseBoolean);
    }

    public boolean isCraftingCratesEnabled() {
        return retrieveAndCacheKey(CRATE_CRAFT_CONTROL_KEY, false, true, Boolean::parseBoolean);
    }

    public boolean isItemFrameInteractionEnabled() {
        return retrieveAndCacheKey(CRATE_ITEM_FRAME_CONTROL_KEY, false, true, Boolean::parseBoolean);
    }

    public boolean isHotbarInteractionEnabled() {
        return retrieveAndCacheKey(CRATE_HOTBAR_CONTROL_KEY, false, true, Boolean::parseBoolean);
    }

    public boolean isShulkerInteractionEnabled() {
        return retrieveAndCacheKey(CRATE_SHULKER_CONTROL_KEY, false, false, Boolean::parseBoolean);
    }

    public boolean isPushbackInteractionEnabled() {
        return retrieveAndCacheKey(CRATE_PUSHBACK_ENABLED_KEY, true, true, Boolean::parseBoolean);
    }

    public double getPushbackXModifier() {
        return retrieveAndCacheKey(CRATE_PUSHBACK_X_MODIFIER_KEY, 1.0, true, Double::parseDouble);
    }

    public double getPushbackYModifier() {
        return retrieveAndCacheKey(CRATE_PUSHBACK_Y_MODIFIER_KEY, 1.0, true, Double::parseDouble);
    }

    public double getPushbackZModifier() {
        return retrieveAndCacheKey(CRATE_PUSHBACK_Z_MODIFIER_KEY, 0.0, true, Double::parseDouble);
    }

    public double getHolographicXModifier() {
        return retrieveAndCacheKey(CRATE_HOLOGRAM_X_MODIFIER_KEY, 0.0, true, Double::parseDouble);
    }

    public double getHolographicYModifier() {
        return retrieveAndCacheKey(CRATE_HOLOGRAM_Y_MODIFIER_KEY, 0.0, true, Double::parseDouble);
    }

    public double getHolographicZModifier() {
        return retrieveAndCacheKey(CRATE_HOLOGRAM_Z_MODIFIER_KEY, 0.0, true, Double::parseDouble);
    }

    public String getDecimalFormat() {
        return retrieveAndCacheKey(PLUGIN_DECIMAL_FORMAT_KEY, "#", true, Function.identity());
    }

    public String getHologramPluginPreference() {
        return retrieveAndCacheKey(PLUGIN_HOLOGRAM_PREFERENCE_KEY, "HolographicDisplays", true, Function.identity());
    }

    public Calendar getCalendar() {
        String key = "calendar";
        Calendar calendar = (Calendar) settings.get(key);
        if (calendar == null) {
            calendar = Calendar.getInstance(getServerTimeZone());
        }
        return calendar;
    }

    public HashMap<String, String> getCustomVariables() {
        String key = "custom-variables";
        HashMap<String, String> map = new HashMap<>();
        if (!settings.containsKey(key)) {
            ConfigurationSection section = fileConfig.getConfigurationSection(key);
            if (section != null) {
                for (String variable : section.getKeys(false)) {
                    map.put(variable, section.getString(variable));
                }
                settings.put(key, map);
            }
        } else {
            map = (HashMap<String, String>) settings.get(key);
        }
        return map;
    }
}
