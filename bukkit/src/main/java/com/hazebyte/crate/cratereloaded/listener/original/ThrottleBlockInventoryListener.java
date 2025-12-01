package com.hazebyte.crate.cratereloaded.listener.original;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

/** This prevents any location with a registered crate to be have it's inventory be opened. */
public class ThrottleBlockInventoryListener extends CrateListener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryRequest(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        boolean hasCrate = bhandler.hasCrates(block.getLocation());

        if (!hasCrate) {
            return;
        }

        if (block.getState() instanceof InventoryHolder) {
            event.setCancelled(true);
        }
    }
}
