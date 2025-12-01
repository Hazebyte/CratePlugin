package com.hazebyte.crate.cratereloaded.menu.buttons;

import com.hazebyte.crate.api.util.ItemBuilder;
import com.hazebyte.crate.cratereloaded.menu.Button;
import com.hazebyte.crate.cratereloaded.menu.ItemClickEvent;
import com.hazebyte.util.Mat;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class CloseMenuButton extends Button {

    public CloseMenuButton() {
        super(new ItemBuilder(Mat.MUSIC_DISC_11.toMaterial())
                .displayName(ChatColor.RED + "Close")
                .asItemStack());
    }

    public CloseMenuButton(ItemStack item) {
        super(item);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillClose(true);
    }
}
