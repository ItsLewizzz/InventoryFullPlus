package fun.lewisdev.inventoryfull.hologram;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public interface HologramHandler {

    String getPluginName();

    void onEnable();

    void displayHologram(JavaPlugin javaPlugin, Player player, List<String> lines, long displayTime);

}
