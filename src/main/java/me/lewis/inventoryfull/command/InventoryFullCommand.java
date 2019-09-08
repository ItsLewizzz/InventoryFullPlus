package me.lewis.inventoryfull.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;


import me.lewis.inventoryfull.InventoryFullPlus;

public class InventoryFullCommand implements CommandExecutor {
	
	private InventoryFullPlus plugin;
	
	public InventoryFullCommand(InventoryFullPlus plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
			
		if (args.length == 0 || args[0].equalsIgnoreCase("help")) { 
				
			PluginDescriptionFile pdfFile = plugin.getDescription();
			
			if (!sender.hasPermission("inventoryfull.help")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l> &7Server is running &3InventoryFull&e+ &ev" + pdfFile.getVersion() + " &7By &6ItsLewizzz"));
				return true;
			}
				
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3&lInventoryFull&e&l+ &ev" + pdfFile.getVersion()));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6By ItsLewizzz"));
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&ohttps://www.spigotmc.org/resources/inventoryfull.31544/"));
		    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
		    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lCOMMANDS"));
		    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/inventoryfull reload &7- &fReload the plugin"));
		    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &e/inventoryfull toggle &7- &fToggle alerts"));
		    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
		    return true;
		}
			
        if (args[0].equalsIgnoreCase("reload")) {
        	
        	if (!sender.hasPermission("inventoryfull.reload")) {
        		sender.sendMessage(plugin.getConfigManager().getMessageNoPermission());
        		return true;
        	} 
        	
        	long startTime = System.currentTimeMillis();
        	plugin.reload();
        	sender.sendMessage(plugin.getConfigManager().getMessageReload().replace("%time%", String.valueOf(System.currentTimeMillis()-startTime)));
        	return true;
		}
		
		if (args[0].equalsIgnoreCase("toggle")) {
			
			if (!(sender instanceof Player)) {
        		sender.sendMessage("You cannot toggle alerts as you are console.");
 	        	return true;
        	}
			
        	if (!sender.hasPermission("inventoryfull.toggle")) {
        		sender.sendMessage(plugin.getConfigManager().getMessageNoPermission());
        		return true;
        	}
        		
        	Player player = (Player)sender;
	        		
        	if (plugin.getConfigManager().isToggleGUI()) {
        		plugin.getInventoryManager().openToggleGUI(player);
        		return true;
        	}
        	
        	if(plugin.getDataManager().hasAlerts(player.getUniqueId())) {
        		plugin.getDataManager().setAlerts(player.getUniqueId(), false);
        		player.sendMessage(plugin.getConfigManager().getMessageToggleDisable());
        	}else{
        		plugin.getDataManager().setAlerts(player.getUniqueId(), true);
        		player.sendMessage(plugin.getConfigManager().getMessageToggleEnable());
        	}
		}
        
		return true;
	}
}
