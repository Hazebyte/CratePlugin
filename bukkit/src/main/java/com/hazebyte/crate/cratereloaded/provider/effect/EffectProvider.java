package com.hazebyte.crate.cratereloaded.provider.effect;

import com.hazebyte.crate.cratereloaded.provider.Provider;
import java.util.Collection;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @param <T> A wrapper that represents an effect.
 */
public interface EffectProvider<T extends EffectWrapper> extends Provider {

    Optional<ConfigurationSection> getEffectConfig(String id);

    void registerEffectConfig(String id, ConfigurationSection effectSection);

    Optional<T> createEffect(ConfigurationSection effectSection);

    void removeEffect(T t);

    void removeAll();

    Optional<Collection<T>> getEffects();

    Optional<Collection<T>> getEffects(Location location);
}
