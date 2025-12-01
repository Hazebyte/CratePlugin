package com.hazebyte.crate.cratereloaded.menu;

import com.hazebyte.crate.api.util.ItemBuilder;
import com.hazebyte.crate.cratereloaded.util.format.CustomFormat;
import com.hazebyte.crate.cratereloaded.util.item.ItemUtil;
import com.hazebyte.util.Mat;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Button {
    private final ItemStack DEFAULT = ItemBuilder.of(Mat.RED_WOOL.toItemStack())
            .displayName("&l&aButton: &fInvalid Item")
            .asItemStack();
    private ItemStack item;

    public Button() {
        this.item = DEFAULT;
    }

    public Button(ItemStack item) {
        this.item = item;
        if (item == null) this.item = DEFAULT;
    }

    public void setIcon(ItemStack item) {
        this.item = item;
    }

    public ItemStack getIcon() {
        return item;
    }

    public ItemStack getFinalIcon(Player player) {
        ItemStack item = this.item.clone();
        if (ItemUtil.hasName(item)) {
            String name = "&r" + CustomFormat.format(ItemUtil.getName(item), player);
            ItemUtil.setName(item, name);
        }
        if (ItemUtil.hasLore(item)) {
            List<String> lore = CustomFormat.format(ItemUtil.getLore(item), player);
            ItemUtil.setLore(item, lore);
        }
        return item;
    }

    public void onItemClick(ItemClickEvent event) {
        // Do nothing
    }
}
