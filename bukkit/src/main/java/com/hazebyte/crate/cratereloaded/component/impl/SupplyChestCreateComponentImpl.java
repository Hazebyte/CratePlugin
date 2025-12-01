package com.hazebyte.crate.cratereloaded.component.impl;

import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.api.effect.Category;
import com.hazebyte.crate.api.event.CrateRewardEvent;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.SupplyChestCreateComponent;
import com.hazebyte.crate.cratereloaded.component.model.CrateOpenRequest;
import com.hazebyte.crate.cratereloaded.component.model.CrateOpenResponse;
import com.hazebyte.crate.cratereloaded.model.CrateImpl;
import com.hazebyte.crate.cratereloaded.model.RewardImpl;
import com.hazebyte.crate.cratereloaded.util.CommandUtil;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SupplyChestCreateComponentImpl implements SupplyChestCreateComponent {

    private final CorePlugin plugin;

    @Inject
    public SupplyChestCreateComponentImpl(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public CrateOpenResponse createChest(CrateOpenRequest crateOpenRequest) {
        // Convert to CrateImpl for supply chest (temporary - uses legacy methods)
        CrateImpl crate = (CrateImpl) crateOpenRequest.getCrateOrConvert();
        Player player = crateOpenRequest.getPlayer();
        Location location = crateOpenRequest.getLocation();
        Block block = location.getBlock();

        if (!(block.getState() instanceof Chest)) {
            return CrateOpenResponse.builder().build();
        }

        Chest chest = (Chest) block.getState();
        Inventory chestInventory = chest.getBlockInventory();
        List<Reward> rewards = plugin.getCrateRegistrar().generateCrateRewards(crate, player);

        CrateRewardEvent event = new CrateRewardEvent(crate, player, location, rewards);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!(event.isCancelled())) {
            crate.runEffect(block.getLocation(), Category.OPEN, player);
            this.onRewards(crate, player, chestInventory, rewards);
        }
        return CrateOpenResponse.builder().build();
        //    return !(event.isCancelled());
    }

    private void onRewards(CrateImpl crate, Player player, Inventory inventory, List<Reward> rewards) {
        crate.runMessage(player, rewards);
        for (Reward reward : rewards) {
            for (ItemStack item : reward.getItems()) {
                int slot = getEmptySlot(inventory);
                if (slot >= 0) {
                    inventory.setItem(slot, item.clone());
                }
            }
            CommandUtil.run(reward.getCommands(player));
            ((RewardImpl) reward).runMessage(player);
        }
    }

    /**
     * Find the first empty slot
     *
     * @param inventory
     * @return The index of a random empty slot otherwise -1
     */
    private int getEmptySlot(Inventory inventory) {
        List<Integer> emptySlots = new ArrayList<>();
        // Find all empty spots
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) emptySlots.add(i);
        }

        // Returns -1 if there are no empty slots
        if (emptySlots.size() == 0) return -1;

        // Returns a random empty slot
        int randomIndex = (int) (Math.random() * emptySlots.size());
        return emptySlots.get(randomIndex);
    }
}
