package com.hazebyte.crate.cratereloaded.provider.holographic.none;

import com.hazebyte.crate.cratereloaded.provider.holographic.HologramWrapper;
import com.hazebyte.crate.cratereloaded.provider.holographic.VisibilityAPI;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class NoHologram implements HologramWrapper {
    @Override
    public void addItemLine(ItemStack item) {}

    @Override
    public void addTextLine(String text) {}

    @Override
    public void clearLines() {}

    @Override
    public void delete() {}

    @Override
    public long getCreationTimestamp() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public void getLine(int index) {}

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public VisibilityAPI getVisibilityManager() {
        return null;
    }

    @Override
    public World getWorld() {
        return null;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getZ() {
        return 0;
    }

    @Override
    public void insertItemLine(int index, ItemStack item) {}

    @Override
    public void insertTextLine(int index, String text) {}

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public void removeLine(int index) {}

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void teleport(Location location) {}

    @Override
    public void teleport(World world, double x, double y, double z) {}
}
