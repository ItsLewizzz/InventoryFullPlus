package me.lewis.inventoryfull.hook;

import org.bukkit.Bukkit;

public class HooksManager {

    private boolean holographicDisplays = false;
    private boolean CMI = false;

    public void loadHooks() {
        if (Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) holographicDisplays = true;
        if (Bukkit.getPluginManager().isPluginEnabled("CMI")) CMI = true;
    }

    public boolean isHolographicDisplays() {
        return holographicDisplays;
    }

    public boolean isCMI() {
        return CMI;
    }
}
