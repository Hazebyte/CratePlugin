package com.hazebyte.crate.cratereloaded.listener.inventory;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.crate.CrateRegistrar;
import com.hazebyte.crate.api.util.Messenger;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.util.PlayerUtil;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

/** Created by willi on 3/3/2017. */
public class ItemFrameListener implements Listener {

    private final PluginSettingComponent settings;

    public ItemFrameListener(PluginSettingComponent settings) {
        this.settings = settings;
    }

    /**
     * Prevents players from placing crate item's onto item frames.
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onFrameInteract(PlayerInteractEntityEvent event) {
        // If item frame is allowed, skip checks
        if (settings.isItemFrameInteractionEnabled()) return;

        CrateRegistrar manager = CorePlugin.getPlugin().getCrateRegistrar();
        Player player = event.getPlayer();
        EntityType type = event.getRightClicked().getType();

        boolean isGlowItemFrame = false;
        try {
            isGlowItemFrame = type == EntityType.valueOf("GLOW_ITEM_FRAME");
        } catch (IllegalArgumentException ignored) {
            // GLOW_ITEM_FRAME does not exist.
        }

        if (type == EntityType.ITEM_FRAME || isGlowItemFrame) {
            ItemStack item = PlayerUtil.getItemInHand(player);

            if (manager.isCrate(item)) {
                String message = CrateAPI.getMessage("core.invalid_action_frame");
                Messenger.tell(player, message);
                event.setCancelled(true);
            }
        }
    }
}
