package com.hazebyte.crate.cratereloaded.claim.storage.yaml;

import static com.hazebyte.crate.cratereloaded.claim.storage.yaml.YamlClaimConstants.CONFIG_PLAYER_KEY;
import static com.hazebyte.crate.cratereloaded.claim.storage.yaml.YamlClaimConstants.CONFIG_REWARDS_KEY;
import static com.hazebyte.crate.cratereloaded.claim.storage.yaml.YamlClaimConstants.CONFIG_TIMESTAMP_KEY;
import static com.hazebyte.crate.cratereloaded.claim.storage.yaml.YamlClaimConstants.CONFIG_VERSION_KEY;

import com.hazebyte.crate.api.claim.Claim;
import com.hazebyte.crate.api.crate.reward.Reward;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.claim.ClaimExecutor;
import com.hazebyte.crate.cratereloaded.claim.CrateClaim;
import com.hazebyte.crate.cratereloaded.claim.storage.ClaimStorage;
import com.hazebyte.crate.cratereloaded.model.Config;
import com.hazebyte.crate.cratereloaded.util.ConfigConstants;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

public class YamlClaimStorage implements ClaimStorage {

    public static final String YAML_CONFIG_VERSION = "v2";
    private final JavaPlugin plugin;
    private final ClaimExecutor claimExecutor;

    public YamlClaimStorage(JavaPlugin plugin, ClaimExecutor claimExecutor) {
        this.plugin = plugin;
        this.claimExecutor = claimExecutor;
    }

    protected Config getConfig(OfflinePlayer player) {
        final String path = String.format("%s%s.yml", ConfigConstants.CONFIG_CLAIM_STORE_SEARCH_INDEX, player.getUniqueId());
        final Config config =
                CorePlugin.getJavaPluginComponent().getConfigManagerComponent().getConfigWithName(path);
        if (config != null) {
            return config;
        }

        File file = new File(plugin.getDataFolder(), path);
        return CorePlugin.getJavaPluginComponent().getConfigManagerComponent().addConfig(file);
    }

    private Claim parse(Config config, String key, OfflinePlayer offlinePlayer) {
        /**
         * This is the original version of the yaml configuration e.g. 'timestamp': - 'reward string,
         * cmd:(/cr give to Bart7567 FoodChest 1)'
         */
        String versionString = config.getConfig().getString(String.format(CONFIG_VERSION_KEY, key));
        Optional<Claim> claim = Optional.empty();
        if (versionString == null) {
            V1ToV2 migration = new V1ToV2(config, key, offlinePlayer);
            try {
                claim = Optional.ofNullable(migration.migrate(this, new V1YamlClaimLineParser(claimExecutor)));
            } catch (IOException e) {
                plugin.getLogger()
                        .log(
                                java.util.logging.Level.SEVERE,
                                String.format("Failed to migrate V1 claim for player %s", offlinePlayer.getUniqueId()),
                                e);
                return null;
            }
        } else if (versionString.equalsIgnoreCase(YAML_CONFIG_VERSION)) {
            V2YamlClaimLineParser v2YamlClaimLineParser = new V2YamlClaimLineParser(claimExecutor);
            claim = Optional.ofNullable(v2YamlClaimLineParser.apply(offlinePlayer, config, key));
        }

        if (!claim.isPresent()) {
            return null;
        }

        if (claim.get().getRewards() == null || claim.get().getRewards().size() == 0) {
            deleteClaimSync(claim.get());
            return null;
        }

        return claim.get();
    }

    @Override
    public CompletableFuture<Collection<Claim>> getClaims(OfflinePlayer player) {
        return CompletableFuture.supplyAsync(() -> {
            plugin.getLogger().finer("Claim: get all " + player.getUniqueId());
            Config config = getConfig(player);
            Collection<Claim> claims = new ArrayList<>();
            for (String key : config.getConfig().getKeys(false)) {
                try {
                    Optional<Claim> claim = Optional.ofNullable(parse(config, key, player));
                    claim.ifPresent(claims::add);
                } catch (IllegalArgumentException | NullPointerException err) {
                    plugin.getLogger()
                            .log(
                                    java.util.logging.Level.WARNING,
                                    String.format(
                                            "Unable to parse claim data for %s (key: %s)",
                                            player.getUniqueId(), key),
                                    err);
                }
            }
            return claims;
        });
    }

    /**
     * Synchronously saves a claim to disk. Should only be called from async context.
     * Package-private for use by V1ToV2 migration.
     */
    void saveClaimSync(Claim claim) throws IOException {
        plugin.getLogger().finer("Claim: save");
        Config config = getConfig(claim.getOwner());
        String key = claim.getId().toString();
        UUID playerUUID = claim.getOwner().getUniqueId();
        String timestamp = Long.toString(claim.getTimestamp());
        List<String> rewards =
                claim.getRewards().stream().map(Reward::serialize).collect(Collectors.toList());
        config.getConfig().set(String.format(CONFIG_VERSION_KEY, key), YAML_CONFIG_VERSION);
        config.getConfig().set(String.format(CONFIG_TIMESTAMP_KEY, key), timestamp);
        config.getConfig().set(String.format(CONFIG_PLAYER_KEY, key), playerUUID.toString());
        config.getConfig().set(String.format(CONFIG_REWARDS_KEY, key), rewards);
        config.saveConfig(true);
    }

    @Override
    public CompletableFuture<Void> saveClaim(Claim claim) throws CompletionException {
        return CompletableFuture.runAsync(() -> {
            try {
                saveClaimSync(claim);
            } catch (IOException e) {
                plugin.getLogger()
                        .log(
                                java.util.logging.Level.SEVERE,
                                String.format(
                                        "Failed to save claim for player %s", claim.getOwner().getUniqueId()),
                                e);
                throw new CompletionException(e);
            }
        });
    }

    /**
     * Synchronously deletes a claim from disk. Should only be called from async context.
     */
    private void deleteClaimSync(Claim claim) {
        plugin.getLogger().finer(String.format("Claim: rm %s %d", claim.getOwner().getUniqueId(), claim.getTimestamp()));
        Config config = getConfig(claim.getOwner());
        UUID uuid = claim.getId();
        config.getConfig().set(uuid.toString(), null);
        try {
            config.saveConfig(true);
        } catch (IOException e) {
            plugin.getLogger()
                    .log(
                            java.util.logging.Level.SEVERE,
                            String.format("Failed to delete claim for player %s", claim.getOwner().getUniqueId()),
                            e);
        }
    }

    @Override
    public CompletableFuture<Void> deleteClaim(Claim claim) throws CompletionException {
        return CompletableFuture.runAsync(() -> {
            deleteClaimSync(claim);
        });
    }
}
