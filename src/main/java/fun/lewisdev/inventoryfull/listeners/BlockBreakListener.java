package fun.lewisdev.inventoryfull.listeners;

import fun.lewisdev.inventoryfull.EventProcessor;
import fun.lewisdev.inventoryfull.InventoryFullPlusPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

public class BlockBreakListener implements Listener {

    private final EventProcessor eventProcessor;

    public BlockBreakListener(InventoryFullPlusPlugin plugin) {
        this.eventProcessor = plugin.getEventProcessor();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        final boolean success = eventProcessor.process(event.getPlayer(), new ArrayList<>(event.getBlock().getDrops()));

        if (success && eventProcessor.isPreventBlockBreak()) {
            event.setCancelled(true);
        }

    }
}
