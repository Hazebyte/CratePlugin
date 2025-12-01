package com.hazebyte.crate.cratereloaded.claim.storage.yaml;

import static com.hazebyte.crate.cratereloaded.claim.storage.yaml.YamlClaimConstants.CONFIG_VERSION_KEY;

import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.cratereloaded.model.Config;
import org.bukkit.OfflinePlayer;

/** This migrates on a per-key basis rather than the whole config at once. */
public class V1ToV2 {

    private Config config;

    // The `key` is the header. In v1, it represents the timestamp. In v2, it is the UUID.
    private String key;

    private OfflinePlayer player;

    protected V1ToV2(Config config, String key, OfflinePlayer player) {
        this.config = config;
        this.key = key;
        this.player = player;
    }

    public Claim migrate(YamlClaimStorage storage, YamlClaimLineParser parser) throws java.io.IOException {
        if (!isOutOfDate(config, key)) {
            return null;
        }

        Claim claim = parser.apply(player, config, key);

        // Remove the previous V1 claim data
        config.getConfig().set(key, null);

        // Save the migrated claim in V2 format synchronously (we're already in async context)
        storage.saveClaimSync(claim);
        return claim;
    }

    public boolean isOutOfDate(Config config, String key) {
        String versionKey = String.format(CONFIG_VERSION_KEY, key);
        String version = config.getConfig().getString(versionKey);
        return version == null;
    }
}
