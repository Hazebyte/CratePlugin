package com.hazebyte.crate.cratereloaded.component.impl;

import com.hazebyte.crate.api.crate.AnimationType;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.AnimationFactoryComponent;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.crate.animation.Animation;
import com.hazebyte.crate.cratereloaded.crate.animation.scroller.Csgo;
import com.hazebyte.crate.cratereloaded.crate.animation.scroller.Heart;
import com.hazebyte.crate.cratereloaded.crate.animation.scroller.Rectangle;
import com.hazebyte.crate.cratereloaded.crate.animation.scroller.ReverseCsgo;
import com.hazebyte.crate.cratereloaded.crate.animation.scroller.ReverseRectangle;
import com.hazebyte.crate.cratereloaded.crate.animation.scroller.Roulette;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory implementation for creating Animation objects with timing from plugin settings.
 * Removes static CorePlugin dependencies from CrateImpl.
 */
@Singleton
public class AnimationFactoryComponentImpl implements AnimationFactoryComponent {

    private final CorePlugin plugin;
    private final PluginSettingComponent settings;

    @Inject
    public AnimationFactoryComponentImpl(CorePlugin plugin, PluginSettingComponent settings) {
        this.plugin = plugin;
        this.settings = settings;
    }

    @Override
    public Animation createAnimation(AnimationType type, Crate crate) {
        if (type == null) {
            return null;
        }

        int length = getAnimationLength(type);

        switch (type) {
            case CSGO:
                return new Csgo(crate, length, settings);
            case ROULETTE:
                return new Roulette(crate, length, settings);
            case CSGO_REVERSE:
                return new ReverseCsgo(crate, length, settings);
            case RECTANGLE:
                return new Rectangle(crate, length, settings);
            case RECTANGLE_REVERSE:
                return new ReverseRectangle(crate, length, settings);
            case HEART:
                return new Heart(crate, length, settings);
            case NONE:
            default:
                return null;
        }
    }

    @Override
    public int getAnimationLength(AnimationType type) {
        if (type == null) {
            return 0;
        }

        switch (type) {
            case CSGO:
            case CSGO_REVERSE:
                return 20 * settings.getCSGOAnimationLength();
            case ROULETTE:
                return 20 * settings.getRouletteAnimationLength();
            case RECTANGLE:
            case RECTANGLE_REVERSE:
            case HEART:
                return 20 * settings.getWheelAnimationLength();
            case NONE:
            default:
                return 0;
        }
    }
}
