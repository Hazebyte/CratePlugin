package com.hazebyte.crate.cratereloaded.component;

import com.hazebyte.crate.api.crate.AnimationType;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.crate.animation.Animation;

/**
 * Factory component for creating Animation objects with proper timing configuration.
 * Extracts animation creation logic from CrateImpl to remove static CorePlugin dependencies.
 */
public interface AnimationFactoryComponent {

    /**
     * Creates an animation instance for the given type and crate.
     *
     * @param type The animation type to create
     * @param crate The crate this animation is for
     * @return The configured Animation instance, or null if type is NONE
     */
    Animation createAnimation(AnimationType type, Crate crate);

    /**
     * Gets the configured animation length (in ticks) for the given animation type.
     *
     * @param type The animation type
     * @return Animation length in ticks (20 ticks = 1 second)
     */
    int getAnimationLength(AnimationType type);
}
