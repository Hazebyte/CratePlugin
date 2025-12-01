package com.hazebyte.crate.cratereloaded.crate.animation;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class Ending extends BaseScroller {

    public Ending(Crate crate, PluginSettingComponent settings) {
        super(crate, settings);
    }

    public Ending(Crate crate, int length, PluginSettingComponent settings) {
        super(crate, length, settings);
    }

    @Override
    public Inventory openCrate(Player player, Location location) {
        return null;
    }

    public abstract void startClosing(Player player, Location location, Reward reward, Inventory inventory);

    public abstract void startClosing(Player player, Location location, List<Reward> reward, Inventory inventory);
}
