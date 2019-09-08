package me.lewis.inventoryfull.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.lewis.inventoryfull.InventoryFullPlus;
import me.lewis.inventoryfull.events.InventoryFullEvent;

public class BreakEvent implements Listener {
	
	private final InventoryFullPlus plugin;
	
	public BreakEvent(InventoryFullPlus plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		if (plugin.getConfigManager().getDisabledWorlds().contains(event.getPlayer().getWorld().getName())) return;
		if (plugin.getConfigManager().getDisabledGamemodes().contains(event.getPlayer().getGameMode().toString())) return; 			
		
		if (player.getInventory().firstEmpty() == -1) {
			if(!plugin.getDataManager().hasAlerts(player.getUniqueId())) return;
						
			InventoryFullEvent e = new InventoryFullEvent(player, event.getBlock());
			Bukkit.getPluginManager().callEvent(e);
			if(e.isCancelled()) return;
			plugin.getAlertManager().sendAlerts(player);

			if (plugin.getConfigManager().isStopBlockBreak()) event.setCancelled(true);
		}
	}
}
