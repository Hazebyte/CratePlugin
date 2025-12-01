package com.hazebyte.crate.cratereloaded.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Camera {

    private static final Map<String, Class<?>> classCache = new HashMap<>();
    private static final Map<String, Method> methodCache = new HashMap<>();

    static {
        classCache.put("LivingEntity", LivingEntity.class);

        try {
            methodCache.put(
                    "getTargetBlock", classCache.get("LivingEntity").getMethod("getTargetBlock", Set.class, int.class));
        } catch (NoSuchMethodException e) {
            // Expected on older Minecraft versions - fall back to deprecated method
        }
    }

    private static final int DEFAULT_RANGE = 30;

    private Camera() {}

    /**
     * Returns the block within the players crosshair. Max range of 30.
     *
     * @param player
     * @return
     */
    public static Block getTargetBlock(Player player) {
        try {
            Object object = methodCache.get("getTargetBlock").invoke(player, null, DEFAULT_RANGE);
            return (Block) object;
        } catch (Exception e) {
            // Fall back to deprecated method if reflection fails
        }
        return null;
    }

    /**
     * Returns the block within the players crosshair with the range.
     *
     * @param player
     * @param range
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Block getTargetBlock(Player player, int range) {
        return player.getTargetBlock(null, range);
    }

    /**
     * Returns the location of the block the player is viewing.
     *
     * @param player
     * @return
     */
    public static Location getTargetBlockLocation(Player player) {
        Block block = getTargetBlock(player);
        return block != null ? block.getLocation() : null;
    }

    /**
     * Returns the location of the block the player is viewing with the range
     *
     * @param player
     * @return
     */
    public static Location getTargetBlockLocation(Player player, int distance) {
        return getTargetBlock(player, distance).getLocation();
    }
}
