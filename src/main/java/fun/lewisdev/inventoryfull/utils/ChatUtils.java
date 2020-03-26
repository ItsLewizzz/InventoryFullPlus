package fun.lewisdev.inventoryfull.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
