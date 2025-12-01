package com.hazebyte.crate.cratereloaded.listener.original;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The throttle may allow players to break blocks with crates in hand. This listener prevents the
 * player from breaking blocks with crates. This listener also prevents players from breaking blocks
 * of key crates.
 */
public class ThrottleBlockBreakListener extends CrateListener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRequestPlace(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = PlayerUtil.getItemInMainHand(player);
        Crate crate = chandler.getCrate(item);

        if (crate != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKeyCrateBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        if (bhandler.hasCrates((event.getBlock().getLocation()))) {
            event.setCancelled(true);
        }
    }
}
