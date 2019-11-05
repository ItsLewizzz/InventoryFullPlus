package me.lewis.inventoryfull.listeners;

import me.lewis.inventoryfull.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.lewis.inventoryfull.InventoryFullPlus;

public class JoinEvent implements Listener {

    private final InventoryFullPlus plugin;

    public JoinEvent(InventoryFullPlus plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfigManager().isUpdateCheck() && player.isOp() && !plugin.getUpdateManager().isLatestVersion()) {
            player.sendMessage(ChatUtils.color("&7An update for &3InventoryFull+ &7is available"));
            player.sendMessage(ChatUtils.color("  &fCurrent version: &cv" + plugin.getUpdateManager().getCurrentVersion()));
            player.sendMessage(ChatUtils.color("  &fNew version: &av" + plugin.getUpdateManager().getNewVersion()));
        }
    }

}
