package me.lewis.inventoryfull.listeners;

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
	    
	    if (plugin.getConfigManager().isUpdateCheck() && player.isOp()) {
	    	if (!plugin.getUpdateManager().isLatestVersion()) {
	    		player.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
	    		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&lINVENTORYFULL+ UPDATE"));
	    		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &cAn update for InventoryFull+ is available"));
	    		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &7Current version: &cv" + plugin.getUpdateManager().getCurrentVersion()));
	    		player.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &7New version: &cv" + plugin.getUpdateManager().getNewVersion())); 	
	    		player.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
           	}}

	    }

}
