package com.hazebyte.crate.cratereloaded.provider.holographic.hd;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.hazebyte.crate.cratereloaded.provider.holographic.HologramWrapper;
import com.hazebyte.crate.cratereloaded.provider.holographic.VisibilityAPI;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HDHologram implements HologramWrapper {

    private final Hologram hologram;
    private VisibilityAPI visibilityAPI;

    public HDHologram(JavaPlugin plugin, Location location) {
        hologram = HologramsAPI.createHologram(plugin, location);
        visibilityAPI = new HDVisbilityManager(hologram.getVisibilityManager());
    }

    public HDHologram(Hologram hologram) {
        this.hologram = hologram;
    }

    @Override
    public void addItemLine(ItemStack item) {
        hologram.appendItemLine(item);
    }

    @Override
    public void addTextLine(String text) {
        hologram.appendTextLine(text);
    }

    @Override
    public void clearLines() {
        hologram.clearLines();
    }

    @Override
    public void delete() {
        hologram.delete();
    }

    @Override
    public long getCreationTimestamp() {
        return hologram.getCreationTimestamp();
    }

    @Override
    public double getHeight() {
        return hologram.getHeight();
    }

    @Override
    public void getLine(int index) {
        // TODO: Implement HologramLine
    }

    @Override
    public Location getLocation() {
        return hologram.getLocation();
    }

    @Override
    public VisibilityAPI getVisibilityManager() {
        return visibilityAPI;
    }

    @Override
    public World getWorld() {
        return hologram.getWorld();
    }

    @Override
    public double getX() {
        return hologram.getX();
    }

    @Override
    public double getY() {
        return hologram.getY();
    }

    @Override
    public double getZ() {
        return hologram.getZ();
    }

    @Override
    public void insertItemLine(int index, ItemStack item) {
        hologram.insertItemLine(index, item);
    }

    @Override
    public void insertTextLine(int index, String text) {
        hologram.insertTextLine(index, text);
    }

    @Override
    public boolean isDeleted() {
        return hologram.isDeleted();
    }

    @Override
    public void removeLine(int index) {
        hologram.removeLine(index);
    }

    @Override
    public int size() {
        return hologram.size();
    }

    @Override
    public void teleport(Location location) {
        hologram.teleport(location);
    }

    @Override
    public void teleport(World world, double x, double y, double z) {
        hologram.teleport(world, x, y, z);
    }
}
