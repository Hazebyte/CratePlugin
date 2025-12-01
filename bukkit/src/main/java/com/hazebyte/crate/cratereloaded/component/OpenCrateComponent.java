package com.hazebyte.crate.cratereloaded.component;

import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.api.result.RewardExecutorResult;
import com.hazebyte.crate.cratereloaded.component.model.CrateOpenRequest;
import com.hazebyte.crate.cratereloaded.component.model.CrateOpenResponse;
import com.hazebyte.crate.cratereloaded.model.CrateV2;
import com.hazebyte.crate.cratereloaded.model.RewardV2;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import org.bukkit.entity.Player;

public interface OpenCrateComponent {

    CrateOpenResponse openCrate(@NonNull CrateOpenRequest request);

    Set<RewardExecutorResult> executeReward(@NonNull Player player, @NonNull Reward reward);

    void executeRewardV2(@NonNull Player player, @NonNull RewardV2 rewardV2);

    void executeCrateV2Message(@NonNull Player player, @NonNull CrateV2 crateV2, @NonNull List<Reward> rewards);
}
