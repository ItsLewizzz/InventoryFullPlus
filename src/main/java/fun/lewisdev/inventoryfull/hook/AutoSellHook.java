package fun.lewisdev.inventoryfull.hook;

import fun.lewisdev.inventoryfull.InventoryFullPlus;
import fun.lewisdev.inventoryfull.alert.AlertManager;
import fun.lewisdev.inventoryfull.config.ConfigManager;
import fun.lewisdev.inventoryfull.config.DataManager;
import fun.lewisdev.inventoryfull.events.InventoryFullEvent;
import me.clip.autosell.events.DropsToInventoryEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AutoSellHook implements PluginHook, Listener {

    private InventoryFullPlus plugin;

    @Override
    public void onEnable(InventoryFullPlus plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDropsToInv(DropsToInventoryEvent event) {

        Player player = event.getPlayer();

        ConfigManager configManager = plugin.getConfigManager();
        DataManager dataManager = plugin.getDataManager();
        AlertManager alertManager = plugin.getAlertManager();

        if (configManager.getDisabledWorlds().contains(player.getWorld().getName())) return;
        if (configManager.getDisabledGamemodes().contains(player.getGameMode().toString())) return;
        if (!dataManager.hasAlerts(player.getUniqueId())) return;
        if (player.getInventory().firstEmpty() >= 0) return;

        List<ItemStack> blockDrops = new ArrayList<>(event.getDrops());
        if(blockDrops.isEmpty()) return;

        List<ItemStack> items = new ArrayList<>();
        blockDrops.forEach(drop -> {
            for(ItemStack item : player.getInventory().getContents()) {
                if(item != null && item.getType().equals(drop.getType()) && item.getAmount() + drop.getAmount() <= item.getMaxStackSize()) return;
            }
            items.add(drop);
        });

        if(items.isEmpty()) return;

        InventoryFullEvent e = new InventoryFullEvent(player, items);
        Bukkit.getPluginManager().callEvent(e);
        if(e.isCancelled()) return;

        alertManager.sendAlerts(player);

        if (configManager.isStopBlockBreak()) event.setCancelled(true);
    }

}
