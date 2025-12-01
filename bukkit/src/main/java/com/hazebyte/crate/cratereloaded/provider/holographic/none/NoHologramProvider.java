package com.hazebyte.crate.cratereloaded.provider.holographic.none;

import com.hazebyte.crate.cratereloaded.provider.holographic.HologramProvider;
import java.util.Collection;
import java.util.Optional;
import org.bukkit.Location;

public class NoHologramProvider implements HologramProvider<NoHologram> {

    @Override
    public Optional<NoHologram> createHologram(Location location) {
        return Optional.empty();
    }

    @Override
    public void removeHologram(NoHologram hologram) {}

    @Override
    public void removeAll() {}

    @Override
    public Optional<Collection<NoHologram>> getHolograms() {
        return Optional.empty();
    }

    @Override
    public Optional<Collection<NoHologram>> getHolograms(Location location) {
        return Optional.empty();
    }

    @Override
    public String getName() {
        return "None";
    }
}
