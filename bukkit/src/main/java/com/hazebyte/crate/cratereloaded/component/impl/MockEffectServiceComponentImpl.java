package com.hazebyte.crate.cratereloaded.component.impl;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.component.EffectServiceComponent;
import com.hazebyte.crate.cratereloaded.provider.effect.EffectWrapper;
import java.util.List;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class MockEffectServiceComponentImpl implements EffectServiceComponent {
    @Override
    public void dispose() {}

    @Override
    public void startEffectsWhenCrateIsBound(Crate crate, Location location) {}

    @Override
    public void resetEffectsWhenCrateIsUnbound(Location location) {}

    @Override
    public void startEffects(Location location, List<EffectWrapper> effects) {}

    @Override
    public void stopEffects(Location location) {}

    @Override
    public Optional<ConfigurationSection> getEffectConfiguration(String id) {
        return Optional.empty();
    }

    @Override
    public void registerEffectConfiguration(String id, ConfigurationSection effectSection) {}

    @Override
    public Optional<EffectWrapper> createEffect(ConfigurationSection configurationSection) {
        return Optional.empty();
    }
}
