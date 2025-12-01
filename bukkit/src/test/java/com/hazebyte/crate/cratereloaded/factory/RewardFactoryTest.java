package com.hazebyte.crate.cratereloaded.factory;

import com.hazebyte.crate.BukkitTest;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.api.util.ItemBuilder;
import com.hazebyte.crate.cratereloaded.model.CrateImpl;
import com.hazebyte.crate.cratereloaded.util.RewardFactory;
import org.bukkit.Material;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RewardFactoryTest extends BukkitTest {

    private Crate crate;

    @BeforeEach
    public void setup() {
        crate = new CrateImpl("test", CrateType.KEY);
        crate.setDisplayItem(new ItemBuilder(Material.STONE, 1).asItemStack());
    }

    @Test
    public void createReward_returnsEntry() {
        Reward reward = RewardFactory.createReward(crate, server.addPlayer(), 1);

        Assertions.assertNotNull(reward);
        Assertions.assertEquals(reward.getCommands().size(), 1);
        Assertions.assertEquals(reward.getDisplayItem(), crate.getDisplayItem());
    }

    @Test
    public void createReward_fails_withAmountLessThanZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RewardFactory.createReward(crate, server.addPlayer(), -1);
        });
    }
}
