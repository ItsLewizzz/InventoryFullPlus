package fun.lewisdev.inventoryfull.hologram.impl;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import fun.lewisdev.inventoryfull.hologram.HologramHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.List;

public class HolographicDisplaysHologram implements HologramHandler {

    @Override
    public String getPluginName() {
        return "HolographicDisplays";
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void displayHologram(JavaPlugin javaPlugin, Player player, List<String> lines, long displayTime) {
        final Vector vector = player.getLocation().getDirection().multiply(1);
        final Location loc = player.getEyeLocation().add(vector);

        final Hologram holo = HologramsAPI.createHologram(javaPlugin, loc);

        lines.forEach(holo::appendTextLine);
        Bukkit.getScheduler().runTaskLater(javaPlugin, holo::delete, displayTime);
    }
}
