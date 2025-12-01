package com.hazebyte.crate.cratereloaded.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

    private LocationUtil() {}

    /**
     * Checks whether this location is inside the spawn radius
     *
     * @return true if this is inside the spawn radius and false otherwise.
     */
    public static boolean isInSpawnRegion(Location location) {
        Location spawn = location.getWorld().getSpawnLocation();

        return Math.abs(location.distance(spawn)) <= Bukkit.getSpawnRadius();
    }

    public static Location getBlockCenter(Location location) {
        return new Location(
                location.getWorld(),
                location.getBlockX() + 0.5,
                location.getBlockY() + 0.5,
                location.getBlockZ() + 0.5);
    }

    public static Location getOffset(Location location, int offset) {
        double yOffset = 1 + (offset * 0.2);
        return new Location(location.getWorld(), location.getX(), location.getY() + yOffset, location.getZ());
    }

    public static String toKey(Location location) {
        return String.format(
                "world=%s,x=%s,y=%s,z=%s",
                location.getWorld().getName(),
                Math.floor(location.getX()),
                Math.floor(location.getY()),
                Math.floor(location.getZ()));
    }
}
