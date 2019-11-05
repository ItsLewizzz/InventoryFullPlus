package me.lewis.inventoryfull.alert;

import java.util.List;
import java.util.logging.Level;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import io.github.theluca98.textapi.ActionBar;
import io.github.theluca98.textapi.Title;
import me.lewis.inventoryfull.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


import me.lewis.inventoryfull.InventoryFullPlus;

public class AlertManager {

    private final InventoryFullPlus plugin;

    public AlertManager(InventoryFullPlus plugin) {
        this.plugin = plugin;
    }

    public void sendAlerts(Player player) {

        if (!plugin.getCooldownManager().tryCooldown(player.getUniqueId(), plugin.getConfigManager().getCooldownTime())) return;

        if (plugin.getConfigManager().isTitleEnabled()) sendTitle(player);

        if (plugin.getConfigManager().isActionBarEnabled()) sendActionBar(player);

        if (plugin.getConfigManager().isChatEnabled()) sendChatMessage(player);

        if (plugin.getConfigManager().isSoundEnabled()) sendSound(player);

        if (plugin.getConfigManager().isHologramEnabled()) sendHologram(player);
    }

    private void sendTitle(Player player) {
        String mainTitle = plugin.getConfigManager().getMainTitle();
        String subTitle = plugin.getConfigManager().getSubTitle();

        Integer fadeIn = plugin.getConfigManager().getTitleFadeIn();
        Integer stay = plugin.getConfigManager().getTitleStay();
        Integer fadeOut = plugin.getConfigManager().getTitleFadeOut();

        Title title = new Title(mainTitle, subTitle, fadeIn * 20, stay * 20, fadeOut * 20);
        title.send(player);
    }

    private void sendActionBar(Player player) {
        ActionBar action = new ActionBar(plugin.getConfigManager().getActionBarMessage());
        action.send(player);
    }

    private void sendChatMessage(Player player) {
        for (String s : plugin.getConfigManager().getChatMessage()) {
            player.sendMessage(ChatUtils.color(s));
        }
    }

    private void sendSound(Player player) {
        try {
            player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfigManager().getSound()), 50.0F, 50.0F);
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "InventoryFull+ : Your sound value in the configuration is not valid for this server version.");
        }
    }

    private void sendHologram(Player player) {

        if (plugin.getHookManager().isHolographicDisplays()) {

            Vector vector = player.getLocation().getDirection().multiply(1);
            Location loc = player.getEyeLocation().add(vector);

            Hologram holo = HologramsAPI.createHologram(plugin, loc);

            for (String s : plugin.getConfigManager().getHologramMessage()) {
                holo.appendTextLine(ChatUtils.color(s));
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    holo.delete();
                }
            }, 60L);
        }

        if (plugin.getHookManager().isCMI()) {

            Vector vector = player.getLocation().getDirection();
            Location loc = player.getEyeLocation().add(vector);
            loc.setY(loc.getY() - 0.5);

            CMIHologram holo = new CMIHologram("invfull-" + player.getUniqueId(), loc);

            List<String> message = plugin.getConfigManager().getHologramMessage();

            holo.setLines(message);
            CMI.getInstance().getHologramManager().addHologram(holo);
            holo.update();

            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    CMI.getInstance().getHologramManager().removeHolo(holo);
                    holo.hide(player);
                }
            }, 60L);

        }
    }

}
