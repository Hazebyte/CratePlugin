package com.hazebyte.crate.cratereloaded.provider.holographic.cmi;

import com.hazebyte.crate.cratereloaded.provider.holographic.HologramProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class CMIProvider implements HologramProvider<CMIHologram> {

    private static final String DEPENDENCY = "CMI";
    private final JavaPlugin plugin;
    private final List<CMIHologram> holograms;

    public CMIProvider(JavaPlugin plugin) {
        this.plugin = plugin;
        holograms = new ArrayList<>();
    }

    @Override
    public Optional<CMIHologram> createHologram(Location location) {
        CMIHologram hologram = new CMIHologram(plugin, location);
        holograms.add(hologram);
        return Optional.of(hologram);
    }

    @Override
    public void removeHologram(CMIHologram hologram) {
        holograms.remove(hologram);
        hologram.delete();
    }

    @Override
    public void removeAll() {
        holograms.forEach(CMIHologram::delete);
        holograms.clear();
    }

    @Override
    public Optional<Collection<CMIHologram>> getHolograms() {
        return Optional.of(holograms);
    }

    @Override
    public Optional<Collection<CMIHologram>> getHolograms(Location location) {
        Collection<CMIHologram> holograms = this.holograms.stream()
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
