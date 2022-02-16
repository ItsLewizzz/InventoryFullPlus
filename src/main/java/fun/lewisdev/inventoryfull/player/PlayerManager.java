package fun.lewisdev.inventoryfull.player;

import fun.lewisdev.inventoryfull.InventoryFullPlusPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

public class PlayerManager {

    private final InventoryFullPlusPlugin plugin;
    private final Map<UUID, Boolean> players;

    public PlayerManager(InventoryFullPlusPlugin plugin) {
        this.plugin = plugin;
        this.players = new HashMap<>();
    }

    public void onEnable() {
        File directory = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "playerdata");
        if(!directory.exists()) directory.mkdir();

        Stream.of(
                new Listener() {
                    @EventHandler(priority = EventPriority.MONITOR)
                    public void onPlayerJoin(final PlayerJoinEvent event) {
                        loadPlayerData(event.getPlayer().getUniqueId());
                    }

                }, new Listener() {
                    @EventHandler(priority = EventPriority.MONITOR)
                    public void onPlayerQuit(final PlayerQuitEvent event) {
                        final UUID uuid = event.getPlayer().getUniqueId();
                        savePlayerStorage(uuid);
                        players.remove(uuid);
                        plugin.getCooldownManager().removeCooldowns(uuid);
                    }
                }).forEach(listener -> plugin.getServer().getPluginManager().registerEvents(listener, plugin));

        Bukkit.getOnlinePlayers().forEach(player -> loadPlayerData(player.getUniqueId()));
    }

    public void onDisable() {
        players.keySet().forEach(this::savePlayerStorage);
        players.clear();
    }

    public boolean isPlayerNotifications(UUID uuid) {
        return players.getOrDefault(uuid, true);
    }

    public void setPlayerNotifications(UUID uuid, boolean value) {
        players.put(uuid, value);
    }

    private void loadPlayerData(UUID uuid) {
        File dataFile = new File(plugin.getDataFolder() + File.separator + "playerdata", uuid.toString() + ".yml");
        if (dataFile.exists()) {
            FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(dataFile);
            players.put(uuid, playerDataConfig.getBoolean("notifications", true));
        }else {
            players.put(uuid, true);
        }
    }

    private void savePlayerStorage(UUID uuid) {
        if(!players.containsKey(uuid)) return;

        File playerFile = new File(plugin.getDataFolder() + File.separator + "playerdata", uuid.toString() + ".yml");
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean notifications = isPlayerNotifications(uuid);
        FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        config.set("notifications", notifications);

        try {
            config.save(playerFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
