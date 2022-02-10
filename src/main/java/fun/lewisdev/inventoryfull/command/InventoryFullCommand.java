package fun.lewisdev.inventoryfull.command;

import fun.lewisdev.inventoryfull.InventoryFullPlusPlugin;
import fun.lewisdev.inventoryfull.config.Messages;
import fun.lewisdev.inventoryfull.player.PlayerManager;
import fun.lewisdev.inventoryfull.utils.TextUtil;
import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@Command("inventoryfullplus")
@Alias({"inventoryfull", "invfull", "if"})
public class InventoryFullCommand extends CommandBase {

    private final InventoryFullPlusPlugin plugin;
    private final PlayerManager playerManager;

    public InventoryFullCommand(InventoryFullPlusPlugin plugin) {
        this.plugin = plugin;
        this.playerManager = plugin.getPlayerManager();
    }

    @Default
    public void defaultCommand(CommandSender sender) {
        final String version = plugin.getDescription().getVersion();
        if (!sender.hasPermission("inventoryfull.admin")) {
            sender.sendMessage(TextUtil.color("&7Server is running {#5cd7b0}InventoryFull+ &7v" + version + " By ItsLewizzz"));
            return;
        }

        Messages.HELP.send(sender, "{VERSION}", version);
    }

    @SubCommand("reload")
    @Permission("inventoryfull.admin")
    public void reloadSubCommand(CommandSender sender) {
        final long start = System.currentTimeMillis();
        plugin.onReload();
        Messages.RELOAD.send(sender, "{TIME}", System.currentTimeMillis() - start);
    }

    @SubCommand("toggle")
    @Permission("inventoryfull.toggle")
    public void toggleSubCommand(Player player) {
        final UUID uuid = player.getUniqueId();
        if (playerManager.isPlayerNotifications(uuid)) {
            playerManager.setPlayerNotifications(uuid, false);
            Messages.TOGGLE_DISABLE.send(player);
        } else {
            playerManager.setPlayerNotifications(uuid, true);
            Messages.TOGGLE_ENABLE.send(player);
        }
    }
}
