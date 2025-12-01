package com.hazebyte.crate.cratereloaded.provider.economy.vault;

import com.hazebyte.crate.cratereloaded.provider.economy.EconomyProvider;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultProvider implements EconomyProvider<VaultResponse> {

    public static final String DEPENDENCY = "Vault";
    private static Economy economy;

    public VaultProvider() {
        RegisteredServiceProvider<Economy> rsp =
                Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            throw new IllegalArgumentException("Unable to initialize economy.");
        }

        economy = rsp.getProvider();
    }

    @Override
    public VaultResponse deposit(OfflinePlayer player, double amount) {
        return new VaultResponse(economy.depositPlayer(player, amount));
    }

    @Override
    public VaultResponse withdraw(OfflinePlayer player, double amount) {
        return new VaultResponse(economy.withdrawPlayer(player, amount));
    }

    @Override
    public VaultResponse getBalance(OfflinePlayer player) {
        return new VaultResponse(
                new EconomyResponse(0, economy.getBalance(player), EconomyResponse.ResponseType.SUCCESS, null));
    }

    @Override
    public String getName() {
        return "Vault";
    }
}
