package com.hazebyte.crate.cratereloaded.dagger;

import com.hazebyte.crate.cratereloaded.component.PluginSettingComponent;
import com.hazebyte.crate.cratereloaded.listener.crate.LeftClickListener;
import com.hazebyte.crate.cratereloaded.listener.crate.RightClickListener;
import com.hazebyte.crate.cratereloaded.listener.inventory.CraftListener;
import com.hazebyte.crate.cratereloaded.listener.inventory.CreativeInventoryListener;
import com.hazebyte.crate.cratereloaded.listener.inventory.InventoryListener;
import com.hazebyte.crate.cratereloaded.listener.inventory.ItemFrameListener;
import com.hazebyte.crate.cratereloaded.listener.inventory.ShulkerBoxListener;
import com.hazebyte.crate.cratereloaded.listener.original.ClaimMessageListener;
import com.hazebyte.crate.cratereloaded.listener.original.KeyListener;
import com.hazebyte.crate.cratereloaded.listener.original.MysteryListener;
import com.hazebyte.crate.cratereloaded.listener.original.SupplyCrateListener;
import com.hazebyte.crate.cratereloaded.listener.original.ThrottleBlockPlaceListener;
import com.hazebyte.crate.cratereloaded.listener.original.WorldLoadListener;
import com.hazebyte.crate.cratereloaded.menuV2.InventoryHistoryManager;
import com.hazebyte.crate.cratereloaded.menuV2.InventoryManager;
import com.hazebyte.crate.cratereloaded.menuV2.InventoryManagerListener;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import javax.inject.Singleton;
import lombok.NonNull;
import org.bukkit.event.Listener;

@Module
public class BukkitListenerModule {

    @Provides
    @Singleton
    public InventoryManagerListener provideInventoryManagerListener(
            @NonNull InventoryManager inventoryManager, @NonNull InventoryHistoryManager inventoryHistoryManager) {
        return new InventoryManagerListener(inventoryManager, inventoryHistoryManager);
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideInventoryManagerListenerAsListener(InventoryManagerListener listener) {
        return listener;
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideWorldLoadListener() {
        return new WorldLoadListener();
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideLeftClickListener() {
        return new LeftClickListener();
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideRightClickListener(@NonNull PluginSettingComponent settings) {
        return new RightClickListener(settings);
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideCreativeInventoryListener(@NonNull PluginSettingComponent settings) {
        return new CreativeInventoryListener(settings);
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideItemFrameListener(@NonNull PluginSettingComponent settings) {
        return new ItemFrameListener(settings);
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideCraftListener(@NonNull PluginSettingComponent settings) {
        return new CraftListener(settings);
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideInventoryListener(@NonNull PluginSettingComponent settings) {
        return new InventoryListener(settings);
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideShulkerBoxListener(@NonNull PluginSettingComponent settings) {
        return new ShulkerBoxListener(settings);
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideSupplyCrateListener() {
        return new SupplyCrateListener();
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideThrottleBlockPlaceListener() {
        return new ThrottleBlockPlaceListener();
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideKeyListener() {
        return new KeyListener();
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideMysteryListener() {
        return new MysteryListener();
    }

    @Provides
    @Singleton
    @IntoSet
    public Listener provideClaimMessageListener(@NonNull PluginSettingComponent settings) {
        return new ClaimMessageListener(settings);
    }
}
