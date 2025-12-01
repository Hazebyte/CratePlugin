package com.hazebyte.crate.cratereloaded.crate.animationV2.events;

import com.hazebyte.crate.cratereloaded.crate.animationV2.Animation;
import com.hazebyte.crate.cratereloaded.menuV2.InventoryV2;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimationCloseEvent extends Event {

    @NonNull
    private Animation animation;

    @NonNull
    private InventoryV2 inventoryV2;

    @NonNull
    private Boolean isCompleted;

    @Builder.Default
    private static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
