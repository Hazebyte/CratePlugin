package com.hazebyte.crate.cratereloaded.listener.resolver;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.listener.Resolver;
import org.bukkit.plugin.Plugin;

public class CitizenResolver implements Resolver {
    @Override
    public boolean resolve() {
        Plugin citizens = CorePlugin.getPlugin().getServer().getPluginManager().getPlugin("Citizens");
        return (citizens != null && citizens.isEnabled());
    }
}
