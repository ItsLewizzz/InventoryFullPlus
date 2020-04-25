package fun.lewisdev.inventoryfull.alert;

import java.util.List;
import java.util.logging.Level;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import fun.lewisdev.inventoryfull.InventoryFullPlus;
import fun.lewisdev.inventoryfull.utils.ChatUtils;
import fun.lewisdev.inventoryfull.utils.reflection.ActionBar;
import fun.lewisdev.inventoryfull.utils.reflection.Titles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AlertManager {

    private final InventoryFullPlus plugin;

    public AlertManager(InventoryFullPlus plugin) {
        this.plugin = plugin;
    }

    public void sendAlerts(Player player) {

        if (!plugin.getCooldownManager().tryCooldown(player.getUniqueId(), plugin.getConfigManager().getCooldownTime()))
            return;

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

        Titles.sendTitle(player, fadeIn * 20, stay * 20, fadeOut * 20, mainTitle, subTitle);
    }

    private void sendActionBar(Player player) {
        ActionBar.sendActionBar(player, plugin.getConfigManager().getActionBarMessage());
    }

    private void sendChatMessage(Player player) {
        for (String s : plugin.getConfigManager().getChatMessage()) {
            player.sendMessage(ChatUtils.color(s));
        }
    }

    private void sendSound(Player player) {
        try {
            player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfigManager().getSound()), (float) plugin.getConfigManager().getSoundVolume(), (float) plugin.getConfigManager().getSoundPitch());
        } catch (Exception ex) {
            plugin.getLogger().log(Level.WARNING, "Your sound value in the configuration is not valid for this server version.");
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
