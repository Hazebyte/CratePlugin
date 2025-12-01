package com.hazebyte.crate.cratereloaded.cmd.completion;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.InvalidCommandArgument;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class RegisteredCrateFromCameraCompletion
        implements CommandCompletions.CommandCompletionHandler<BukkitCommandCompletionContext> {

    @Override
    public Collection<String> getCompletions(BukkitCommandCompletionContext context) throws InvalidCommandArgument {
        Block targetBlock = context.getPlayer().getTargetBlock(null, 30);
        if (targetBlock == null) {
            return null;
        }

        Location location = targetBlock.getLocation();
        List<Crate> crates = CorePlugin.getPlugin().getBlockCrateRegistrar().getCrates(location);
        if (crates == null) {
            return null;
        }
        return crates.stream().map(Crate::getCrateName).collect(Collectors.toSet());
    }
}
