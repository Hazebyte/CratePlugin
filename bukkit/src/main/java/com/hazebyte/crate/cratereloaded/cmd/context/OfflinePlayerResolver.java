package com.hazebyte.crate.cratereloaded.cmd.context;

import co.aikar.commands.BukkitCommandExecutionContext;
import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.MinecraftMessageKeys;
import co.aikar.commands.contexts.IssuerAwareContextResolver;
import com.hazebyte.crate.cratereloaded.util.ConfigConstants;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 * Resolves a player by UUID or name. The original implementation of this class method is here:
 * https://github.com/aikar/commands/blob/31341dcebdcb372bfa4ab505915e05244165e9b5/bukkit/src/main/java/co/aikar/commands/BukkitCommandContexts.java#L132-L154
 *
 * <p>The default resolver only allows either UUID or name. This also fixes issues with
 * geyser/floodgate where name may be prefixed with a symbol.
 * https://github.com/Hazebyte/CrateReloaded/issues/575
 */
public class OfflinePlayerResolver implements IssuerAwareContextResolver<OfflinePlayer, BukkitCommandExecutionContext> {

    @Override
    public OfflinePlayer getContext(BukkitCommandExecutionContext context) throws InvalidCommandArgument {
        String name = context.popFirstArg();
        if (context.hasFlag("uuid")
                && ConfigConstants.UUID_PATTERN.asPredicate().test(name)) {
            try {
                UUID uuid = UUID.fromString(name);
                return Bukkit.getOfflinePlayer(uuid);
            } catch (IllegalArgumentException e) {
                throw new InvalidCommandArgument(MinecraftMessageKeys.NO_PLAYER_FOUND_OFFLINE, "{search}", name);
            }
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);
        if (offlinePlayer == null || (!offlinePlayer.hasPlayedBefore() && !offlinePlayer.isOnline())) {
            throw new InvalidCommandArgument(MinecraftMessageKeys.NO_PLAYER_FOUND_OFFLINE, "{search}", name);
        }
        return offlinePlayer;
    }
}
