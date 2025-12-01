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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/** This handles the PlayerInteractEvent for mystery crates */
public class MysteryListener extends CrateListener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onMysteryCrateInteract(PlayerInteractEvent event) {
        if (!check(event)) {
            return;
        }

        Player player = event.getPlayer();

        // Get the item in hand
        ItemStack item = PlayerUtil.getItemInMainHand(player);

        Crate crate = chandler.getCrate(item);

        // The player does not have a crate
        if (crate == null) {
            return;
        }

        if (crate.getType() != CrateType.MYSTERY) {
            return;
        }

        // We have a mystery crate at this point

        boolean ignoreCancelled =
                event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR;

        // If it is a AIR click, we can continue executing
        // Otherwise, we should cancel this event
        if (!ignoreCancelled && event.isCancelled()) {
            return;
        }

        // If the player does not have any free slots and it's an open action
        if (PlayerUtil.getSlotsLeft(player) <= 0) {
            String message = CrateAPI.getMessage("core.inventory_insufficient_space");
            Messenger.tell(player, CustomFormat.format(message, crate, player));
            event.setCancelled(true);
            return;
        }

        CrateAction action = toCrateAction(crate, event.getAction());
        if (action == null) {
            return;
        }

        CrateInteractEvent interactEvent = new CrateInteractEvent(crate, player, action, event.getAction());

        Bukkit.getServer().getPluginManager().callEvent(interactEvent);
        event.setCancelled(true);
    }
}
