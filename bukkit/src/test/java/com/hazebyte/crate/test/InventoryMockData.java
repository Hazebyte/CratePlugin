package com.hazebyte.crate.test;

import org.bukkit.inventory.ItemStack;

public class InventoryMockData {

    private static final int INV_SIZE = 41;

    public static ItemStack[] ONE_STONE_INVENTORY = createMockDataFromItem(ItemStackMockData.ONE_STONE);
    public static ItemStack[] FULL_STONE_INVENTORY = createMockDataFromItem(ItemStackMockData.ONE_INVENTORY_OF_STONE);

    public static ItemStack[] ONE_MINECART_INVENTORY = createMockDataFromItem(ItemStackMockData.ONE_MINECART);
    public static ItemStack[] FULL_MINECART_INVENTORY =
            createMockDataFromItem(ItemStackMockData.ONE_INVENTORY_OF_MINECART);

    private static ItemStack[] createMockDataFromItem(ItemStack itemStack) {
        ItemStack[] contents = new ItemStack[INV_SIZE];
        int amount = itemStack.getAmount();

        for (int i = 0; i < contents.length; i++) {
            int itemSize = Math.min(amount, 64);
            if (itemSize <= 0) {
                break;
            }

            ItemStack clone = itemStack.clone();
            clone.setAmount(itemSize);
            amount -= itemSize;
            contents[i] = clone;
        }

        return contents;
    }
}
