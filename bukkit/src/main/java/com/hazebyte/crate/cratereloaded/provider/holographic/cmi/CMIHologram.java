package com.hazebyte.crate.cratereloaded.provider.holographic.cmi;

import com.Zrips.CMI.CMI;
import com.hazebyte.crate.cratereloaded.provider.holographic.HologramWrapper;
import com.hazebyte.crate.cratereloaded.provider.holographic.VisibilityAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CMIHologram implements HologramWrapper {

    private final List<String> texts;
    private final com.Zrips.CMI.Modules.Holograms.CMIHologram hologram;
    private JavaPlugin plugin;
    boolean isDeleted = false;

    public CMIHologram(JavaPlugin plugin, Location location) {
        this.plugin = plugin;
        texts = new ArrayList<>();
        String hologramName = String.format("cr-%s", UUID.randomUUID());
        hologram = new com.Zrips.CMI.Modules.Holograms.CMIHologram(hologramName, location);
        CMI.getInstance().getHologramManager().addHologram(hologram);
    }

    public CMIHologram(com.Zrips.CMI.Modules.Holograms.CMIHologram hologram) {
        this.hologram = hologram;
        texts = hologram.getLines();
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode += 37 * plugin.hashCode();
        hashCode += 37 * texts.hashCode();
        hashCode += 37 * getX();
        hashCode += 37 * getY();
        hashCode += 37 * getZ();
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CMIHologram)) {
            return false;
        }

        CMIHologram hologram = (CMIHologram) obj;
        if (!this.plugin.equals(hologram.plugin)) {
            return false;
        }

        if (!this.texts.equals(hologram.texts)) {
            return false;
        }

        return this.getLocation().equals(hologram.getLocation());
    }

    @Override
    public void addItemLine(ItemStack item) {}

    @Override
    public void addTextLine(String text) {
        texts.add(text);
        hologram.setLines(texts);
        hologram.update();
    }

    @Override
    public void clearLines() {
        texts.clear();
        hologram.setLines(texts);
        hologram.update();
    }

    @Override
    public void delete() {
        CMI.getInstance().getHologramManager().removeHolo(hologram);
        isDeleted = true;
    }

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
        return hologram.getLoc();
    }

    @Override
    public VisibilityAPI getVisibilityManager() {
        return null;
    }

    @Override
    public World getWorld() {
        return hologram.getWorld();
    }

    @Override
    public double getX() {
        return hologram.getLoc().getX();
    }

    @Override
    public double getY() {
        return hologram.getLoc().getY();
    }

    @Override
    public double getZ() {
        return hologram.getLoc().getZ();
    }

    @Override
    public void insertItemLine(int index, ItemStack item) {}

    @Override
    public void insertTextLine(int index, String text) {
        texts.add(index, text);
        hologram.setLines(texts);
        hologram.update();
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public void removeLine(int index) {
        texts.remove(index);
        hologram.setLines(texts);
        hologram.update();
    }

    @Override
    public int size() {
        return texts.size();
    }

    @Override
    public void teleport(Location location) {
        hologram.setLoc(location);
        hologram.update();
    }

    @Override
    public void teleport(World world, double x, double y, double z) {
        hologram.setLoc(new Location(world, x, y, z));
        hologram.update();
    }
}
