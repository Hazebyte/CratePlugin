package com.hazebyte.crate.cratereloaded.component;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.provider.effect.EffectWrapper;
import java.util.List;
import java.util.Optional;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public interface EffectServiceComponent {

    void dispose();

    void startEffectsWhenCrateIsBound(Crate crate, Location location);

    void resetEffectsWhenCrateIsUnbound(Location location);

    void startEffects(Location location, List<EffectWrapper> effects);

    void stopEffects(Location location);

    Optional<ConfigurationSection> getEffectConfiguration(String id);

    void registerEffectConfiguration(String id, ConfigurationSection effectSection);

    Optional<EffectWrapper> createEffect(ConfigurationSection configurationSection);
}
