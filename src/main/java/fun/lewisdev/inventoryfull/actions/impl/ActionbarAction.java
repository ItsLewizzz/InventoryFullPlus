package fun.lewisdev.inventoryfull.actions.impl;

import fun.lewisdev.inventoryfull.actions.Action;
import fun.lewisdev.inventoryfull.utils.TextUtil;
import fun.lewisdev.inventoryfull.utils.reflection.ActionBar;
import org.bukkit.entity.Player;

public class ActionbarAction implements Action {

    @Override
    public String getIdentifier() {
        return "ACTIONBAR";
    }

    @Override
    public void execute(Player player, String data) {
        ActionBar.sendActionBar(player, TextUtil.color(data));
    }
}
