package com.hazebyte.crate.cratereloaded.component.impl;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.component.RateLimitServiceComponent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleRateLimitServiceComponentImpl implements RateLimitServiceComponent {

    private final Map<UUID, Long> requestIdToTime;

    private final CorePlugin plugin;
    private final PluginSettingComponent settings;

    private final long taskPeriod = 20 * 60 * 5; // 5 minutes
    private final long taskDelay = 20 * 60 * 5; // 5 minutes

    public SimpleRateLimitServiceComponentImpl(CorePlugin plugin, PluginSettingComponent settings) {
        this.plugin = plugin;
        this.settings = settings;
        requestIdToTime = new HashMap<>();

        startTaskTimerToClearMap();
    }

    @Override
    public boolean isRateLimited(UUID id) {
        long delayInMillis = getRequestDelay();
        long currentTimeMillis = System.currentTimeMillis();
        long lastActionTimeMillis = requestIdToTime.getOrDefault(id, 0L);

        return lastActionTimeMillis + delayInMillis > currentTimeMillis;
    }

    @Override
    public void request(UUID id) {
        requestIdToTime.put(id, System.currentTimeMillis());
    }

    @Override
    public long getMillisTilRateIsLifted(UUID id) {
        long delayInMillis = getRequestDelay();
        long currentTimeMillis = System.currentTimeMillis();
        long lastActionTimeMillis = requestIdToTime.getOrDefault(id, currentTimeMillis);

        long timeLeftInMillis = (lastActionTimeMillis + delayInMillis) - currentTimeMillis;
        return timeLeftInMillis;
    }

    private void startTaskTimerToClearMap() {
        plugin.getServer().getScheduler().runTaskTimer(plugin, () -> requestIdToTime.clear(), taskDelay, taskPeriod);
    }

    private long getRequestDelay() {
        return (long) (settings.getCrateInteractionCooldown() * 1000);
    }
}
