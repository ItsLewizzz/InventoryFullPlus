package fun.lewisdev.inventoryfull.hologram.impl;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.HologramManager;
import fun.lewisdev.inventoryfull.hologram.HologramHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.List;

public class CMIHologram implements HologramHandler {

    private HologramManager hologramManager;

    @Override
    public String getPluginName() {
        return "CMI";
    }

    @Override
    public void onEnable() {
        hologramManager = CMI.getInstance().getHologramManager();
    }

    @Override
    public void displayHologram(JavaPlugin javaPlugin, Player player, List<String> lines, long displayTime) {
        final Vector vector = player.getLocation().getDirection();
        final Location location = player.getEyeLocation().add(vector);
        location.setY(location.getY() - 0.5);

        final com.Zrips.CMI.Modules.Holograms.CMIHologram holo = new com.Zrips.CMI.Modules.Holograms.CMIHologram("invfull-" + player.getUniqueId(), location);

        holo.setLines(lines);
        hologramManager.addHologram(holo);
        holo.update();

        Bukkit.getScheduler().runTaskLater(javaPlugin, () -> {
            hologramManager.removeHolo(holo);
            holo.hide(player);
        }, displayTime);
    }
}
