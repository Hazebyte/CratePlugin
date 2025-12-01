package com.hazebyte.crate.cratereloaded.menu;

import org.bukkit.entity.Player;

public class ItemClickEvent {
    private final Player player;
    private boolean update, close, back, forward;

    public ItemClickEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean willUpdate() {
        return update;
    }

    public void setWillUpdate(boolean update) {
        this.update = update;
    }

    public boolean willClose() {
        return close;
    }

    public void setWillClose(boolean close) {
        this.close = close;
    }

    public boolean willGoBack() {
        return back;
    }

    public void setWillGoBack(boolean back) {
        this.back = back;
    }

    public boolean willGoForward() {
        return forward;
    }

    public void setWillGoForward(boolean forward) {
        this.forward = forward;
    }
}
