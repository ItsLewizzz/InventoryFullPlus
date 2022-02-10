package fun.lewisdev.inventoryfull.actions.impl;

import fun.lewisdev.inventoryfull.actions.Action;
import org.bukkit.entity.Player;

public class CommandAction implements Action {

    @Override
    public String getIdentifier() {
        return "COMMAND";
    }

    @Override
    public void execute(Player player, String data) {
        player.performCommand(data.contains("/") ? data.replace("/", "") : data);
    }
}
