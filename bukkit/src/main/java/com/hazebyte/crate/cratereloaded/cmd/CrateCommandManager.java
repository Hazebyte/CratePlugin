package com.hazebyte.crate.cratereloaded.cmd;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.MessageKeys;
import co.aikar.commands.MessageType;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.crate.CrateType;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.cmd.completion.PlayerCameraCoordinateCompletion;
import com.hazebyte.crate.cratereloaded.cmd.completion.RegisteredCrateFromCameraCompletion;
import com.hazebyte.crate.cratereloaded.cmd.context.CrateResolver;
import com.hazebyte.crate.cratereloaded.cmd.context.OfflinePlayerResolver;
import com.hazebyte.crate.cratereloaded.cmd.context.TruthyResolver;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.bukkit.OfflinePlayer;

public class CrateCommandManager extends BukkitCommandManager {

    private final CorePlugin plugin;

    public CrateCommandManager(CorePlugin plugin) {
        super(plugin);
        this.plugin = plugin;
        this.enableUnstableAPI("help");

        registerResolver();
        registerCompletion(plugin);
        registerCommand();
        registerExceptionHandler();
    }

    private void registerResolver() {
        getCommandContexts().registerContext(OfflinePlayer.class, new OfflinePlayerResolver());
        getCommandContexts().registerContext(Crate.class, new CrateResolver(plugin));
        getCommandContexts().registerContext(Boolean.class, new TruthyResolver());
    }

    private void registerCompletion(CorePlugin plugin) {
        getCommandCompletions()
                .registerCompletion("AvailableCrateAtLocation", new RegisteredCrateFromCameraCompletion());
        getCommandCompletions().registerCompletion("crates", (c) -> (plugin.getCrateRegistrar().getCrates().stream()
                .map(crate -> crate.getCrateName())
                .collect(Collectors.toSet())));
        getCommandCompletions().registerCompletion("keys", (c) -> (plugin.getCrateRegistrar().getCrates().stream()
                .filter(crate -> crate.getType() == CrateType.KEY)
                .map(crate -> crate.getCrateName())
                .collect(Collectors.toSet())));
        getCommandCompletions().registerCompletion("status", (c) -> Arrays.asList("online", "offline"));
        getCommandCompletions().registerCompletion("target", new PlayerCameraCoordinateCompletion());
    }

    private void registerCommand() {
        registerCommand(new CrateCommand(plugin).setExceptionHandler((command, registeredCommand, sender, args, t) -> {
            sender.sendMessage(MessageType.ERROR, MessageKeys.ERROR_GENERIC_LOGGED);
            return true;
        }));
    }

    private void registerExceptionHandler() {
        setDefaultExceptionHandler((command, registeredCommand, sender, args, t) -> {
            plugin.getLogger().warning("Error occurred while executing command " + command.getName());
            return false; // mark as unhandled, sender will see original message
        });
    }
}
