package fun.lewisdev.inventoryfull.actions.impl;

import fun.lewisdev.inventoryfull.actions.Action;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ConsoleCommandAction implements Action {

    private final ConsoleCommandSender CONSOLE_COMMAND_SENDER = Bukkit.getConsoleSender();

    @Override
    public String getIdentifier() {
        return "CONSOLE";
    }

    @Override
    public void execute(Player player, String data) {
        Bukkit.dispatchCommand(CONSOLE_COMMAND_SENDER, data);
    }
}
