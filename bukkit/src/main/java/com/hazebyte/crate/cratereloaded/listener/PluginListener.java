package com.hazebyte.crate.cratereloaded.listener;

import com.hazebyte.crate.api.util.Messenger;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.util.format.CustomFormat;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Listener;

public class PluginListener implements Listener {
    public void cancel(Cancellable event, HumanEntity entity, String messageKey, Object... format) {
        if (!(event.isCancelled())) {
            event.setCancelled(true);
            entity.closeInventory();

            Messenger.tell(entity, CustomFormat.format(CorePlugin.getPlugin().getMessage(messageKey), format));
        }
    }
}
