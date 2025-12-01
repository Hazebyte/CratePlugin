package com.hazebyte.crate.cratereloaded.provider;

import com.hazebyte.crate.cratereloaded.provider.economy.EconomyProvider;
import com.hazebyte.crate.cratereloaded.provider.economy.none.NoEconomyProvider;
import com.hazebyte.crate.cratereloaded.provider.economy.vault.VaultProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class EconomyProviderSelector extends ProviderSelector<EconomyProvider> {

    public static final String VAULT = "Vault";
    public static final String NONE = "NONE";

    public EconomyProviderSelector(JavaPlugin plugin) {
        super(plugin);
        addProvider(VAULT, VaultProvider.class, true);
        addProvider(NONE, NoEconomyProvider.class, false);
    }

    @Override
    public EconomyProvider getNilProvider() {
        return new NoEconomyProvider();
    }
}
