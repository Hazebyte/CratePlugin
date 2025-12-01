package com.hazebyte.crate.cratereloaded.provider.economy.none;

import com.hazebyte.crate.cratereloaded.provider.economy.EconomyProvider;
import org.bukkit.OfflinePlayer;

public class NoEconomyProvider implements EconomyProvider<NoEconomyResponse> {
    @Override
    public NoEconomyResponse deposit(OfflinePlayer player, double amount) {
        return new NoEconomyResponse();
    }

    @Override
    public NoEconomyResponse withdraw(OfflinePlayer player, double amount) {
        return new NoEconomyResponse();
    }

    @Override
    public NoEconomyResponse getBalance(OfflinePlayer player) {
        return new NoEconomyResponse();
    }

    @Override
    public String getName() {
        return "None";
    }
}
