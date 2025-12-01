package com.hazebyte.crate.cratereloaded.component;

import com.hazebyte.crate.cratereloaded.menuV2.InventoryV2;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public interface InventoryComponent {

    void openInventory(@NonNull InventoryV2 inventoryV2, @NonNull Player player);

    void closeAllInventories();

    void handleOpen(InventoryOpenEvent event);

    void handleClick(InventoryClickEvent event);

    void handleClose(InventoryCloseEvent event);
}
