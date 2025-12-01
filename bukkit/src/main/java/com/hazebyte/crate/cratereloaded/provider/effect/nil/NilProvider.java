package com.hazebyte.crate.cratereloaded.provider.effect.nil;

import com.hazebyte.crate.cratereloaded.provider.effect.EffectProvider;
import com.hazebyte.crate.cratereloaded.provider.effect.EffectWrapper;
import java.util.Collection;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class NilProvider implements EffectProvider {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Optional<ConfigurationSection> getEffectConfig(String id) {
        return Optional.empty();
    }

    @Override
    public void registerEffectConfig(String id, ConfigurationSection effectSection) {}

    @Override
    public Optional createEffect(ConfigurationSection effectSection) {
        return Optional.empty();
    }

    @Override
    public void removeEffect(EffectWrapper effectWrapper) {}

    @Override
    public void removeAll() {}

    @Override
    public Optional<Collection> getEffects() {
        return Optional.empty();
    }

    @Override
    public Optional<Collection> getEffects(Location location) {
        return Optional.empty();
    }
}
