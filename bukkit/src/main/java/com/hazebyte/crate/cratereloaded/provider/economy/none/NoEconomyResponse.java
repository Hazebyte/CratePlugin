package com.hazebyte.crate.cratereloaded.provider.economy.none;

import com.hazebyte.crate.cratereloaded.provider.economy.EconomyResponse;

public class NoEconomyResponse implements EconomyResponse {
    @Override
    public boolean transactionSuccess() {
        return false;
    }

    @Override
    public double amount() {
        return 0;
    }

    @Override
    public double balance() {
        return 0;
    }

    @Override
    public String errorMessage() {
        return null;
    }
}
