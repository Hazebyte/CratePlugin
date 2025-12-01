package com.hazebyte.crate.cratereloaded.component;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.model.CrateV2;
import com.hazebyte.crate.cratereloaded.model.RewardV2;
import java.util.List;
import org.bukkit.entity.Player;

public interface GenerateCratePrizeComponent {

    List<Reward> generateCratePrize(
            Crate crate, Player player, int amountOfPrizes, boolean overrideUniqueRule, boolean addConstantRewards);

    RewardV2 generateRewardForAnimation(Player player, CrateV2 crateV2);
}
