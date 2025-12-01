package com.hazebyte.crate.cratereloaded.menuV2;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.java.Log;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.server.PluginDisableEvent;

@Log
public class InventoryHistoryManager {

    private Map<Player, Deque<InventoryV2>> inventoryDeque;

    public InventoryHistoryManager() {
        inventoryDeque = new HashMap<>();
    }

    public void handleOpen(InventoryOpenEvent event) {
        final Player player = (Player) event.getPlayer();
        //    Optional<InventoryV2> inventoryV2 = CorePlugin.getJavaPluginComponent()
        //        .getInventoryManager()
        //        .getInventoryV2(event.getInventory());
        //    if (inventoryV2.isPresent()) {
        //      inventoryDeque.putIfAbsent(player, new ArrayDeque<>());
        //      inventoryDeque.get(player).add(inventoryV2.get());
        //    }
    }

    public void handleClose(InventoryCloseEvent event) {
        inventoryDeque.remove(event.getPlayer());
    }

    public void handlePluginDisable(PluginDisableEvent event) {
        inventoryDeque.clear();
    }
}
