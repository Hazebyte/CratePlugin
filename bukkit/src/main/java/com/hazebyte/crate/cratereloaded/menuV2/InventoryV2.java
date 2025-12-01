package com.hazebyte.crate.cratereloaded.menuV2;

import static com.hazebyte.crate.cratereloaded.util.InventoryConstants.SIX_ROWS;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class InventoryV2 implements InventoryHandler {

    @NonNull
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Map<Integer, InventoryButtonV2> buttons = new HashMap<>();

    @NonNull
    private Integer inventorySize;

    @NonNull
    private String title;

    @NonNull
    @Singular
    @EqualsAndHashCode.Exclude
    private List<InventoryDecorator> decorators;

    public void addButton(@NonNull InventoryButtonV2 button) {
        int nextFreeSlot = 0;
        while (buttons.containsKey(nextFreeSlot) && nextFreeSlot++ < SIX_ROWS - 1) {
            nextFreeSlot++;
        }
        setButton(nextFreeSlot, button);
    }

    public void setButton(int slot, @NonNull InventoryButtonV2 button) {
        buttons.put(slot, button);
        Optional<Inventory> optional =
                CorePlugin.getJavaPluginComponent().getInventoryManager().getInventory(this);
        optional.ifPresent(
                inventory -> inventory.setItem(slot, button.getItemCreator().apply(null)));
    }

    @Override
    public void onClick(@NonNull InventoryClickEvent event) {
        if (buttons.containsKey(event.getSlot())) {
            buttons.get(event.getSlot()).onClick(event);
        }
    }
}
