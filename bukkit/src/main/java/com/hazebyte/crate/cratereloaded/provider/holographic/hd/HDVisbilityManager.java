package com.hazebyte.crate.cratereloaded.provider.holographic.hd;

import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.hazebyte.crate.cratereloaded.provider.holographic.VisibilityAPI;
import org.bukkit.entity.Player;

public class HDVisbilityManager implements VisibilityAPI {

    private final VisibilityManager manager;

    public HDVisbilityManager(VisibilityManager manager) {
        this.manager = manager;
    }

    @Override
    public void hideTo(Player player) {
        manager.hideTo(player);
    }

    @Override
    public boolean isVisibleByDefault() {
        return manager.isVisibleByDefault();
    }

    @Override
    public void setVisibleByDefault(boolean visibleByDefault) {
        manager.setVisibleByDefault(visibleByDefault);
    }

    @Override
    public boolean isVisibleTo(Player player) {
        return manager.isVisibleTo(player);
    }

    @Override
    public void resetVisibility(Player player) {
        manager.resetVisibility(player);
    }

    @Override
    public void resetVisibilityAll() {
        manager.resetVisibilityAll();
    }

    @Override
    public void showTo(Player player) {
        manager.showTo(player);
    }
}
