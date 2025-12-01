package com.hazebyte.crate.cratereloaded.util;

import com.hazebyte.crate.cratereloaded.util.item.ItemUtil;
import com.hazebyte.crate.utils.NumberGenerator;
import com.hazebyte.util.Mat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public class RandomGlassPaneGenerator {

    private static final List<Mat> panes = new ArrayList<>();

    static {
        for (Mat mat : Mat.values()) {
            if (mat.name().contains("LIGHT_GRAY_STAINED_GLASS")) {
                continue;
            }
            if (mat.name().contains("STAINED_GLASS_PANE")) {
                panes.add(mat);
            }
        }
    }

    public static ItemStack getRandomPane() {
        Mat mat = panes.get(NumberGenerator.range(0, panes.size() - 1));
        ItemStack itemStack = mat.toItemStack();
        ItemUtil.setNameAndLore(itemStack, "", Collections.emptyList()); // TODO: This should be set to the settings
        return itemStack;
    }
}
