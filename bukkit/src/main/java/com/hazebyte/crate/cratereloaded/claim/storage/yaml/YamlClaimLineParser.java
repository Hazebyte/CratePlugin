package com.hazebyte.crate.cratereloaded.claim.storage.yaml;

import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.cratereloaded.model.Config;
import com.hazebyte.utils.function.TriFunction;
import java.util.Objects;
import org.bukkit.OfflinePlayer;

public interface YamlClaimLineParser extends TriFunction<OfflinePlayer, Config, String, Claim> {

    default void validateInput(OfflinePlayer player, Config config, String key) {
        Objects.requireNonNull(player, "player must not be null");
        Objects.requireNonNull(config, "config must not be null");
        Objects.requireNonNull(key, "key must not be null");
        if (key.isEmpty()) throw new NullPointerException("key is empty");
        if (!config.getConfig().isSet(key)) {
            throw new NullPointerException("key is not found in config");
        }
    }
}
