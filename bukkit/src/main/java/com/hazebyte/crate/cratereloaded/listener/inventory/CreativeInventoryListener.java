package com.hazebyte.crate.cratereloaded.listener.inventory;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.util.Messenger;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.util.format.CustomFormat;
import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CreativeInventoryListener implements Listener {

    private final PluginSettingComponent settings;

    public CreativeInventoryListener(PluginSettingComponent settings) {
        this.settings = settings;
    }

    @EventHandler(priority = EventPriority.HIGH)
    void onInventoryItemDupe(InventoryClickEvent event) {
        ItemStack currentItem = event.getCurrentItem();
        ItemStack cursorItem = event.getCursor();
        HumanEntity entity = event.getWhoClicked();
        Player player = (Player) entity;

        if (player.getGameMode() != GameMode.CREATIVE) return;

        Crate crateA = CrateAPI.getCrateRegistrar().getCrate(currentItem);
        Crate crateB = CrateAPI.getCrateRegistrar().getCrate(cursorItem);

        boolean creativeControl = settings.isCreativeControlEnabled();

        if (crateA == null && crateB == null) {
            return;
        }

        if (!creativeControl) {
            return;
        }

        if (player.isOp()) {
            return;
        }

        event.setCancelled(true);
        entity.closeInventory();
        Messenger.tell(entity, CustomFormat.format(CorePlugin.getPlugin().getMessage("core.invalid_action_creative")));
    }
}
