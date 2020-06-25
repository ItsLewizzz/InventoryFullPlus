package fun.lewisdev.inventoryfull.hook;

import fun.lewisdev.inventoryfull.InventoryFullPlus;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

public class HooksManager {

    private boolean holographicDisplays = false;
    private boolean CMI = false;

    private Map<String, PluginHook> hooks;

    public HooksManager() {
        hooks = new HashMap<>();
    }

    public void reloadHooks(InventoryFullPlus plugin) {
        PluginManager pluginManager = Bukkit.getPluginManager();

        if(pluginManager.isPluginEnabled("AutoSell")) {
            hooks.put("AUTOSELL", new AutoSellHook());
            plugin.getLogger().info("Hooked into AutoSell!");
        }

        if(pluginManager.isPluginEnabled("HolographicDisplays")) {
            holographicDisplays = true;
            plugin.getLogger().info("Hooked into HolographicDisplays!");
        }

        if(Bukkit.getPluginManager().isPluginEnabled("CMI")) {
            CMI = true;
            plugin.getLogger().info("Hooked into CMI holograms!");
        }

        hooks.values().forEach(pluginHook -> pluginHook.onEnable(plugin));
    }

    public boolean isHookEnabled(String identifier) {
        return hooks.containsKey(identifier);
    }

    public Object getPluginHook(String identifier) {
        if(!isHookEnabled(identifier)) return null;
        return hooks.get(identifier).getClass();
    }

    public boolean isHolographicDisplays() {
        return holographicDisplays;
    }

    public boolean isCMI() {
        return CMI;
    }
}
