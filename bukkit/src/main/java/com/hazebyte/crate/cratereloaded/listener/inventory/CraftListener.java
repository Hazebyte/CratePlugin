package com.hazebyte.crate.cratereloaded.listener.inventory;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.listener.PluginListener;
import com.hazebyte.crate.cratereloaded.util.PlayerUtil;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class CraftListener extends PluginListener {

    private final PluginSettingComponent settings;

    public CraftListener(PluginSettingComponent settings) {
        this.settings = settings;
    }

    @EventHandler
    public void onCrateCraft(CraftItemEvent event) {
        CraftingInventory inventory = event.getInventory();
        HumanEntity entity = event.getWhoClicked();
        ItemStack[] contents = inventory.getMatrix();

        boolean craftingAllowed = settings.isCraftingCratesEnabled();
        if (craftingAllowed) {
            return;
        }

        if (entity instanceof Player) {
            Player player = (Player) entity;
            for (ItemStack content : contents) {
                Crate crate = CrateAPI.getCrateRegistrar().getCrate(content);
                if (crate != null) {
                    cancel(event, entity, "core.invalid_crate_craft", crate);
                    PlayerUtil.refreshInventory(player);
                    event.setCancelled(true);
                }
            }
        }
    }
}
