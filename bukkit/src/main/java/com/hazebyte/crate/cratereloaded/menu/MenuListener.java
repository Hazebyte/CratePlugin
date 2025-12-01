package com.hazebyte.crate.cratereloaded.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuListener implements Listener {
    private static final MenuListener INSTANCE = new MenuListener();
    private Plugin plugin = null;

    private MenuListener() {}

    public static MenuListener getInstance() {
        return INSTANCE;
    }

    public static void closeOpenMenus() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            InventoryView inventoryView = player.getOpenInventory();
            if (inventoryView != null) {
                Inventory inventory = inventoryView.getTopInventory();
                if (inventory.getHolder() instanceof MenuHolder) {
                    player.closeInventory();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() instanceof MenuHolder) {
            event.setCancelled(true);
            ((MenuHolder) event.getInventory().getHolder()).getMenu().onInventoryClick(event);
        }
    }

    public void register(JavaPlugin plugin) {
        if (!isRegistered(plugin)) {
            plugin.getServer().getPluginManager().registerEvents(INSTANCE, plugin);
            this.plugin = plugin;
        }
    }

    public boolean isRegistered(JavaPlugin plugin) {
        if (plugin.equals(this.plugin)) {
            for (RegisteredListener listener : HandlerList.getRegisteredListeners(plugin)) {
                if (listener.getListener().equals(INSTANCE)) {
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(plugin)) {
            closeOpenMenus();
            plugin = null;
        }
    }
}
