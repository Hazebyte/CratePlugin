package com.hazebyte.crate.cratereloaded.util;

import com.hazebyte.crate.cratereloaded.util.item.ItemUtil;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class RewardDisplayItemFactory {

    public static ItemStack createDisplayItem(ItemStack displayItem, int amount) {
        ItemStack clonedDisplay = displayItem.clone(); // defensive copying
        clonedDisplay.setAmount(Math.min(64, amount));
        if (amount > 64) {
            List<String> lore = ItemUtil.getLore(clonedDisplay);
            lore.add(String.format("&7Items: &6%d", amount));
            ItemUtil.setLore(clonedDisplay, lore);
        }
        return clonedDisplay;
    }
}
