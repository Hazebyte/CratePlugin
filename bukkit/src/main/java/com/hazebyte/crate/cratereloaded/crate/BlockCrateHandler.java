package com.hazebyte.crate.cratereloaded.crate;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.hazebyte.crate.api.crate.BlockCrateRegistrar;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.event.CrateRemoveEvent;
import com.hazebyte.crate.api.event.CrateSetEvent;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.model.Config;
import com.hazebyte.crate.cratereloaded.model.CrateImpl;
import com.hazebyte.crate.cratereloaded.model.CrateV2;
import com.hazebyte.crate.cratereloaded.parser.BlockCrateParser;
import com.hazebyte.crate.cratereloaded.provider.holographic.HologramWrapper;
import com.hazebyte.crate.cratereloaded.util.ConfigConstants;
import com.hazebyte.crate.cratereloaded.util.LocationUtil;
import com.hazebyte.crate.cratereloaded.util.MoreObjects;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class BlockCrateHandler implements BlockCrateRegistrar {

    // V2 Storage (primary) - THREAD SAFE
    private final Multimap<Location, CrateV2> crateMapV2;
    private final BlockCrateParser parser;
    private final CorePlugin plugin;
    private final PluginSettingComponent settings;

    @Inject
    public BlockCrateHandler(CorePlugin plugin, PluginSettingComponent settings) {
        this.plugin = plugin;
        this.settings = settings;
        // Thread-safe V2 storage
        this.crateMapV2 = Multimaps.synchronizedSetMultimap(HashMultimap.create());
        Config config = plugin.getJavaPluginComponent()
                .getConfigManagerComponent()
                .getConfigWithName(ConfigConstants.CONFIG_LOCATION_STORE_SEARCH_INDEX);
        parser = new BlockCrateParser(config, this);
        parser.parse();
    }

    public void reload() {
        crateMapV2.clear();
        parser.parse();
    }

    // Internal V2 methods (use CrateV2 directly)
    private boolean addV2(Location location, CrateV2 crateV2) {
        if (containsV2(location, crateV2)) {
            return false;
        }
        return this.crateMapV2.put(location, crateV2);
    }

    private boolean removeV2(Location location, CrateV2 crateV2) {
        if (location == null || crateV2 == null) {
            return false;
        }
        return this.crateMapV2.remove(location, crateV2);
    }

    private boolean containsV2(Location location, CrateV2 crateV2) {
        if (location == null || crateV2 == null) {
            return false;
        }
        return (this.crateMapV2.containsEntry(location, crateV2) || getV2(location).contains(crateV2));
    }

    private Collection<CrateV2> getV2(Location location) {
        if (location == null) return null;
        return this.crateMapV2.get(location);
    }

    // Legacy API compatibility methods (convert between Crate and CrateV2)
    private boolean add(Location location, Crate crate) {
        CrateV2 crateV2 = getCrateV2FromHandler(crate.getCrateName());
        if (crateV2 == null) {
            // Fallback: convert from legacy Crate
            crateV2 = CorePlugin.CRATE_MAPPER.fromImplementation((CrateImpl) crate);
        }
        return addV2(location, crateV2);
    }

    private boolean remove(Location location, Crate crate) {
        CrateV2 crateV2 = getCrateV2FromHandler(crate.getCrateName());
        if (crateV2 == null) {
            return false;
        }
        return removeV2(location, crateV2);
    }

    private boolean contains(Location location, Crate crate) {
        CrateV2 crateV2 = getCrateV2FromHandler(crate.getCrateName());
        if (crateV2 == null) {
            return false;
        }
        return containsV2(location, crateV2);
    }

    private Collection<Crate> get(Location location) {
        if (location == null) return null;
        Collection<CrateV2> cratesV2 = getV2(location);
        if (cratesV2 == null) return null;
        // Convert V2 to legacy Crate for API compatibility
        return cratesV2.stream()
                .map(CorePlugin.CRATE_MAPPER::toImplementation)
                .collect(Collectors.toList());
    }

    private CrateV2 getCrateV2FromHandler(String crateName) {
        return ((CrateHandler) plugin.getCrateRegistrar()).getCrateV2(crateName);
    }

    @Override
    public boolean setCrate(Location location, Crate crate) {
        return setCrate(location, crate, true);
    }

    @Override
    public boolean setCrate(Location location, Crate crate, boolean save) {
        if (location == null || crate == null || location.getBlock().isEmpty()) {
            return false;
        }

        CrateSetEvent event = new CrateSetEvent(crate, location);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!(event.isCancelled())) {
            if (add(location, crate)) {
                if (save) {
                    save();
                }

                plugin.getLogger()
                        .log(
                                Level.FINE,
                                String.format(
                                        "Setting crate effects on bind at [%s] for crate [%s]",
                                        event.getLocation(), event.getCrate()));
                plugin.getJavaPluginComponent()
                        .getEffectServiceComponent()
                        .startEffectsWhenCrateIsBound(event.getCrate(), event.getLocation());

                this.createHolograms(location);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeCrate(Location location, Crate crate) {
        return removeCrate(location, crate, true);
    }

    @Override
    public boolean removeCrate(Location location, Crate crate, boolean save) {
        if (location == null || crate == null) {
            return false;
        }

        if (!(contains(location, crate))) {
            return false;
        }

        CrateRemoveEvent event = new CrateRemoveEvent(crate, location);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return false;
        }

        if (remove(location, crate)) {
            if (save) {
                save();
            }

            // Remove holograms
            List<String> hologramText = crate.getHolographicText();
            Location center = LocationUtil.getBlockCenter(location);
            Location offset = LocationUtil.getOffset(center, hologramText.size())
                    .add(
                            settings.getHolographicXModifier(),
                            settings.getHolographicYModifier(),
                            settings.getHolographicZModifier());
            Optional<? extends Collection<? extends HologramWrapper>> optional =
                    plugin.getHolographicProvider().getHolograms(offset);
            optional.ifPresent(collection -> collection.forEach(HologramWrapper::delete));

            plugin.getLogger()
                    .log(
                            Level.FINE,
                            String.format(
                                    "Resetting crate effects on unbind at [%s] for crate [%s]",
                                    event.getLocation(), event.getCrate()));
            plugin.getJavaPluginComponent()
                    .getEffectServiceComponent()
                    .resetEffectsWhenCrateIsUnbound(event.getLocation());
            createHolograms(location);
            return true;
        }
        return false;
    }

    @Override
    public boolean save() {
        Config config = CorePlugin.getJavaPluginComponent()
                .getConfigManagerComponent()
                .getConfigWithName(ConfigConstants.CONFIG_LOCATION_STORE_SEARCH_INDEX);

        Map<String, List> configKeys = new HashMap();

        for (Location location : crateMapV2.keySet()) {
            World world = location.getWorld();
            String worldName = world.getName();

            List<String> keys = configKeys.containsKey(worldName) ? configKeys.get(worldName) : new ArrayList<>();
            Collection<CrateV2> crates = this.crateMapV2.get(location);

            for (CrateV2 crate : crates) {
                keys.add(getSaveKeyV2(location, crate));
            }
            configKeys.put(worldName, keys);
        }

        for (Map.Entry<String, List> entry : configKeys.entrySet()) {
            String worldName = entry.getKey();
            List keys = entry.getValue();
            plugin.getLogger().finer(String.format("%s: %s", worldName, keys));
            config.getConfig().set(worldName, keys);
        }

        try {
            config.saveConfig();
            return true;
        } catch (IOException e) {
            plugin.getLogger().severe(String.format("Failed to save block crate configuration: %s", e.getMessage()));
            plugin.getLogger().log(Level.SEVERE, "Stack trace:", e);
            return false;
        }
    }

    private String getSaveKeyV2(Location location, CrateV2 crate) {
        if (location == null || crate == null) {
            return null;
        }
        return LocationUtil.toKey(location) + "," + String.format("crate=%s", crate.getCrateName());
    }

    // Legacy compatibility
    private String getSaveKey(Location location, Crate crate) {
        if (location == null || crate == null) {
            return null;
        }
        return LocationUtil.toKey(location) + "," + String.format("crate=%s", crate.getCrateName());
    }

    @Override
    public Crate getCrate(Location location, Crate crate) {
        Collection<Crate> crates = get(location);
        Optional<Crate> optional =
                crates.stream().filter(current -> current.equals(crate)).findFirst();
        return optional.orElse(null);
    }

    @Override
    public Crate getCrate(Location location, String crateName) {
        Collection<Crate> crates = get(location);
        Optional<Crate> optional = crates.stream()
                .filter(crate -> crate.getCrateName().equalsIgnoreCase(crateName))
                .findFirst();
        return optional.orElse(null);
    }

    @Override
    public List<Crate> getCrates(Location location) {
        if (hasCrates(location)) {
            return new ArrayList<>(get(location));
        }
        return null;
    }

    @Override
    public boolean hasCrate(Location location, Crate crate) {
        return contains(location, crate);
    }

    @Override
    public boolean hasCrates(Location location) {
        Collection<Crate> crates = get(location);
        return !MoreObjects.isNullOrEmpty(crates);
    }

    @Override
    public Crate getFirstCrate(Location location) {
        if (location == null) {
            return null;
        }

        if (!hasCrates(location)) {
            return null;
        }

        List<Crate> crates = getCrates(location);
        return crates.get(0);
    }

    @Override
    public List<Location> getLocations() {
        return new ArrayList<>(this.crateMapV2.keySet());
    }

    private boolean createHolograms(Location location) {
        List<Crate> crates = getCrates(location);

        if (MoreObjects.isNullOrEmpty(crates)) return false;

        Location center = LocationUtil.getBlockCenter(location);
        boolean createdHolographics = false;

        for (Crate crate : crates) {
            if (!createdHolographics) {
                List<String> hologramText = crate.getHolographicText();
                Location hologramLocation = LocationUtil.getOffset(center, hologramText.size())
                        .add(
                                settings.getHolographicXModifier(),
                                settings.getHolographicYModifier(),
                                settings.getHolographicZModifier());
                Optional<? extends HologramWrapper> optional =
                        plugin.getHolographicProvider().createHologram(hologramLocation);
                hologramText.forEach(text -> optional.ifPresent(hologram -> hologram.addTextLine(text)));
                createdHolographics = true;
            }
        }
        return false;
    }
}
