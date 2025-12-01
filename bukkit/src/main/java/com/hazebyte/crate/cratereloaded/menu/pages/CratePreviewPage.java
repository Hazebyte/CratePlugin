package com.hazebyte.crate.cratereloaded.menu.pages;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.api.util.ItemBuilder;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.menu.Button;
import com.hazebyte.crate.cratereloaded.menu.Menu;
import com.hazebyte.crate.cratereloaded.menu.Size;
import com.hazebyte.util.Mat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.inventory.ItemStack;

public class CratePreviewPage extends PaginationPage {

    private final Crate crate;

    public CratePreviewPage(Crate crate, Size size, PluginSettingComponent settings) {
        this(crate, size, null, settings);
    }

    public CratePreviewPage(Crate crate, Size size, Menu parent, PluginSettingComponent settings) {
        super(CorePlugin.getPlugin(), crate.getDisplayName(), size, settings);
        this.crate = crate;
        this.setParent(parent);
        this.addItems();
    }

    @Override
    public void addItems() {
        List<Reward> rewards = Stream.concat(crate.getConstantRewards().stream(), crate.getRewards().stream())
                .collect(Collectors.toList());
        rewards.forEach(reward -> {
            ItemStack item = reward.getDisplayItem();
            if (item == null) {
                String line = reward.getLine().toString();
                item = ItemBuilder.of(Mat.RED_WOOL.toItemStack())
                        .displayName("&4Invalid Button")
                        .lore(line)
                        .asItemStack();
            }
            addToQueue(new Button(item));
        });
        super.addItems();
    }
}
