package com.hazebyte.crate.cratereloaded.cmd.completion;

import co.aikar.commands.BukkitCommandCompletionContext;
import co.aikar.commands.CommandCompletions;
import co.aikar.commands.InvalidCommandArgument;
import com.hazebyte.crate.cratereloaded.util.Camera;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class PlayerCameraCoordinateCompletion
        implements CommandCompletions.CommandCompletionHandler<BukkitCommandCompletionContext> {

    private final Collection<String> defaultList =
            IntStream.rangeClosed(0, 9).mapToObj(Integer::toString).collect(Collectors.toList());

    @Override
    public Collection<String> getCompletions(BukkitCommandCompletionContext context) throws InvalidCommandArgument {
        String config = context.getConfig();
        if (config == null) {
            return Collections.emptyList();
        }

        if (!(context.getSender() instanceof Player)) {
            return defaultList;
        }

        Block block = Camera.getTargetBlock(context.getPlayer());
        switch (config.toUpperCase()) {
            case "X":
                return Collections.singletonList(String.valueOf(block.getX()));
            case "Y":
                return Collections.singletonList(String.valueOf(block.getY()));
            case "Z":
                return Collections.singletonList(String.valueOf(block.getZ()));
        }

        return defaultList;
    }
}
