package com.hazebyte.crate.cratereloaded.crate.animationV2;

import com.hazebyte.crate.cratereloaded.crate.animationV2.events.AnimationCloseEvent;
import com.hazebyte.crate.cratereloaded.crate.animationV2.events.AnimationFrameChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class AnimationListener implements Listener {

    private AnimationManager animationManager;

    public AnimationListener(AnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryCloseWhileAnimationInProgress(InventoryCloseEvent event) {
        this.animationManager.onInventoryClose(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAnimationClose(AnimationCloseEvent event) {
        this.animationManager.onAnimationClose(event);
    }

    @EventHandler
    public void onFrameChange(AnimationFrameChangeEvent event) {
        this.animationManager.onFrameChange(event);
    }
}
