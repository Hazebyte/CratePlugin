package com.hazebyte.crate.cratereloaded.provider.holographic;

import org.bukkit.entity.Player;

public interface VisibilityAPI {
    void hideTo(Player player);

    boolean isVisibleByDefault();

    void setVisibleByDefault(boolean visibleByDefault);

    boolean isVisibleTo(Player player);

    void resetVisibility(Player player);

    void resetVisibilityAll();

    void showTo(Player player);
}
