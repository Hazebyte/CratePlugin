package com.hazebyte.crate.cratereloaded.crate.animationV2;

import com.hazebyte.crate.cratereloaded.model.CrateV2;
import com.hazebyte.crate.cratereloaded.model.RewardV2;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;
import org.bukkit.entity.Player;

@Data
@Builder
public class Animation {

    @NonNull
    private CrateV2 crateV2;

    @NonNull
    private Player player;

    @NonNull
    @Singular
    private List<RewardV2> winningRewards;

    @NonNull
    @Singular
    private List<AnimationFrame> frames;
}
