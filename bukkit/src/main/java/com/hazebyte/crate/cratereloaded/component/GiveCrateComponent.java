package com.hazebyte.crate.cratereloaded.component;

import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.cratereloaded.model.GiveItemExecutorResult;
import java.util.Set;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface GiveCrateComponent {

    void giveCrate(
            @NonNull CommandSender sender,
            @NonNull OfflinePlayer offlinePlayer,
            @NonNull Crate crate,
            @NonNull Integer amount,
            @NonNull boolean sendToClaim);

    Set<GiveItemExecutorResult> giveCrateToOnlinePlayer(
            @NonNull Player player, @NonNull Crate crate, @NonNull int amount);

    void giveCrateToAllOnlinePlayers(@NonNull CommandSender sender, @NonNull Crate crate, Integer amount);
}
