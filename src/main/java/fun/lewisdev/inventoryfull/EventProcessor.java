package fun.lewisdev.inventoryfull;

import fun.lewisdev.inventoryfull.actions.ActionManager;
import fun.lewisdev.inventoryfull.events.InventoryFullEvent;
import fun.lewisdev.inventoryfull.hologram.HologramManager;
import fun.lewisdev.inventoryfull.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventProcessor {

    private final InventoryFullPlusPlugin plugin;
    private final PlayerManager playerManager;
    private final ActionManager actionManager;
    private final HologramManager hologramManager;

    private List<String> disabledWorlds, disabledGamemodes, actions;
    private boolean preventBlockBreak;
    private int cooldownTime;

    public EventProcessor(InventoryFullPlusPlugin plugin) {
        this.plugin = plugin;
        this.playerManager = plugin.getPlayerManager();
        this.actionManager = plugin.getActionManager();
        this.hologramManager = plugin.getHologramManager();
    }

    public void loadConfigValues() {
        FileConfiguration config = plugin.getConfig();
        disabledWorlds = config.getStringList("disabled_worlds");
        disabledGamemodes = config.getStringList("disabled_gamemodes");
        actions = config.getStringList("actions");
        preventBlockBreak = config.getBoolean("prevent_block_breaking");
        cooldownTime = config.getInt("cooldown_time");
    }

    public boolean process(Player player, List<ItemStack> drops) {
        if (disabledWorlds.contains(player.getWorld().getName()) || disabledGamemodes.contains(player.getGameMode().toString())) return false;
        final UUID uuid = player.getUniqueId();
        if (!playerManager.isPlayerNotifications(uuid) || !plugin.getCooldownManager().tryCooldown(uuid)) return false;
        if (player.getInventory().firstEmpty() >= 0) return false;

        if(drops.isEmpty()) return false;

        List<ItemStack> items = new ArrayList<>();
        drops.forEach(drop -> {
            for(ItemStack item : player.getInventory().getContents()) {
                if(item != null && item.getType().equals(drop.getType()) && item.getAmount() + drop.getAmount() <= item.getMaxStackSize()) return;
            }
            items.add(drop);
        });

        if(items.isEmpty()) return false;

        InventoryFullEvent event = new InventoryFullEvent(player, items);
        Bukkit.getPluginManager().callEvent(event);
        if(event.isCancelled()) return false;

        actionManager.executeActions(player, actions);
        hologramManager.displayHologram(player);

        plugin.getCooldownManager().setCooldown(uuid, cooldownTime + 1);
        return true;
    }

    public boolean isPreventBlockBreak() {
        return preventBlockBreak;
    }
}
