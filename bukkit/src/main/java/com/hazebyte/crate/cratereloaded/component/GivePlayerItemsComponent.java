package com.hazebyte.crate.cratereloaded.component;

import com.hazebyte.crate.cratereloaded.model.GiveItemExecutorResult;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface GivePlayerItemsComponent {

    Set<GiveItemExecutorResult> giveItems(@NonNull List<ItemStack> items, @NonNull Player player);
}
