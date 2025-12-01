package com.hazebyte.crate.test;

import com.hazebyte.crate.api.util.ItemBuilder;
import com.hazebyte.crate.constants.ItemConstants;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackMockData {
    public static final ItemStack ONE_STONE = new ItemBuilder(Material.STONE).asItemStack();
    public static final ItemStack ONE_INVENTORY_OF_STONE =
            new ItemBuilder(Material.STONE, ItemConstants.SLOTS_IN_INVENTORY * 64).asItemStack();
    public static final ItemStack ONE_INVENTORY_OF_STONE_PLUS_ONE_EXTRA =
            new ItemBuilder(Material.STONE, ItemConstants.SLOTS_IN_INVENTORY * 64 + 1).asItemStack();
    public static final ItemStack ONE_MINECART = new ItemBuilder(Material.MINECART).asItemStack();
    public static final ItemStack ONE_INVENTORY_OF_MINECART =
            new ItemBuilder(Material.MINECART, ItemConstants.SLOTS_IN_INVENTORY * 64).asItemStack();
}
