package com.hazebyte.crate.cratereloaded.crate.animationV2.prebuilt;

import com.hazebyte.crate.cratereloaded.crate.animationV2.Animation;
import com.hazebyte.crate.cratereloaded.model.CrateV2;
import lombok.NonNull;
import org.bukkit.entity.Player;

public interface AnimationGenerator {

    @NonNull
    Animation createAnimation(@NonNull Player player, @NonNull CrateV2 crateV2);
}
