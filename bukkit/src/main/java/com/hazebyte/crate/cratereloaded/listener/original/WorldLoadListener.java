package com.hazebyte.crate.cratereloaded.listener.original;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.crate.BlockCrateHandler;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldLoadListener implements Listener {

    private static final List<String> worldNames = new ArrayList<>();
    private static BukkitRunnable task;

    public static boolean add(String worldName) {
        if (worldNames.contains(worldName)) return false;
        worldNames.add(worldName);
        return true;
    }

    public static boolean remove(String worldName) {
        return worldNames.contains(worldName) && worldNames.remove(worldName);
    }

    public static void clear() {
        worldNames.clear();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();
        String name = world.getName();
        add(name);

        if (task == null) {
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    BlockCrateHandler registrar = (BlockCrateHandler) CrateAPI.getBlockCrateRegistrar();
                    CorePlugin.getPlugin()
                            .getLogger()
                            .info(String.format("Detected World Load: %s. Reloading key crates.", worldNames));
                    worldNames.clear();
                    registrar.reload();
                    task = null;
                }
            };
            task.runTaskLater(CorePlugin.getPlugin(), 60);
        }
    }
}
