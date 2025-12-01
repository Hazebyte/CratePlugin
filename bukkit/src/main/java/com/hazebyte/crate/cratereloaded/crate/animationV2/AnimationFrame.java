package com.hazebyte.crate.cratereloaded.crate.animationV2;

import java.util.Map;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.bukkit.inventory.ItemStack;

@Data
@Builder
public class AnimationFrame {

    @Singular
    private Map<Integer, ItemStack> itemMappings;

    private long frameLength;
}
