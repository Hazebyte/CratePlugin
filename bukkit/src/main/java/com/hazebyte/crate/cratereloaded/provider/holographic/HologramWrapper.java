package com.hazebyte.crate.cratereloaded.provider.holographic;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public interface HologramWrapper {

    void addItemLine(ItemStack item);

    void addTextLine(String text);

    void clearLines();

    void delete();

    long getCreationTimestamp();

    double getHeight();

    void getLine(int index);

    Location getLocation();

    VisibilityAPI getVisibilityManager();

    World getWorld();

    double getX();

    double getY();

    double getZ();

    void insertItemLine(int index, ItemStack item);

    void insertTextLine(int index, String text);

    boolean isDeleted();

    void removeLine(int index);

    int size();

    void teleport(Location location);

    void teleport(World world, double x, double y, double z);
}
