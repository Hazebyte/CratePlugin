package com.hazebyte.crate.cratereloaded.util.format;

import com.hazebyte.crate.cratereloaded.util.item.ItemUtil;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class ItemFormatter {

    /**
     * Does not have to use the return value. This will modify the stack as an object reference.
     *
     * @param item
     * @param objects
     */
    public static ItemStack format(ItemStack item, Object... objects) {
        String name = ItemUtil.getName(item);
        List<String> lore = ItemUtil.getLore(item);

        name = CustomFormat.format(name, objects);
        lore = CustomFormat.format(lore, objects);

        ItemUtil.setNameAndLore(item, name, lore);
        return item;
    }
}
