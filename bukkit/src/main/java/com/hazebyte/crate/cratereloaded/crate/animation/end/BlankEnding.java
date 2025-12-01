package com.hazebyte.crate.cratereloaded.crate.animation.end;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.crate.animation.AnimationTask;
import com.hazebyte.crate.cratereloaded.crate.animation.BaseScroller;
import com.hazebyte.crate.cratereloaded.crate.animation.Ending;
import java.util.Collections;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlankEnding extends Ending {
    public BlankEnding(Crate crate, PluginSettingComponent settings) {
        super(crate, settings);
        this.length = 10;
    }

    public BlankEnding(Crate crate, int length, PluginSettingComponent settings) {
        super(crate, length, settings);
    }

    @Override
    public void startClosing(Player player, Location location, Reward reward, Inventory inventory) {
        BlankEndingTask task =
                new BlankEndingTask(this, inventory, player, this.length, location, Collections.singletonList(reward));
        task.runTaskLater(CorePlugin.getPlugin(), this.length);
    }

    @Override
    public void startClosing(Player player, Location location, List<Reward> rewards, Inventory inventory) {
        BlankEndingTask task = new BlankEndingTask(this, inventory, player, this.length, location, rewards);
        task.runTaskLater(CorePlugin.getPlugin(), this.length);
    }

    @Override
    public AnimationTask task(Inventory inventory, Player player, List<Reward> rewards, Location location) {
        return null;
    }

    @Override
    public AnimationTask task(AnimationTask task) {
        return new BlankEndingTask(task);
    }

    private class BlankEndingTask extends AnimationTask {

        public BlankEndingTask(
                BaseScroller parent,
                Inventory inventory,
                Player player,
                int length,
                Location location,
                List<Reward> rewards) {
            super(parent, inventory, player, length, location);
            this.rewards = rewards;
        }

        public BlankEndingTask(AnimationTask previous) {
            super(previous);
        }

        public void focus(Inventory inventory) {
            ItemStack[] contents = inventory.getContents();
            ItemStack glass = getDisplay();
            java.util.stream.IntStream.range(0, contents.length)
                    .filter(i -> i != (contents.length / 2))
                    .forEach(i -> contents[i] = glass);
            inventory.setContents(contents);
        }

        @Override
        public void run() {
            this.focus(inventory);
            onReward(player, location, this.rewards);
        }

        @Override
        public void update(Player player, Inventory inventory, List<Reward> rewards) {}
    }
}
