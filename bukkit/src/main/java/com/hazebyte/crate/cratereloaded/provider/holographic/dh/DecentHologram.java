package com.hazebyte.crate.cratereloaded.provider.holographic.dh;

import com.hazebyte.crate.cratereloaded.provider.holographic.HologramWrapper;
import com.hazebyte.crate.cratereloaded.provider.holographic.VisibilityAPI;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class DecentHologram implements HologramWrapper {

    private final Hologram hologram;

    public DecentHologram(Location location) {
        UUID uniqueId = UUID.randomUUID();
        hologram = DHAPI.createHologram(uniqueId.toString(), location);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecentHologram that = (DecentHologram) o;
        return Objects.equals(hologram.getId(), that.hologram.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(hologram.getId());
    }

    @Override
    public void addItemLine(ItemStack item) {
        DHAPI.addHologramLine(hologram, item);
    }

    @Override
    public void addTextLine(String text) {
        DHAPI.addHologramLine(hologram, text);
    }

    @Override
    public void clearLines() {
        int lines = hologram.getPage(0).getLines().size();
        for (int i = 0; i < lines; i++) {
            DHAPI.removeHologramLine(hologram, 0);
        }
    }

    @Override
    public void delete() {
        DHAPI.removeHologram(hologram.getName());
    }

    @Override
    public long getCreationTimestamp() {
        // Not Supported
        return -1;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public void getLine(int index) {
        // TODO: Implement Hologram Line
    }

    @Override
    public Location getLocation() {
        return hologram.getLocation();
    }

    @Override
    public VisibilityAPI getVisibilityManager() {
        return null;
    }

    @Override
    public World getWorld() {
        return hologram.getLocation().getWorld();
    }

    @Override
    public double getX() {
        return hologram.getLocation().getX();
    }

    @Override
    public double getY() {
        return hologram.getLocation().getY();
    }

    @Override
    public double getZ() {
        return hologram.getLocation().getZ();
    }

    @Override
    public void insertItemLine(int index, ItemStack item) {
        DHAPI.insertHologramLine(hologram, index, item);
    }

    @Override
    public void insertTextLine(int index, String text) {
        DHAPI.insertHologramLine(hologram, index, text);
    }

    @Override
    public boolean isDeleted() {
        return !hologram.isEnabled();
    }

    @Override
    public void removeLine(int index) {
        DHAPI.removeHologramLine(hologram, index);
    }

    @Override
    public int size() {
        HologramPage page = hologram.getPage(0);
        return page.getLines().size();
    }

    @Override
    public void teleport(Location location) {
        DHAPI.moveHologram(hologram, location);
    }

    @Override
    public void teleport(World world, double x, double y, double z) {
        DHAPI.moveHologram(hologram, new Location(world, x, y, z));
    }
}
