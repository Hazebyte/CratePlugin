package com.hazebyte.crate.cratereloaded.provider.holographic.dh;

import com.hazebyte.crate.cratereloaded.provider.holographic.HologramProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.bukkit.Location;

public class DecentHologramProvider implements HologramProvider<DecentHologram> {

    private static final String DEPENDENCY = "DecentHolograms";

    private Predicate<DecentHologram> HOLOGRAM_NOT_DELETED = decentHologram -> !decentHologram.isDeleted();

    private final List<DecentHologram> hologramCache = new ArrayList<>();

    public DecentHologramProvider() {}

    @Override
    public Optional<DecentHologram> createHologram(Location location) {
        DecentHologram hologram = new DecentHologram(location);
        hologramCache.add(hologram);
        return Optional.of(hologram);
    }

    @Override
    public void removeHologram(DecentHologram hologram) {
        hologram.delete();
        hologramCache.remove(hologram);
    }

    @Override
    public void removeAll() {
        getHolograms().ifPresent(list -> list.forEach(DecentHologram::delete));
    }

    @Override
    public Optional<Collection<DecentHologram>> getHolograms() {
        Collection<DecentHologram> holograms =
                hologramCache.stream().filter(HOLOGRAM_NOT_DELETED).collect(Collectors.toList());
        return Optional.of(holograms);
    }

    @Override
    public Optional<Collection<DecentHologram>> getHolograms(Location location) {
        Collection<DecentHologram> holograms = hologramCache.stream()
                .filter(HOLOGRAM_NOT_DELETED)
                .filter(hologram -> hologram.getLocation().getWorld().equals(location.getWorld()))
                .filter(hologram -> hologram.getLocation().distance(location) < 1)
                .collect(Collectors.toList());
        return Optional.of(holograms);
    }

    @Override
    public String getName() {
        return DEPENDENCY;
    }
}
