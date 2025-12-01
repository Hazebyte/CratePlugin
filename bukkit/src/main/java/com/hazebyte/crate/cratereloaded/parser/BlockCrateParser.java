package com.hazebyte.crate.cratereloaded.parser;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.crate.BlockCrateHandler;
import com.hazebyte.crate.cratereloaded.crate.CrateHandler;
import com.hazebyte.crate.cratereloaded.listener.original.WorldLoadListener;
import com.hazebyte.crate.cratereloaded.model.Config;
import com.hazebyte.crate.cratereloaded.util.LocationFactory;
import com.hazebyte.crate.cratereloaded.util.format.CustomFormat;
import com.hazebyte.crate.exception.items.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class BlockCrateParser implements Parser {

    private final Config config;
    private final BlockCrateHandler registrar;

    public BlockCrateParser(Config config, BlockCrateHandler registrar) {
        this.config = config;
        this.registrar = registrar;
    }

    private Set<String> sections(Config config) {
        return config.getConfig().getKeys(false);
    }

    public void parse() {
        for (String worldName : sections(config)) {
            List<String> valueStore = config.getConfig().getStringList(worldName);
            List<String> copyStore = new ArrayList<>(valueStore); // Copy the value store to avoid concurrency issues
            for (String value : valueStore) {
                if ((Bukkit.getWorld(worldName)) == null) {
                    CorePlugin.getPlugin()
                            .getLogger()
                            .warning(String.format(
                                    "Block.yml: Not able to find world, \"%s\". Queuing listener for world!",
                                    worldName));
                    WorldLoadListener.add(worldName);
                    break;
                }

                Location location;
                try {
                    location = LocationFactory.createLocation(value);
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }

                Crate crate = parseCrate(value);

                if (crate == null) {
                    CorePlugin.getPlugin()
                            .getLogger()
                            .warning(String.format(
                                    "The saved value, %s, in was not found. Removing from the location.yml", value));
                    copyStore.remove(value);
                    continue;
                }

                if (crate.getType() != CrateType.KEY) {
                    String message = CrateAPI.getMessage("core.invalid_crate_key");
                    CorePlugin.getPlugin().getLogger().warning(CustomFormat.format(message, crate));
                    CorePlugin.getPlugin()
                            .getLogger()
                            .warning(CustomFormat.format("Removing the crate, {crate}, from the location.yml", crate));
                    copyStore.remove(value);
                    continue;
                }

                registrar.setCrate(location, crate, false);
            }

            if (copyStore.size() != valueStore.size()) { // If there was a key removed, save the updated list.
                config.getConfig().set(worldName, copyStore);
            }
        }
        registrar.save();
    }

    public Crate parseCrate(String values) {
        Crate crate = null;
        for (String current : values.split(",")) {
            String[] pair = current.split("=");
            if (pair.length == 2) {
                String key = pair[0];
                String value = pair[1];
                switch (key) {
                    case "crate":
                        crate = CrateHandler.getInstance().getCrate(value);
                        break;
                }
            }
        }
        return crate;
    }
}
