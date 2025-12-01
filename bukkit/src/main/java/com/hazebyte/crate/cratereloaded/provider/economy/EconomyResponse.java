package com.hazebyte.crate.cratereloaded.provider.economy;

public interface EconomyResponse {

    boolean transactionSuccess();

    double amount();

    double balance();

    String errorMessage();
}
