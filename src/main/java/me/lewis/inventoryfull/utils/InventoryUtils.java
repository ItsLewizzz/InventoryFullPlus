package me.lewis.inventoryfull.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.lewis.inventoryfull.InventoryFullPlus;
import me.lewis.inventoryfull.utils.XMaterial;

public class InventoryUtils implements Listener {
	
	private InventoryFullPlus plugin;
	
	public InventoryUtils(InventoryFullPlus plugin) {
		this.plugin = plugin;
	}
	
	public void openToggleGUI(Player player) {
		
		Inventory toggleGUI = Bukkit.createInventory(null, 9, plugin.getConfigManager().getToggleGUIName());
	    
		ItemStack enableItem = new ItemStack(XMaterial.LIME_WOOL.parseItem(1));
	    ItemMeta enableMeta = enableItem.getItemMeta();
	    enableMeta.setDisplayName(plugin.getConfigManager().getToggleGUIOnName());
	    enableItem.setItemMeta(enableMeta);
	    toggleGUI.setItem(2, enableItem);
	    
	    ItemStack disableItem = new ItemStack(XMaterial.RED_WOOL.parseItem(1));
	    ItemMeta disableMeta = disableItem.getItemMeta();
	    disableMeta.setDisplayName(plugin.getConfigManager().getToggleGUIOffName());
	    disableItem.setItemMeta(disableMeta);
	    toggleGUI.setItem(6, disableItem);

	    player.openInventory(toggleGUI);
  }
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
	    
	    if (event.getClickedInventory() == null) return;
	    
	    if (!event.getView().getTitle().equals(plugin.getConfigManager().getToggleGUIName())) return;
	    
	    if (clicked == null || !clicked.hasItemMeta()) return;
		 	
	    Material enable = XMaterial.LIME_WOOL.parseMaterial();
	    Material disable = XMaterial.RED_WOOL.parseMaterial();
	    	
	    if (clicked.getType() == enable && clicked.getItemMeta().getDisplayName().equals(plugin.getConfigManager().getToggleGUIOnName())) {
	    	plugin.getDataManager().setAlerts(player.getUniqueId(), true);
	    	player.sendMessage(plugin.getConfigManager().getMessageToggleEnable());
	    }
	      
	    else if (clicked.getType() == disable && clicked.getItemMeta().getDisplayName().equals(plugin.getConfigManager().getToggleGUIOffName())) {
	    	plugin.getDataManager().setAlerts(player.getUniqueId(), false);
	    	player.sendMessage(plugin.getConfigManager().getMessageToggleDisable());
	    }
	    
	    event.setCancelled(true);
	    player.closeInventory();
	    
	  }
  }
