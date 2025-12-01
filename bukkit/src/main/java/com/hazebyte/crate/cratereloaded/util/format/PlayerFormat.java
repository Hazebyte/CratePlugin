package com.hazebyte.crate.cratereloaded.util.format;

import com.google.common.base.Strings;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayerFormat extends Format {

    public PlayerFormat(@NotNull String message) {
        super(message);
    }

    @Override
    public String format(Object object) {
        if (object instanceof Player) {
            message = format((Player) object);
        }
        if (object instanceof OfflinePlayer) {
            message = format((OfflinePlayer) object);
        }
        return message;
    }

    public String format(@NotNull Player player) {
        double balance =
                CorePlugin.getPlugin().getEconomyProvider().getBalance(player).balance();
        String name = !Strings.isNullOrEmpty(player.getName())
                ? player.getName()
                : player.getUniqueId().toString();
        message = message.replace("{player}", name)
                .replace("{player-name}", player.getDisplayName())
                .replace("{player-uuid}", player.getUniqueId().toString())
                .replace("{money}", Double.toString(balance));
        return message;
    }

    public String format(@NotNull OfflinePlayer offlinePlayer) {
        message = message.replace("{player}", offlinePlayer.getName());
        return message;
    }
}
