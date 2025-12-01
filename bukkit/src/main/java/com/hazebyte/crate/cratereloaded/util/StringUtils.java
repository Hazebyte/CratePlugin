package com.hazebyte.crate.cratereloaded.util;

import com.hazebyte.crate.api.util.Messages;
import com.hazebyte.crate.api.util.Replacer;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StringUtils {
    private static final Pattern spigotHex = Pattern.compile("&x(&[a-f0-9]){6}", Pattern.CASE_INSENSITIVE);

    /**
     * Regular expression for validating floating-point number strings.
     * This regex pattern matches Java floating-point literals including:
     * - Decimal numbers (e.g., "123", "123.456")
     * - Scientific notation (e.g., "1.23e10", "1E-5")
     * - Hexadecimal floating-point (e.g., "0x1.8p3")
     * - Special values ("NaN", "Infinity")
     * - Optional leading/trailing whitespace
     * - Optional type suffixes (f, F, d, D)
     *
     * Based on Java Language Specification section 3.10.2 for floating-point literals.
     */
    private static final String FLOATING_POINT_REGEX;

    static {
        final String Digits = "(\\p{Digit}+)";
        final String HexDigits = "(\\p{XDigit}+)";
        // An exponent is 'e' or 'E' followed by an optionally signed decimal integer
        final String Exp = "[eE][+-]?" + Digits;

        FLOATING_POINT_REGEX = "[\\x00-\\x20]*"  // Optional leading whitespace
                + "[+-]?("  // Optional sign character
                + "NaN|"  // "NaN" string
                + "Infinity|"  // "Infinity" string
                // Decimal floating-point patterns:
                + "(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|"  // Digits . Digits ExponentPart
                + "(\\.(" + Digits + ")(" + Exp + ")?)|"  // . Digits ExponentPart
                // Hexadecimal floating-point patterns:
                + "(("
                + "(0[xX]" + HexDigits + "(\\.)?)|"  // 0x HexDigits .
                + "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")"  // 0x HexDigits . HexDigits
                + ")[pP][+-]?" + Digits + "))"
                + "[fFdD]?))"  // Optional float/double suffix
                + "[\\x00-\\x20]*";  // Optional trailing whitespace
    }

    public static boolean isInteger(String str) {
        for (Character c : str.toCharArray()) {
            if (!(Character.isDigit(c))) return false;
        }
        return true;
    }

    /**
     * Validates whether a string represents a valid Java floating-point number.
     * Supports decimal notation, scientific notation, hexadecimal floating-point,
     * and special values like "NaN" and "Infinity".
     *
     * @param str the string to validate
     * @return true if the string is a valid floating-point number, false otherwise
     */
    public static boolean isDouble(String str) {
        return Pattern.matches(FLOATING_POINT_REGEX, str);
    }

    public static List<String> splitEqually(String text, int size) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        List<String> ret = new ArrayList<>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }

    public static String replaceOnce(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, 1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if (!isEmpty(text) && !isEmpty(searchString) && replacement != null && max != 0) {
            int start = 0;
            int end = text.indexOf(searchString, start);
            if (end == -1) {
                return text;
            } else {
                int replLength = searchString.length();
                int increase = replacement.length() - replLength;
                increase = Math.max(increase, 0);
                increase *= max < 0 ? 16 : (Math.min(max, 64));

                StringBuilder buf;
                for (buf = new StringBuilder(text.length() + increase);
                        end != -1;
                        end = text.indexOf(searchString, start)) {
                    buf.append(text, start, end).append(replacement);
                    start = end + replLength;
                    --max;
                    if (max == 0) {
                        break;
                    }
                }

                buf.append(text.substring(start));
                return buf.toString();
            }
        } else {
            return text;
        }
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static BaseComponent[] formatString(CommandSender sender, String string) {
        ComponentBuilder components = null;
        if (string.toLowerCase().startsWith("[format]: ")) {
            string = string.substring(9);
            for (String component : string.split(" ;; ")) {
                TextComponent formatted = new TextComponent("");
                String msg = null, hover = null, click = null;

                for (String sections : component.split(" :: ")) {
                    if (msg == null) {
                        msg = sections;
                        continue;
                    }
                    if (hover == null) {
                        hover = sections;
                        continue;
                    }
                    if (click == null) {
                        click = sections;
                    }
                }
                // If the for loop retrieves null value inform the user.

                if (msg == null) {
                    msg = "&cImproper text format please see guide for info!";
                }
                if (hover == null) {
                    hover = "&cImproper hover format please see guide for info!";
                }
                if (click == null) {
                    click = "&cImproper click format please see guide for info!";
                }
                // Set the text, colour it & translate the placeholders.
                formatted.setText(ChatColorUtil.colorify(translatePlaceholders(msg, sender)));

                // if !blank we colour the text, translate its placeholders & assign it as the hover/click
                // event of the textcomponent.
                // this also means that if it is <blank> then we ignore it, and the text doesn't get that
                // event added.
                if (!hover.equalsIgnoreCase("<blank>")) {
                    hover = ChatColorUtil.colorify(hover);
                    formatted.setHoverEvent(new HoverEvent(
                            HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder(translatePlaceholders(hover, sender)).create()));
                }
                if (!click.equalsIgnoreCase("<blank>")) {
                    click = ChatColorUtil.colorify(click);
                    formatted.setClickEvent(new ClickEvent(
                            click.startsWith("url:") ? ClickEvent.Action.OPEN_URL : ClickEvent.Action.RUN_COMMAND,
                            translatePlaceholders(click, sender).replaceFirst("url:", "")));
                }
                // if component doesnt exist make one. component is like List<TextComponent>
                // whereby it compiles into one line via BaseComponent[].
                // if the component exist, we just add to the component.
                // think of it like "test :: hover :: click ".append("test2 :: hover2 :: click2")
                if (components == null) {
                    components = new ComponentBuilder(formatted);
                } else {
                    components.append(formatted);
                }
            }
        } else {
            components = new ComponentBuilder(string);
        }
        return components == null ? new ComponentBuilder("").create() : components.create();
    }

    public static List<BaseComponent[]> formatStringList(CommandSender sender, List<String> components) {
        List<BaseComponent[]> list = new ArrayList<>();
        components.forEach(string -> list.add(formatString(sender, string)));
        return list;
    }

    private static String translatePlaceholders(String text, CommandSender sender) {
        text = Replacer.replace(text);
        if (text.contains(Messages.MESSAGE_NOT_FOUND)) {
            CorePlugin.getPlugin().getLogger().info(text);
            return text;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            text = Replacer.replace(text, player);
            text = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")
                    ? PlaceholderAPI.setPlaceholders(player, text)
                    : text;
        }
        return text;
    }

    public static String normaliseHex(String msg) {
        msg = msg.replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "&");
        Matcher matcher = spigotHex.matcher(msg);
        while (matcher.find()) {
            String group = matcher.group();
            msg = msg.replace(group, String.format("{#%s}", group.substring(2).replace("&", "")));
        }
        return msg;
    }
}
