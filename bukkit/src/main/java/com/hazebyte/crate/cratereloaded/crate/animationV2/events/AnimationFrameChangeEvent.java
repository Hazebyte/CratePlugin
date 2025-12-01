package com.hazebyte.crate.cratereloaded.crate.animationV2.events;

import com.hazebyte.crate.cratereloaded.crate.animationV2.Animation;
import com.hazebyte.crate.cratereloaded.menuV2.InventoryV2;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimationFrameChangeEvent extends Event {

    @NonNull
    private Animation animation;

    @NonNull
    private InventoryV2 inventoryV2;

    @NonNull
    private int currentFrameIndex;

    private static HandlerList handlerList = new HandlerList();

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
