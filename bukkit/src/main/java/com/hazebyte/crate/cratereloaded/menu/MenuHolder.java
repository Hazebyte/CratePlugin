package com.hazebyte.crate.cratereloaded.menu;

import org.bukkit.inventory.Inventory;

public class MenuHolder extends BaseHolder {
    private final Menu menu;

    public MenuHolder(Menu menu, Inventory inventory) {
        super(inventory);
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }
}
