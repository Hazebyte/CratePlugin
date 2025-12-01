package com.hazebyte.crate.cratereloaded.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class BaseHolder implements InventoryHolder {

    private final Inventory inventory;

    public BaseHolder(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
