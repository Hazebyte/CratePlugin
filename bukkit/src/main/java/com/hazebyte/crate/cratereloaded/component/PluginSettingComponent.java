package com.hazebyte.crate.cratereloaded.component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import org.bukkit.inventory.ItemStack;

public interface PluginSettingComponent {

    Integer getCrateComparisonLevel();

    Double getCrateInteractionCooldown();

    String getPluginMessagePrefix();

    TimeZone getServerTimeZone();

    SimpleDateFormat getDateFormat();

    Level getLogLevel();

    ItemStack getCrateAnimationShuffleDisplay();

    boolean allowsInventoryExiting();

    int getCSGOAnimationLength();

    int getRouletteAnimationLength();

    int getWheelAnimationLength();

    ItemStack getCSGOAnimationTopRowItem();

    ItemStack getCSGOAnimationBottomRowItem();

    boolean isMenuInteractionEnabled();

    String getPreviewMenuName();

    String getConfirmationMenuName();

    String getClaimMenuName();

    int getClaimLimit();

    boolean isHandlingClaims();

    boolean getClaimMessageJoinToggle();

    List<String> getClaimItemFormat();

    String getClaimSuccessMessage();

    ItemStack getClaimAcceptButton();

    ItemStack getClaimDeclineButton();

    ItemStack getPreviewCloseButton();

    ItemStack getPreviewBackButton();

    ItemStack getPreviewNextButton();

    boolean isCreativeControlEnabled();

    boolean isCraftingCratesEnabled();

    boolean isItemFrameInteractionEnabled();

    boolean isHotbarInteractionEnabled();

    boolean isShulkerInteractionEnabled();

    boolean isPushbackInteractionEnabled();

    double getPushbackXModifier();

    double getPushbackYModifier();

    double getPushbackZModifier();

    double getHolographicXModifier();

    double getHolographicYModifier();

    double getHolographicZModifier();

    String getDecimalFormat();

    String getHologramPluginPreference();

    /**
     * Clears the settings cache, forcing values to be re-read from disk.
     * Should be called when the configuration is reloaded.
     */
    void clearCache();
}
