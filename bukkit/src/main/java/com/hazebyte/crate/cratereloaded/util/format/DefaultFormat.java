package com.hazebyte.crate.cratereloaded.util.format;

import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.ServerVersion;
import com.hazebyte.crate.api.util.ColorUtil;
import com.hazebyte.crate.api.util.Messenger;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.util.Calendar;
import java.util.Map;
import org.bukkit.ChatColor;

public class DefaultFormat extends Format {

    public DefaultFormat(String message) {
        super(message);
    }

    @Override
    public String format(Object object) {
        return format();
    }

    public String format() {
        if (message == null || message.isEmpty()) return "";
        for (Map.Entry<String, String> entry :
                CorePlugin.getPlugin().getSettings().getCustomVariables().entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }
        message = message.replace("{prefix}", Messenger.getPrefix())
                .replace("{p}", Messenger.getPrefix())
                .replace(
                        "{day}",
                        String.valueOf(CorePlugin.getPlugin()
                                .getSettings()
                                .getCalendar()
                                .get(Calendar.DAY_OF_MONTH)))
                .replace(
                        "{days}",
                        String.valueOf(CorePlugin.getPlugin()
                                .getSettings()
                                .getCalendar()
                                .get(Calendar.DAY_OF_YEAR)))
                .replace(
                        "{month}",
                        String.valueOf(CorePlugin.getPlugin()
                                .getSettings()
                                .getCalendar()
                                .get(Calendar.MONTH)))
                .replace(
                        "{year}",
                        String.valueOf(CorePlugin.getPlugin()
                                .getSettings()
                                .getCalendar()
                                .get(Calendar.YEAR)));
        message = ChatColor.translateAlternateColorCodes('&', message);

        if (ServerVersion.getVersion().gte(ServerVersion.v1_16_R1)) {
            message = ColorUtil.of(message);
        }

        if (CrateAPI.getInstance().isReady()) {
            String list = CrateAPI.getCrateRegistrar().getCrateString();
            message = message.replace("{crates}", list);
        }
        return message;
    }
}
