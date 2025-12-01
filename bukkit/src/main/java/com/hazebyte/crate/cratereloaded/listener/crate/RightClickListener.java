package com.hazebyte.crate.cratereloaded.listener.crate;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.crate.BlockCrateRegistrar;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.CrateAction;
import com.hazebyte.crate.api.crate.CrateRegistrar;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.api.event.CrateInteractEvent;
import com.hazebyte.crate.api.util.Messenger;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.util.MoreObjects;
import com.hazebyte.crate.cratereloaded.util.PlayerUtil;
import com.hazebyte.crate.cratereloaded.util.format.CustomFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class RightClickListener implements Listener {

    private final CrateRegistrar crateHandler;
    private final BlockCrateRegistrar blockHandler;
    private final PluginSettingComponent settings;

    public RightClickListener(PluginSettingComponent settings) {
        this.settings = settings;
        crateHandler = CorePlugin.getPlugin().getCrateRegistrar();
        blockHandler = CorePlugin.getPlugin().getBlockCrateRegistrar();
    }

    /** Mystery Crate Interaction Either left or right click opens the crate */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onMysteryCrate(CrateInteractEvent event) {
        if (event.isCancelled() || event.getAction() != CrateAction.OPEN) {
            return;
        }

        Crate crate = event.getCrate();
        Player player = event.getPlayer();

        if (crate.getType() != CrateType.MYSTERY) {
            return;
        }

        if (isRateLimitExceeded(player)) {
            return;
        }

        if (!(event.isCancelled())) {
            if (crate.hasConfirmationToggle()) {
                crateHandler.openConfirmationPage(crate, player, event.getLocation());
            } else {
                crateHandler.open(crate, player, event.getLocation());
            }
        }
    }

    /**
     * Key Crate - Creative Listener Left click will open the preview menu Right click will open the
     * crate. If the user does not have a key in hand, it will search for one in their inventory.
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockCreative(CrateInteractEvent event) {
        Player player = event.getPlayer();
        // Cancelled, not open event, or not creative
        if (event.isCancelled() || event.getAction() != CrateAction.OPEN || player.getGameMode() != GameMode.CREATIVE) {
            return;
        }

        Location location = event.getLocation();
        ItemStack item = PlayerUtil.getItemInMainHand(player);
        Crate seeking = crateHandler.getCrate(item);

        // Seeking is null if the player clicks with an item that is not a crate.
        // We want to skip the over this check if the item is not a crate.
        // If it is a crate and it isn't a key crate, return since this is the incorrect call.
        if (seeking != null && (seeking.getType() != CrateType.KEY)) {
            return;
        }

        // If he has a crate in hand, and the location doesn't register the item.
        if (seeking != null && !(blockHandler.hasCrate(location, seeking))) {
            String message = CorePlugin.getPlugin().getMessage("core.invalid_crate_use");
            Messenger.tell(player, CustomFormat.format(message, seeking));
            return;
        }

        // If the item in hand is invalid and we have creative control enabled
        // Search for a valid key
        if (seeking == null && settings.isCreativeControlEnabled()) {
            List<ItemStack> contents = Arrays.asList(player.getInventory().getContents());
            Optional<ItemStack> optional = contents.stream()
                    .filter(citem -> {
                        Crate crate = crateHandler.getCrate(citem);
                        return blockHandler.hasCrate(location, crate);
                    })
                    .findFirst();

            if (optional.isPresent()) {
                item = optional.get();
                seeking = crateHandler.getCrate(item);
            }
        }

        // If the clicked location register the key item
        if (blockHandler.hasCrate(location, seeking)) {
            if (isRateLimitExceeded(player)) {
                return;
            }
            openConfirmationMenuOrCrate(seeking, player, location, item);
            return;
        }

        // If the clicked location has crates, but it isn't registered by previous statement.
        if (blockHandler.hasCrates(location)) {
            String message = CorePlugin.getPlugin().getMessage("core.invalid_crate");
            Messenger.tell(
                    player,
                    CustomFormat.format(
                            message,
                            blockHandler.getCrates(location),
                            MoreObjects.firstNonNull(blockHandler.getCrates(location))));
            PlayerUtil.pushPlayerBack(player, event.getLocation(), blockHandler.getFirstCrate(location));
            return;
        }
        return;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockSurvivalOrAdventure(CrateInteractEvent event) {
        Player player = event.getPlayer();
        // Cancelled, not open event, or not survival.
        if (event.isCancelled()
                || event.getAction() != CrateAction.OPEN
                || (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE)) {
            return;
        }

        ItemStack item = PlayerUtil.getItemInHand(player);
        Crate crate = crateHandler.getCrate(item);
        Location location = event.getLocation();

        // Check if crate isn't null and that it is a key crate
        if (crate != null && crate.getType() != CrateType.KEY) {
            return;
        }

        // Ensure that either the crate isn't null or the location isn't empty.
        if (crate == null && !(blockHandler.hasCrates(location))) {
            return;
        }

        if (crate == null) { // Infers this is a registered location
            String message = CrateAPI.getMessage("core.invalid_crate");
            Messenger.tell(
                    player,
                    CustomFormat.format(
                            message, blockHandler.getCrates(location), blockHandler.getFirstCrate(location)));
            PlayerUtil.pushPlayerBack(player, event.getLocation(), blockHandler.getFirstCrate(location));
            return;
        } else if (blockHandler.hasCrate(location, crate)) { // The crate isn't null
            if (isRateLimitExceeded(player)) {
                return;
            }
            openConfirmationMenuOrCrate(crate, player, location, item);
            return;
        } else { // Crate isn't null: He has the wrong key
            String message = CrateAPI.getMessage("core.invalid_crate_use");
            Messenger.tell(player, CustomFormat.format(message, crate));
            return;
        }
    }

    private void openConfirmationMenuOrCrate(Crate crate, Player player, Location location, ItemStack item) {
        if (crate.hasConfirmationToggle()) {
            crateHandler.openConfirmationPage(crate, player, location);
        } else {
            Map<String, Object> settings = new HashMap<String, Object>() {
                {
                    put("itemToRemove", item);
                }
            };
            crateHandler.open(crate, player, location, settings);
        }
    }

    private boolean isRateLimitExceeded(Player player) {
        if (CorePlugin.getJavaPluginComponent().getRateLimitManagerComponent().isRateLimited(player.getUniqueId())) {
            double timeLeft = ((double) CorePlugin.getJavaPluginComponent()
                            .getRateLimitManagerComponent()
                            .getMillisTilRateIsLifted(player.getUniqueId()))
                    / 1000;
            String message = CorePlugin.getPlugin().getMessage("core.cooldown");
            Messenger.tell(player, message.replace("{cooldown}", String.format("%.2f", timeLeft)));
            return true;
        } else {
            CorePlugin.getJavaPluginComponent().getRateLimitManagerComponent().request(player.getUniqueId());
        }
        return false;
    }
}
