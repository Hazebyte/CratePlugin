package com.hazebyte.crate.cratereloaded.provider.holographic;

import com.hazebyte.crate.cratereloaded.provider.Provider;
import java.util.Collection;
import java.util.Optional;
import org.bukkit.Location;

/**
 * Each supported plugin will have it's own Provider class. This provider class extends Holograms
 * and gives a way to create a HologramWrapper.
 */
public interface HologramProvider<T extends HologramWrapper> extends Provider {

    Optional<T> createHologram(Location location);

    void removeHologram(T hologram);

    void removeAll();

    Optional<Collection<T>> getHolograms();

    Optional<Collection<T>> getHolograms(Location location);

    String getName();
}
