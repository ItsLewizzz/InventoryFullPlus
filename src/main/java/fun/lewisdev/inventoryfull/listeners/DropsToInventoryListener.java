package fun.lewisdev.inventoryfull.listeners;

import fun.lewisdev.inventoryfull.EventProcessor;
import fun.lewisdev.inventoryfull.InventoryFullPlusPlugin;
import me.clip.autosell.events.DropsToInventoryEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

// AutoSell Hook
public class DropsToInventoryListener implements Listener {

    private final EventProcessor eventProcessor;

    public DropsToInventoryListener(InventoryFullPlusPlugin plugin) {
        this.eventProcessor = plugin.getEventProcessor();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDropsToInv(DropsToInventoryEvent event) {
        final boolean success = eventProcessor.process(event.getPlayer(), new ArrayList<>(event.getBlock().getDrops()));

        if (success && eventProcessor.isPreventBlockBreak()) {
            event.setCancelled(true);
        }
    }

}
