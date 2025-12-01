package com.hazebyte.crate.cratereloaded.claim.storage;

import com.hazebyte.crate.api.claim.Claim;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import org.bukkit.OfflinePlayer;

public interface ClaimStorage {

    CompletableFuture<Collection<Claim>> getClaims(OfflinePlayer player);

    CompletableFuture<Void> saveClaim(Claim claim);

    CompletableFuture<Void> deleteClaim(Claim claim);
}
