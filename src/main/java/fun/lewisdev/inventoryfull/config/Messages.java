/*
 * XLTournaments Plugin
 * Copyright (c) 2021 - 2021 Lewis D (ItsLewizzz). All rights reserved.
 */

package fun.lewisdev.inventoryfull.config;

import fun.lewisdev.inventoryfull.utils.TextUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public enum Messages {

    PREFIX("prefix"),
    RELOAD("reload"),
    NO_PERMISSION("no_permission"),
    HELP("help"),

    TOGGLE_ENABLE("toggle_enable"),
    TOGGLE_DISABLE("toggle_disable");

    private static FileConfiguration config;
    private final String path;

    Messages(String path) {
        this.path = path;
    }

    public static void setConfiguration(FileConfiguration c) {
        config = c;
    }

    public void send(CommandSender receiver, Object... replacements) {
        Object value = config.get("messages." + this.path);

        String message;
        if (value == null) {
            message = "InventoryFullPlus: message not found (" + this.path + ")";
        }else {
            message = value instanceof List ? TextUtil.fromList((List<?>) value) : value.toString();
        }

        if (!message.isEmpty()) {
            receiver.sendMessage(TextUtil.color(replace(message, replacements)));
        }
    }

    private String replace(String message, Object... replacements) {
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 >= replacements.length) break;
            message = message.replace(String.valueOf(replacements[i]), String.valueOf(replacements[i + 1]));
        }

        String prefix = config.getString("messages." + PREFIX.getPath());
        return message.replace("{PREFIX}", prefix != null && !prefix.isEmpty() ? prefix : "");
    }

    public String getPath() {
        return this.path;
    }

}
