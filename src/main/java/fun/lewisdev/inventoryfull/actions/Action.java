package fun.lewisdev.inventoryfull.actions;

import org.bukkit.entity.Player;

public interface Action {

    String getIdentifier();

    void execute(Player player, String data);

}
