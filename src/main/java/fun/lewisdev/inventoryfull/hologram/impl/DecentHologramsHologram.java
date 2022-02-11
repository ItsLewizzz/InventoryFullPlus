package fun.lewisdev.inventoryfull.hologram.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import fun.lewisdev.inventoryfull.hologram.HologramHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

public class DecentHologramsHologram implements HologramHandler {

    @Override
    public String getPluginName() {
        return "DecentHolograms";
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void displayHologram(JavaPlugin javaPlugin, Player player, List<String> lines, long displayTime) {
        final Vector vector = player.getLocation().getDirection().multiply(1);
        final Location location = player.getEyeLocation().add(vector);

        final Hologram hologram = DHAPI.createHologram("invfull-" + UUID.randomUUID().toString(), location, lines);
        Bukkit.getScheduler().runTaskLater(javaPlugin, hologram::delete, displayTime);
    }
}
