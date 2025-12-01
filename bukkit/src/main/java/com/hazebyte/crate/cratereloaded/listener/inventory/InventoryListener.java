package com.hazebyte.crate.cratereloaded.listener.inventory;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.listener.PluginListener;
import com.hazebyte.crate.cratereloaded.menu.CrateHolder;
import com.hazebyte.crate.cratereloaded.util.PlayerUtil;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class InventoryListener extends PluginListener {

    private final PluginSettingComponent settings;

    public InventoryListener(PluginSettingComponent settings) {
        this.settings = settings;
    }

    /**
     * Sees if a player interacts with a preview menu for a crate. If the action is of type
     * SHIFT_CLICK, the action will be cancelled and the inventory will be closed.
     *
     * @param event InventoryClickEvent
     */
    @EventHandler
    public void onPreviewShiftClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();
        InventoryAction action = event.getAction();
        HumanEntity entity = event.getWhoClicked();

        if (holder instanceof CrateHolder) {
            CrateHolder crateHolder = (CrateHolder) holder;
            Crate crate = crateHolder.getCrate();

            if (crate.isPreviewable()
                    && (event.isShiftClick()
                            || action == InventoryAction.HOTBAR_MOVE_AND_READD
                            || action == InventoryAction.HOTBAR_SWAP)) {
                cancel(event, entity, "core.invalid_action", crate);
            }
        }
    }

    @EventHandler
    public void onItemCraftOrHotbar(InventoryClickEvent event) {
        HumanEntity entity = event.getWhoClicked();
        Player player = entity instanceof Player ? (Player) entity : null;
        if (player == null) return;

        Inventory inventory = event.getInventory();
        InventoryType type = inventory.getType();
        ItemStack item = event.getCurrentItem();
        InventoryAction action = event.getAction();

        // Does not have to check for valid crate.
        // This disables all hotbar actions. MC returns the item that our cursor is on, not the hotbar
        // item.
        if (event.getHotbarButton() >= 0) {

            ItemStack firstCheck = player.getInventory().getContents()[event.getHotbarButton()];
            Crate crate = CrateAPI.getCrateRegistrar().getCrate(firstCheck);
            if (crate != null && !settings.isHotbarInteractionEnabled()) {
                cancel(event, entity, "core.invalid_action", crate);
                PlayerUtil.refreshInventory(player);
                return;
            }
        }

        boolean craftingAllowed = settings.isCraftingCratesEnabled();
        Crate crate = CrateAPI.getCrateRegistrar().getCrate(item);
        if (crate != null) {
            if (!craftingAllowed && isCraftingType(type)) {
                cancel(event, entity, "core.invalid_crate_craft", crate);
                PlayerUtil.refreshInventory(player);
            }
        }
    }

    private static final Set<String> blockedCraftingTypes = new HashSet<>();

    static {
        blockedCraftingTypes.add(InventoryType.ANVIL.name());
        blockedCraftingTypes.add(InventoryType.BREWING.name());
        blockedCraftingTypes.add(InventoryType.ENCHANTING.name());
        blockedCraftingTypes.add(InventoryType.WORKBENCH.name());
        blockedCraftingTypes.add(InventoryType.FURNACE.name());
        blockedCraftingTypes.add("GRINDSTONE");
    }

    private boolean isCraftingType(InventoryType type) {
        return blockedCraftingTypes.contains(type.name());
    }
}
