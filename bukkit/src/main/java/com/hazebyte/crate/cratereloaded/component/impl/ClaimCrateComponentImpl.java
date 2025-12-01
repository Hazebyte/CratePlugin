package com.hazebyte.crate.cratereloaded.component.impl;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.api.claim.ClaimRegistrar;
import com.hazebyte.crate.api.util.Messenger;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.ClaimCrateComponent;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.util.PlayerUtil;
import com.hazebyte.crate.cratereloaded.util.format.CustomFormat;
import com.hazebyte.crate.exception.ExceptionHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import javax.inject.Inject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClaimCrateComponentImpl implements ClaimCrateComponent {

    private final CorePlugin plugin;
    private final ExceptionHandler exceptionHandler;
    private final PluginSettingComponent settings;

    @Inject
    public ClaimCrateComponentImpl(
            CorePlugin plugin, ExceptionHandler exceptionHandler, PluginSettingComponent settings) {
        this.plugin = plugin;
        this.exceptionHandler = exceptionHandler;
        this.settings = settings;
    }

    @Override
    public void openClaim(Player player) {
        ClaimRegistrar claimManager = plugin.getClaimRegistrar();
        claimManager.getClaims(player)
                .thenAccept(claims -> {
                    if (claims.size() > 0) {
                        claimManager.openInventory(player);
                    } else {
                        String message = CrateAPI.getMessage("core.claim_none");
                        Messenger.tell(player, CustomFormat.format(message));
                    }
                })
                .exceptionally(throwable -> {
                    exceptionHandler.handle((Exception) throwable);
                    Messenger.tell(player, "&cFailed to load your claims. Please contact an administrator.");
                    return null;
                });
    }

    @Override
    public void claimAllRewards(Player player) {
        ClaimRegistrar manager = plugin.getClaimRegistrar();
        Consumer<Collection<Claim>> test = (claims) -> {
            if (claims.size() <= 0) {
                String messageKey = "core.claim_none";
                Messenger.tell(player, CustomFormat.format(CrateAPI.getMessage(messageKey)));
                return;
            }

            int limit = Math.min(
                    claims.size(),
                    Math.min(settings.getClaimLimit(), PlayerUtil.getSlotsLeft(player)));

            List<Claim> copy = new ArrayList<>();
            Iterator<Claim> iterator = claims.iterator();

            for (int i = 0; i < limit; i++) {
                copy.add(iterator.next());
            }

            for (Claim claim : copy) {
                try {
                    manager.removeClaim(claim)
                            .thenRun(() -> {
                                Consumer<Claim> syncCall =
                                        (item) -> Bukkit.getScheduler().runTask(plugin, () -> item.execute());
                                syncCall.accept(claim);
                            })
                            .exceptionally(throwable -> {
                                exceptionHandler.handle((Exception) throwable);
                                plugin.getLogger()
                                        .log(
                                                Level.SEVERE,
                                                String.format(
                                                        "Failed to remove claim for player %s", player.getName()),
                                                throwable);
                                return null;
                            });
                } catch (Exception e) {
                    exceptionHandler.handle(e);
                    plugin.getLogger()
                            .log(
                                    Level.SEVERE,
                                    String.format(
                                            "Failed to initiate claim removal for player %s", player.getName()),
                                    e);
                }
            }
        };
        manager.getClaims(player)
                .thenAccept(test)
                .exceptionally(throwable -> {
                    exceptionHandler.handle((Exception) throwable);
                    Messenger.tell(player, "&cFailed to load your claims. Please try again later.");
                    return null;
                });
    }
}
