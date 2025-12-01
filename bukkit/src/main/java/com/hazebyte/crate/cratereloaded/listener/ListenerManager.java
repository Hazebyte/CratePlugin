package com.hazebyte.crate.cratereloaded.listener;

import com.google.common.base.Strings;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.listener.resolver.CitizenResolver;
import com.hazebyte.crate.cratereloaded.listener.resolver.ShulkerResolver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import org.bukkit.event.Listener;

public class ListenerManager {

    private final CorePlugin plugin;
    private final List<Listener> listeners;
    private final Map<String, Resolver> resolver;

    @Inject
    public ListenerManager(CorePlugin plugin, Set<Listener> providedListeners) {
        this.plugin = plugin;
        this.listeners = new ArrayList<>();
        this.resolver = new HashMap<>();
        addResolvers();

        // Register all listeners provided by Dagger
        // The add() method handles @Condition annotations (e.g., ShulkerBoxListener)
        for (Listener listener : providedListeners) {
            add(listener);
        }
    }

    public void add(Listener listener) {
        Condition annotation = listener.getClass().getAnnotation(Condition.class);
        if (annotation == null) {
            listeners.add(listener);
            register(listener);
            return;
        }

        String condition = annotation.value();

        if (!Strings.isNullOrEmpty(condition)) {
            Resolver resolver = getResolver(condition);
            if (resolver.resolve()) {
                listeners.add(listener);
                register(listener);
            }
        }
    }

    public void remove(Listener listener) {
        listeners.remove(listener);
    }

    public void addResolvers() {
        addResolver("shulker", new ShulkerResolver());
        addResolver("citizen", new CitizenResolver());
    }

    public void addResolver(String condition, Resolver resolver) {
        this.resolver.put(condition, resolver);
    }

    public Resolver getResolver(String string) {
        return resolver.get(string);
    }

    public List<Listener> getListeners() {
        return listeners;
    }

    private void register(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
