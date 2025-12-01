package com.hazebyte.crate.cratereloaded.model.menuV2;

import com.hazebyte.crate.cratereloaded.menuV2.InventoryDecorator;
import com.hazebyte.crate.cratereloaded.menuV2.InventoryV2;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.entity.Player;

@Data
@Builder
public class InventoryNavigationDecorator implements InventoryDecorator {

    @NonNull
    private InventoryV2 inventoryV2;

    @Override
    public void decorate(Player player) {
        inventoryV2.setButton(inventoryV2.getInventorySize() - 4, InventoryButtonsV2.createCloseButton());
        //    inventoryV2.setButton(inventoryV2.getInventorySize() - 5, Inventory);
        //    CorePlugin.getJavaPluginComponent().getInventoryManager().
    }
}
