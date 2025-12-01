package com.hazebyte.crate.cratereloaded.crate.animation;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

public class AnimationMenuListener implements Listener {
    private static final AnimationMenuListener INSTANCE = new AnimationMenuListener();
    private Plugin plugin = null;

    public static AnimationMenuListener getInstance() {
        return INSTANCE;
    }

    public static void handleAnimationRewards(Player player, boolean closeInventory) {
        if (player.getOpenInventory() != null) {
            Inventory inventory = player.getOpenInventory().getTopInventory();
            if (inventory.getHolder() instanceof AnimationHolder) {
                AnimationHolder holder = (AnimationHolder) inventory.getHolder();
                holder.getMenu().onDisable(holder.getRewards(), holder.getConstantRewards(), player);
                holder.getMenu().removePlayerFromOpening(player);
                if (closeInventory) {
                    player.closeInventory();
                }
            }
        }
    }

    public static void closeOpenMenus(PluginDisableEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            handleAnimationRewards(player, true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        handleAnimationRewards(player, false);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        handleAnimationRewards(player, false);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() instanceof AnimationHolder) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof AnimationHolder) {
            //            boolean allowInventoryExit = CorePlugin.getPlugin().getSettings().allowsInventoryExiting();
            boolean allowInventoryExit = false;
            if (!allowInventoryExit) {
                ((AnimationHolder) event.getInventory().getHolder()).getMenu().onInventoryClose(event);
            } else {
                handleAnimationRewards((Player) event.getPlayer(), false);
            }
        }
    }

    public void register(JavaPlugin plugin) {
        if (!isRegistered(plugin)) {
            plugin.getServer().getPluginManager().registerEvents(INSTANCE, plugin);
            this.plugin = plugin;
        }
    }

    public boolean isRegistered(JavaPlugin plugin) {
        if (this.plugin == null) return false;

        if (this.plugin.equals(plugin)) {
            for (RegisteredListener listener : HandlerList.getRegisteredListeners(plugin)) {
                if (listener.getListener().equals(INSTANCE)) {
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPluginEnd(PluginDisableEvent event) {
        if (event.getPlugin().equals(plugin)) {
            closeOpenMenus(event);
            plugin = null;
        }
    }
}
