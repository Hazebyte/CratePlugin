package com.hazebyte.crate.cratereloaded.provider.economy;

import com.hazebyte.crate.cratereloaded.provider.Provider;
import org.bukkit.OfflinePlayer;

public interface EconomyProvider<T extends EconomyResponse> extends Provider {

    T deposit(OfflinePlayer player, double amount);

    T withdraw(OfflinePlayer player, double amount);

    T getBalance(OfflinePlayer player);
}
