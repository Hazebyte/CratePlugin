package com.hazebyte.crate.cratereloaded.provider.economy.vault;

import com.hazebyte.crate.cratereloaded.provider.economy.EconomyResponse;

public class VaultResponse implements EconomyResponse {

    private final net.milkbowl.vault.economy.EconomyResponse economyResponse;

    public VaultResponse(net.milkbowl.vault.economy.EconomyResponse economyResponse) {
        this.economyResponse = economyResponse;
    }

    @Override
    public boolean transactionSuccess() {
        return economyResponse.transactionSuccess();
    }

    @Override
    public double amount() {
        return economyResponse.amount;
    }

    @Override
    public double balance() {
        return economyResponse.balance;
    }

    @Override
    public String errorMessage() {
        return economyResponse.errorMessage;
    }
}
