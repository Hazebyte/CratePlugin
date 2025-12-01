package com.hazebyte.crate.cratereloaded.provider.holographic.hd;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.hazebyte.crate.cratereloaded.provider.holographic.HologramProvider;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class HDProvider implements HologramProvider<HDHologram> {

    private static final String DEPENDENCY = "HolographicDisplays";
    private final JavaPlugin plugin;

    public HDProvider(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Optional<HDHologram> createHologram(Location location) {
        HDHologram hologram = new HDHologram(plugin, location);
        return Optional.of(hologram);
    }

    @Override
    public void removeHologram(HDHologram hologram) {
        hologram.delete();
    }

    @Override
    public void removeAll() {
        getHolograms().ifPresent(list -> list.forEach(HDHologram::delete));
    }

    @Override
    public Optional<Collection<HDHologram>> getHolograms() {
        Collection<HDHologram> holograms = HologramsAPI.getHolograms(plugin).stream()
                .filter(hologram -> !hologram.isDeleted())
                .map(HDHologram::new)
                .collect(Collectors.toList());
        return Optional.of(holograms);
    }

    @Override
    public Optional<Collection<HDHologram>> getHolograms(Location location) {
        Collection<HDHologram> holograms = HologramsAPI.getHolograms(plugin).stream()
                .filter(hologram -> !hologram.isDeleted())
                .filter(hologram -> hologram.getLocation().getWorld().equals(location.getWorld()))
                .filter(hologram -> hologram.getLocation().distance(location) < 1)
                .map(HDHologram::new)
                .collect(Collectors.toList());
        return Optional.of(holograms);
    }

    @Override
    public String getName() {
        return DEPENDENCY;
    }
}
