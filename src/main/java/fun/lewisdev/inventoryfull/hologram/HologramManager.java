package fun.lewisdev.inventoryfull.hologram;

import fun.lewisdev.inventoryfull.hologram.impl.CMIHologram;
import fun.lewisdev.inventoryfull.hologram.impl.HolographicDisplaysHologram;
import fun.lewisdev.inventoryfull.utils.TextUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class HologramManager {

    private final JavaPlugin javaPlugin;
    private HologramHandler hologramHandler;
    private List<String> hologramLines;
    private int displayTime;

    public HologramManager(JavaPlugin plugin) {
        this.javaPlugin = plugin;
    }

    public void onEnable() {
        hologramHandler = null;

        FileConfiguration config = javaPlugin.getConfig();
        if (!config.getBoolean("hologram.enabled")) return;
        hologramLines = new ArrayList<>();
        config.getStringList("hologram.lines").forEach(line -> hologramLines.add(TextUtil.color(line)));

        displayTime = config.getInt("hologram.display_time") * 20;

        final List<HologramHandler> handlerList = new ArrayList<>();
        handlerList.add(new HolographicDisplaysHologram());
        handlerList.add(new CMIHologram());

        final PluginManager pluginManager = javaPlugin.getServer().getPluginManager();
        for (HologramHandler handler : handlerList) {
            if (pluginManager.getPlugin(handler.getPluginName()) != null) {
                handler.onEnable();
                hologramHandler = handler;
                javaPlugin.getLogger().info("Hooked into '" + handler.getPluginName() + "' for Holograms");
                break;
            }
        }
    }

    public void displayHologram(final Player player) {
        if(hologramHandler != null) {
            hologramHandler.displayHologram(javaPlugin, player, hologramLines, displayTime);
        }
    }
}
