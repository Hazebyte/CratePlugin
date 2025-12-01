package com.hazebyte.crate.cratereloaded.menuV2;

import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class InventoryButtonV2 {

    // supports null player
    @NonNull
    private Function<Player, ItemStack> itemCreator;

    @Nullable
    private Consumer<InventoryClickEvent> clickHandler;

    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (clickHandler != null) {
            clickHandler.accept(event);
        }
    }
}
