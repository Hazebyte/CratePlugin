package com.hazebyte.crate.cratereloaded.menu.buttons;

import com.hazebyte.crate.cratereloaded.menu.Button;
import com.hazebyte.crate.cratereloaded.menu.ItemClickEvent;
import com.hazebyte.crate.cratereloaded.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PageButton extends Button {

    private final JavaPlugin plugin;
    private final Menu menu;

    public PageButton(JavaPlugin plugin, ItemStack item, Menu menu) {
        super(item);
        this.plugin = plugin;
        this.menu = menu;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> menu.open(event.getPlayer()), 3);
    }
}
