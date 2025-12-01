package com.hazebyte.crate.cratereloaded.cmd.context;

import co.aikar.commands.BukkitCommandExecutionContext;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.contexts.ContextResolver;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.CorePlugin;

public class CrateResolver implements ContextResolver<Crate, BukkitCommandExecutionContext> {

    private final CorePlugin plugin;

    public CrateResolver(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Crate getContext(BukkitCommandExecutionContext context) throws InvalidCommandArgument {
        final String search = context.popFirstArg();
        Crate crate = plugin.getCrateRegistrar().getCrate(search);

        if (crate == null) throw new InvalidCommandArgument(String.format("Not crate matching %s.", search));

        return crate;
    }
}
