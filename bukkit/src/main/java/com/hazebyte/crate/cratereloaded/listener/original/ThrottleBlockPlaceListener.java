package com.hazebyte.crate.cratereloaded.listener.original;

import com.hazebyte.crate.api.crate.Crate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * The throttle may interfere with crate interactions. This listener prevents the crate from being
 * placed
 */
public class ThrottleBlockPlaceListener extends CrateListener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRequestPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Crate crate = chandler.getCrate(event.getItemInHand());

        if (crate == null) {
            return;
        }

        if (!crate.isPlaceable()) {
            event.setCancelled(true);
        }
    }
}
