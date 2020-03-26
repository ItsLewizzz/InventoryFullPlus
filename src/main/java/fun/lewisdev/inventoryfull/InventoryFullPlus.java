package fun.lewisdev.inventoryfull;

import java.util.logging.Level;

import fun.lewisdev.inventoryfull.update.UpdateChecker;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import fun.lewisdev.inventoryfull.alert.AlertManager;
import fun.lewisdev.inventoryfull.alert.CooldownManager;
import fun.lewisdev.inventoryfull.command.InventoryFullCommand;
import fun.lewisdev.inventoryfull.config.ConfigManager;
import fun.lewisdev.inventoryfull.config.DataManager;
import fun.lewisdev.inventoryfull.hook.HooksManager;
import fun.lewisdev.inventoryfull.listeners.BlockBreakListener;
import fun.lewisdev.inventoryfull.utils.InventoryUtils;

public class InventoryFullPlus extends JavaPlugin {

    private AlertManager alertManager;
    private CooldownManager cooldownManager;
    private DataManager dataManager;
    private ConfigManager configManager;
    private HooksManager hookManager;
    private InventoryUtils inventoryManager;

    public void onEnable() {
        saveDefaultConfig();

        new Metrics(this);
        loadManagers();
        loadCommands();

        PluginDescriptionFile pdfFile = getDescription();
        getLogger().log(Level.INFO, "--------------------------------------");
        getLogger().log(Level.INFO, "InventoryFull+");
        getLogger().log(Level.INFO, "Author: ItsLewizzz");
        getLogger().log(Level.INFO, "Plugin Version: " + pdfFile.getVersion());

        if (getHookManager().isHolographicDisplays()) {
            getLogger().log(Level.INFO, "HOOK: Hooked into HolographicDisplays");
        }

        if (getHookManager().isCMI()) {
            getLogger().log(Level.INFO, "HOOK: Hooked into CMI");
        }

        alertManager = new AlertManager(this);

        new UpdateChecker(this).checkForUpdate();
        getLogger().log(Level.INFO, "--------------------------------------");
    }

    public void onDisable() {
        dataManager.save();
    }

    public void reload() {
        if(dataManager != null) dataManager.save();
        HandlerList.unregisterAll(this);
        reloadConfig();
        loadManagers();
        new InventoryFullCommand(this);
    }

    private void loadManagers() {
        hookManager = new HooksManager();
        hookManager.loadHooks();
        cooldownManager = new CooldownManager();
        dataManager = new DataManager(this);
        configManager = new ConfigManager(getConfig());
        inventoryManager = new InventoryUtils(this);
        new BlockBreakListener(this);
    }

    private void loadCommands() {
        getCommand("inventoryfullplus").setExecutor(new InventoryFullCommand(this));
    }

    public AlertManager getAlertManager() {
        return alertManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public HooksManager getHookManager() {
        return hookManager;
    }

    public InventoryUtils getInventoryManager() {
        return inventoryManager;
    }
}
