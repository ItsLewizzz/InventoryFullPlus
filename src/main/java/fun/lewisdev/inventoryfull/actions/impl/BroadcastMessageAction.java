package fun.lewisdev.inventoryfull.actions.impl;

import fun.lewisdev.inventoryfull.actions.Action;
import fun.lewisdev.inventoryfull.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BroadcastMessageAction implements Action {

    @Override
    public String getIdentifier() {
        return "BROADCAST";
    }

    @Override
    public void execute(Player player, String data) {
        final String message = TextUtil.color(data);
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(message));
    }
}
