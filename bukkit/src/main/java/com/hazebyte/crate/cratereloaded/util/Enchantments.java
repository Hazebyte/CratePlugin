package com.hazebyte.crate.cratereloaded.util;

import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import org.bukkit.enchantments.Enchantment;

public class Enchantments {
    private static final Map<String, Enchantment> ENCHANTS = new HashMap<>();
    private static final StringJoiner enchantSB = new StringJoiner(", ");

    static {
        for (Enchantment e : Enchantment.values()) enchantSB.add(e.getName());

        put("environmental_protection", Enchantment.PROTECTION_ENVIRONMENTAL);
        put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
        put("fire_protection", Enchantment.PROTECTION_FIRE);
        put("fall_protection", Enchantment.PROTECTION_FALL);
        put("explosion_protection", Enchantment.PROTECTION_EXPLOSIONS);
        put("projectile_protection", Enchantment.PROTECTION_PROJECTILE);
        put("oxygen", Enchantment.OXYGEN);
        put("water_worker", Enchantment.WATER_WORKER);
        put("thorns", Enchantment.THORNS);
        put("depth_strider", Enchantment.DEPTH_STRIDER);
        put("frost_walker", "FROST_WALKER");
        put("binding_curse", "BINDING_CURSE");
        put("sharpness", Enchantment.DAMAGE_ALL);
        put("smite", Enchantment.DAMAGE_UNDEAD);
        put("bane_of_arthropods", Enchantment.DAMAGE_ARTHROPODS);
        put("fire_aspect", Enchantment.FIRE_ASPECT);
        put("looting", Enchantment.LOOT_BONUS_MOBS);
        put("sweeping_edge", "SWEEPING_EDGE");
        put("efficiency", Enchantment.DIG_SPEED);
        put("silk_touch", Enchantment.SILK_TOUCH);
        put("unbreaking", Enchantment.DURABILITY);
        put("fortune", Enchantment.LOOT_BONUS_BLOCKS);
        put("power", Enchantment.ARROW_DAMAGE);
        put("punch", Enchantment.ARROW_KNOCKBACK);
        put("flame", Enchantment.ARROW_FIRE);
        put("infinity", Enchantment.ARROW_INFINITE);
        put("luck", Enchantment.LUCK);
        put("lure", Enchantment.LURE);
        put("mending", "MENDING");
        put("vanishing_curse", "VANISHING_CURSE");

        put("channeling", "CHANNELING");
        put("impaling", "IMPALING");
        put("multishot", "MULTSHOT");
        put("piercing", "PIERCING");
        put("quick_charge", "QUICK_CHARGE");
    }

    private static void put(String name, Enchantment enchant) {
        ENCHANTS.put(name, enchant);
    }

    private static void put(String name, String enchant) {
        Enchantment enchantment = Enchantment.getByName(enchant);
        if (enchantment != null) {
            ENCHANTS.put(name, enchantment);
        } else {
            CorePlugin.getPlugin().getLogger().finer(enchant + " was not found.");
        }
    }

    public static Enchantment getByName(String name) {
        Enchantment enchantment = Enchantment.getByName(name.toUpperCase());
        return enchantment == null ? ENCHANTS.get(name.toLowerCase()) : enchantment;
    }

    public static String getStringFormat() {
        return enchantSB.toString();
    }
}
