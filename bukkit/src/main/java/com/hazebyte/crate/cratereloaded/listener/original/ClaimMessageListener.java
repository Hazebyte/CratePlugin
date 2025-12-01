package com.hazebyte.crate.cratereloaded.listener.original;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.claim.ClaimMessageConsumer;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import java.util.logging.Level;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ClaimMessageListener implements Listener {

    private final PluginSettingComponent settings;

    public ClaimMessageListener(PluginSettingComponent settings) {
        this.settings = settings;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if ((player != null) && settings.getClaimMessageJoinToggle()) {
            ClaimMessageConsumer consumer = new ClaimMessageConsumer(player);
            CorePlugin.getPlugin()
                    .getClaimRegistrar()
                    .getClaims(player)
                    .thenAccept(consumer)
                    .exceptionally(throwable -> {
                        CorePlugin.getPlugin()
                                .getLogger()
                                .log(
                                        Level.SEVERE,
                                        String.format(
                                                "Failed to fetch claims for join notification (player: %s)",
                                                player.getName()),
                                        throwable);
                        return null;
                    });
        }
    }
}
