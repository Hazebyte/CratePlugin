package com.hazebyte.crate.cratereloaded.listener.original;

import com.hazebyte.crate.cratereloaded.util.Camera;
import com.hazebyte.crate.cratereloaded.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerAnimationListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockCrate(PlayerAnimationEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.ADVENTURE) {
            Block block = Camera.getTargetBlock(player, 6);
            PlayerInteractEvent call = new PlayerInteractEvent(
                    player, Action.LEFT_CLICK_BLOCK, PlayerUtil.getItemInHand(player), block, block.getFace(block));
            Bukkit.getServer().getPluginManager().callEvent(call);
        }
    }
}
