package com.hazebyte.crate.cratereloaded.cmd.context;

import co.aikar.commands.ACFUtil;
import co.aikar.commands.BukkitCommandExecutionContext;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.contexts.ContextResolver;

public class TruthyResolver implements ContextResolver<Boolean, BukkitCommandExecutionContext> {
    @Override
    public Boolean getContext(BukkitCommandExecutionContext context) throws InvalidCommandArgument {
        String search = context.popFirstArg();
        return search.equalsIgnoreCase("online") || search.equalsIgnoreCase("true") || ACFUtil.isTruthy(search);
    }
}
