package com.hazebyte.crate.cratereloaded.provider;

import com.hazebyte.crate.cratereloaded.provider.holographic.HologramProvider;
import com.hazebyte.crate.cratereloaded.provider.holographic.cmi.CMIProvider;
import com.hazebyte.crate.cratereloaded.provider.holographic.dh.DecentHologramProvider;
import com.hazebyte.crate.cratereloaded.provider.holographic.hd.HDProvider;
import com.hazebyte.crate.cratereloaded.provider.holographic.none.NoHologramProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class HologramProviderSelector extends ProviderSelector<HologramProvider> {

    public static final String HOLOGRAPHIC_DISPLAY = "HolographicDisplays";
    public static final String CMI = "CMI";
    public static final String DECENT_HOLOGRAM = "DecentHolograms";
    public static final String NONE = "none";

    public HologramProviderSelector(JavaPlugin plugin) {
        super(plugin);
        addProvider(HOLOGRAPHIC_DISPLAY, HDProvider.class, true);
        addProvider(CMI, CMIProvider.class, true);
        addProvider(DECENT_HOLOGRAM, DecentHologramProvider.class, true);
        addProvider(NONE, NoHologramProvider.class, false);
    }

    @Override
    public HologramProvider getNilProvider() {
        return new NoHologramProvider();
    }
}
