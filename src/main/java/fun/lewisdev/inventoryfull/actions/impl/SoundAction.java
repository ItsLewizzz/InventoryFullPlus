package fun.lewisdev.inventoryfull.actions.impl;

import fun.lewisdev.inventoryfull.actions.Action;
import fun.lewisdev.inventoryfull.utils.universal.XSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SoundAction implements Action {

    @Override
    public String getIdentifier() {
        return "SOUND";
    }

    @Override
    public void execute(Player player, String data) {
        final Optional<XSound> xSound = XSound.matchXSound(data);
        if(xSound.isPresent()) {
            player.playSound(player.getLocation(), xSound.get().parseSound(), 1L, 1L);
        }else{
            Bukkit.getLogger().warning("[InventoryFull+ Action] Invalid sound name: " + data);
        }
    }
}
