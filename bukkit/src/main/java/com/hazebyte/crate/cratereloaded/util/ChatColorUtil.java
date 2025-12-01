package com.hazebyte.crate.cratereloaded.util;

import org.bukkit.ChatColor;

/** Created by William on 1/16/2016. */
public class ChatColorUtil {
    public static String colorify(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
