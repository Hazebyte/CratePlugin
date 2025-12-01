package com.hazebyte.crate.cratereloaded.util;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandUtil {

    /**
     * Executes a command from the console. It will check for any '/' and remove them before executing
     * the console.
     *
     * @param command
     */
    public static void run(final String command) {
        try {
            TaskChain.newChain()
                    .add(new TaskChain.GenericTask() {
                        @Override
                        protected void run() {
                            String newCommand = command;
                            if (command.charAt(0) == '/') {
                                newCommand = command.substring(1);
                            }
                            CommandSender sender = Bukkit.getServer().getConsoleSender();
                            ServerCommandEvent event = new ServerCommandEvent(sender, newCommand);
                            Bukkit.getPluginManager().callEvent(event);
                            if (event.isCancelled()) return;
                            Bukkit.getServer().dispatchCommand(sender, newCommand);
                        }
                    })
                    .execute();
        } catch (CommandException e) {
            CorePlugin.getPlugin()
                    .getLogger()
                    .severe(String.format("Was not able to execute %s. Check if the plugin is up to date?", command));
        }
    }

    public static void run(final List<String> commands) {
        commands.forEach(CommandUtil::run);
    }
}
