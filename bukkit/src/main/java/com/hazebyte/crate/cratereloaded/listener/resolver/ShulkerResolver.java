package com.hazebyte.crate.cratereloaded.listener.resolver;

import com.hazebyte.crate.api.ServerVersion;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.listener.Resolver;

public class ShulkerResolver implements Resolver {
    @Override
    public boolean resolve() {
        return CorePlugin.getPlugin().getServerVersion().gte(ServerVersion.v1_12_R1);
    }
}
