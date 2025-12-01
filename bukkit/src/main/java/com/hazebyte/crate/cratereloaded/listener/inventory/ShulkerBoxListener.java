package com.hazebyte.crate.cratereloaded.listener.inventory;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.crate.CrateRegistrar;
import com.hazebyte.crate.api.util.Messenger;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.listener.Condition;
import com.hazebyte.crate.cratereloaded.util.PlayerUtil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

/** Created by wixu on 7/2/17. */
@Condition("shulker")
public class ShulkerBoxListener implements Listener {

    private final PluginSettingComponent settings;

    public ShulkerBoxListener(PluginSettingComponent settings) {
        this.settings = settings;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onHopperMove(InventoryMoveItemEvent event) {
        if (event.getDestination().getType() != InventoryType.SHULKER_BOX) {
            return;
        }

        CrateRegistrar handler = CorePlugin.getPlugin().getCrateRegistrar();
        if (handler.isCrate(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onBoxInteraction(InventoryClickEvent event) {
        CrateRegistrar handler = CorePlugin.getPlugin().getCrateRegistrar();
        HumanEntity entity = event.getWhoClicked();
        Player player = (entity instanceof Player) ? (Player) entity : null;

        if (event.getInventory().getType() != InventoryType.SHULKER_BOX) {
            return;
        }

        if (!settings.isShulkerInteractionEnabled()) {
            return;
        }

        if (event.getClick() == ClickType.NUMBER_KEY) {
            int hotbarButton = event.getHotbarButton();
            ItemStack hotbarItem = event.getInventory().getItem(hotbarButton);

            boolean isCrate = handler.isCrate(event.getCurrentItem())
                    || handler.isCrate(event.getCursor())
                    || handler.isCrate(hotbarItem);

            if (!isCrate) return;

            event.setCancelled(true);

            if (player != null) {
                PlayerUtil.closeInventoryLater(player, 0);
                Messenger.tell(player, CrateAPI.getMessage("core.invalid_action_shulker"));
            }
        }

        boolean isCrate = (handler.isCrate(event.getCurrentItem()));
        if (!isCrate) {
            return;
        }

        event.setCancelled(true);

        if (player != null) {
            PlayerUtil.closeInventoryLater(player, 0);
            Messenger.tell(player, CrateAPI.getMessage("core.invalid_action_shulker"));
        }
    }
}
