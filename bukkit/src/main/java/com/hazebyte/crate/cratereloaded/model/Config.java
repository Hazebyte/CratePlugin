package com.hazebyte.crate.cratereloaded.model;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config implements Storage {

    private final JavaPlugin plugin;
    private final File file;
    private FileConfiguration fileConfiguration;

    public Config(JavaPlugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;

        if (plugin == null) throw new NullPointerException("plugin");
        if (file == null) throw new NullPointerException("file");
    }

    public FileConfiguration getConfig() {
        if (fileConfiguration == null) {
            fileConfiguration = YamlConfiguration.loadConfiguration(file);
        }

        return fileConfiguration;
    }

    public File getFile() {
        return file;
    }

    public void saveConfig() throws IOException {
        fileConfiguration.save(file);
    }

    Lock configLock = new ReentrantLock();

    // Potential issue if anyone uses saveConfig in the mainThread: will lock thread
    public void saveConfig(boolean lock) throws IOException {
        if (lock) {
            configLock.lock();
            try {
                fileConfiguration.save(file);
            } finally {
                configLock.unlock();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Config that = (Config) o;
        return Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }
}
