package com.hazebyte.crate.cratereloaded.listener.original;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.CrateAction;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.api.event.CrateInteractEvent;
import com.hazebyte.crate.api.util.Messenger;
import com.hazebyte.crate.cratereloaded.util.PlayerUtil;
import com.hazebyte.crate.cratereloaded.util.format.CustomFormat;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/** This handles the PlayerInteract event for key crates */
public class KeyListener extends CrateListener {

    /** This handles when a player interacts with a key crate. */
    @EventHandler(priority = EventPriority.HIGH)
    public void onKeyCrateInteract(PlayerInteractEvent event) {
        if (!check(event)) {
            return;
        }

        Player player = event.getPlayer();

        // Get the item in hand
        ItemStack item = PlayerUtil.getItemInMainHand(player);

        Block block = event.getClickedBlock();
        Crate crate = chandler.getCrate(item);
        Crate crateAtBlock = block != null ? bhandler.getFirstCrate(block.getLocation()) : null;

        // The player didn't actually have a crate nor was he clicking on a crate
        if (crate == null && crateAtBlock == null) {
            return;
        }

        // If either the crate or crateAtBlock is not a key crate
        if ((crate == null || crate.getType() != CrateType.KEY)
                && (crateAtBlock == null || crateAtBlock.getType() != CrateType.KEY)) {
            return;
        }

        // We have a key crate in mind right now

        if (event.isCancelled()) {
            return;
        }

        // At this point, at most one can be null not both.
        Crate nonNullCrate = crateAtBlock != null ? crateAtBlock : crate;

        // If the player does not have any free slots and it's an open action
        if (PlayerUtil.getSlotsLeft(player) <= 0 && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            String message = CrateAPI.getMessage("core.inventory_insufficient_space");
            Messenger.tell(player, CustomFormat.format(message, nonNullCrate, player));
            event.setCancelled(true);
            return;
        }

        CrateAction action = toCrateAction(nonNullCrate, event.getAction());
        if (action == null) {
            return;
        }

        CrateInteractEvent interactEvent = new CrateInteractEvent(
                crate != null ? crate : crateAtBlock, player, block.getLocation(), action, event.getAction());

        Bukkit.getServer().getPluginManager().callEvent(interactEvent);
        event.setCancelled(true);
    }
}
