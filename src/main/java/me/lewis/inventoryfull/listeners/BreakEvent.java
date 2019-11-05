package me.lewis.inventoryfull.listeners;

import me.lewis.inventoryfull.alert.AlertManager;
import me.lewis.inventoryfull.config.ConfigManager;
import me.lewis.inventoryfull.config.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.lewis.inventoryfull.InventoryFullPlus;
import me.lewis.inventoryfull.events.InventoryFullEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BreakEvent implements Listener {

    private final InventoryFullPlus plugin;

    public BreakEvent(InventoryFullPlus plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ConfigManager configManager = plugin.getConfigManager();
        DataManager dataManager = plugin.getDataManager();
        AlertManager alertManager = plugin.getAlertManager();

        if (configManager.getDisabledWorlds().contains(player.getWorld().getName())) return;
        if (configManager.getDisabledGamemodes().contains(player.getGameMode().toString())) return;
        if (!dataManager.hasAlerts(player.getUniqueId())) return;
        if (player.getInventory().firstEmpty() >= 1) return;

        List<ItemStack> blockDrops = new ArrayList<>(event.getBlock().getDrops());
        if(blockDrops.isEmpty()) return;

        List<ItemStack> items = new ArrayList<>();
        blockDrops.forEach(drop -> {
            for(ItemStack item : player.getInventory().getStorageContents()) {
                if(item != null && item.getType().equals(drop.getType()) && item.getAmount() + drop.getAmount() <= item.getMaxStackSize()) return;
            }
            items.add(drop);
        });

        if(items.isEmpty()) return;

        InventoryFullEvent e = new InventoryFullEvent(player, items);
        Bukkit.getPluginManager().callEvent(e);
        if (e.isCancelled()) return;

        alertManager.sendAlerts(player);

        if (configManager.isStopBlockBreak()) event.setCancelled(true);

    }
}
