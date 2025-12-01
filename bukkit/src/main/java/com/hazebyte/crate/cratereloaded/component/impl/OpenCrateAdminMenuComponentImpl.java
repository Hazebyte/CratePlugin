package com.hazebyte.crate.cratereloaded.component.impl;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.OpenCrateAdminMenuComponent;
import com.hazebyte.crate.cratereloaded.crate.CrateHandler;
import com.hazebyte.crate.cratereloaded.menuV2.InventoryManager;
import com.hazebyte.crate.cratereloaded.menuV2.InventoryV2;
import com.hazebyte.crate.cratereloaded.model.CrateV2;
import com.hazebyte.crate.cratereloaded.model.menuV2.InventoryMenusV2;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.bukkit.entity.Player;

public class OpenCrateAdminMenuComponentImpl implements OpenCrateAdminMenuComponent {

    private final CorePlugin plugin;
    private final InventoryManager inventoryManager;

    @Inject
    public OpenCrateAdminMenuComponentImpl(CorePlugin plugin, InventoryManager inventoryManager) {
        this.plugin = plugin;
        this.inventoryManager = inventoryManager;
    }

    @Override
    public InventoryV2 openListCrateAdminMenu(Player player) {
        // Get CrateV2 directly from CrateHandler (no conversion needed!)
        CrateHandler crateHandler = (CrateHandler) plugin.getCrateRegistrar();
        List<CrateV2> cratesV2 = new ArrayList<>(crateHandler.getCratesV2());

        InventoryV2 listCratesAdminMenu = InventoryMenusV2.getListCratesAdminMenu(cratesV2);
        inventoryManager.openInventory(listCratesAdminMenu, player);
        return listCratesAdminMenu;
    }
}
