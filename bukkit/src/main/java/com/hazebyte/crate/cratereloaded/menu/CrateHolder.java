package com.hazebyte.crate.cratereloaded.menu;

import com.hazebyte.crate.api.crate.Crate;
import org.bukkit.inventory.Inventory;

public class CrateHolder extends MenuHolder {

    private final Crate crate;

    public CrateHolder(Menu menu, Inventory inventory, Crate crate) {
        super(menu, inventory);
        this.crate = crate;
    }

    public Crate getCrate() {
        return this.crate;
    }
}
