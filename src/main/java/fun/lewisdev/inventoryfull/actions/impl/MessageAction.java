package fun.lewisdev.inventoryfull.actions.impl;

import fun.lewisdev.inventoryfull.actions.Action;
import fun.lewisdev.inventoryfull.utils.TextUtil;
import org.bukkit.entity.Player;

public class MessageAction implements Action {

    @Override
    public String getIdentifier() {
        return "MESSAGE";
    }

    @Override
    public void execute(Player player, String data) {
        player.sendMessage(TextUtil.color(data));
    }
}
