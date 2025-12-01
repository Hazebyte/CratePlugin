package com.hazebyte.crate.cratereloaded.provider;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.exception.items.DependencyNotEnabledException;
import com.hazebyte.crate.exception.items.InvalidInputException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ProviderSelector<T extends Provider> {

    protected JavaPlugin plugin;

    protected Map<String, Provider> providers = new HashMap<>();

    protected class Provider {
        /** Normalized name that is all uppercase */
        String normalizedName;

        /** Mapping of the normalized name to the provider e.g. VAULT => VaultProvider.class */
        Class<? extends T> providerClass;

        /** Mapping of normalized name to the canonical name e.g. VAULT => Vault. */
        String pluginName;

        /** Is this plugin required for load */
        boolean required;
    }

    public ProviderSelector(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public T getProvider(String preference)
            throws InvalidInputException, DependencyNotEnabledException, ReflectiveOperationException {
        if (preference == null || preference.isEmpty()) {
            throw new NullPointerException("preference is empty");
        }

        String normalizedPreference = preference.toUpperCase();
        Provider provider = providers.get(normalizedPreference);

        if (provider == null || provider.pluginName == null) {
            throw new InvalidInputException(normalizedPreference);
        }

        if (provider.required && !Bukkit.getPluginManager().isPluginEnabled(provider.pluginName)) {
            throw new DependencyNotEnabledException(provider.pluginName);
        }

        Class<? extends T> clazz = provider.providerClass;
        for (Constructor constructor : clazz.getConstructors()) {
            Class[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length > 1) {
                throw new Error("Unexpected provider with more than one parameter. Supported Providers are JavaPlugin,"
                        + " CorePlugin, and an empty constructor.");
            }

            if (paramTypes.length == 0) {
                return clazz.getConstructor().newInstance();
            }

            Class paramType = paramTypes[0];
            if (paramType == JavaPlugin.class) {
                return clazz.getConstructor(JavaPlugin.class).newInstance(plugin);
            } else if (paramType == CorePlugin.class) {
                return clazz.getConstructor(CorePlugin.class).newInstance(CorePlugin.getPlugin());
            } else {
                throw new Error(String.format("Provider constructor not found: [%s].", paramType.getName()));
            }
        }
        throw new Error("ProviderSelector: unexpected path");
    }

    // TODO: Allow injection of prerequisites i.e. Do we only allow 1.8?
    public void addProvider(String pluginName, Class<? extends T> providerClass, boolean required) {
        Provider provider = new Provider();
        provider.normalizedName = pluginName.toUpperCase();
        provider.pluginName = pluginName;
        provider.providerClass = providerClass;
        provider.required = required;
        providers.put(provider.normalizedName, provider);
    }

    public abstract T getNilProvider();
}
