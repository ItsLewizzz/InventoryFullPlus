package me.lewis.inventoryfull;

import java.util.logging.Level;

import org.bstats.bukkit.Metrics;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import me.lewis.inventoryfull.alert.AlertManager;
import me.lewis.inventoryfull.alert.CooldownManager;
import me.lewis.inventoryfull.command.InventoryFullCommand;
import me.lewis.inventoryfull.config.ConfigManager;
import me.lewis.inventoryfull.config.DataManager;
import me.lewis.inventoryfull.hook.HooksManager;
import me.lewis.inventoryfull.listeners.BreakEvent;
import me.lewis.inventoryfull.listeners.JoinEvent;
import me.lewis.inventoryfull.update.UpdateManager;
import me.lewis.inventoryfull.utils.InventoryUtils;

public class InventoryFullPlus extends JavaPlugin {

    private UpdateManager updateManager;
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

        if (!getUpdateManager().isLatestVersion()) {
            getLogger().log(Level.INFO, "");
            getLogger().log(Level.WARNING, "You do not have the most updated version of InventoryFull+ (" + getUpdateManager().getNewVersion() + ")");
            getLogger().log(Level.WARNING, "https://www.spigotmc.org/resources/inventoryfull.31544/");
        } else {
            getLogger().log(Level.INFO, "");
            getLogger().log(Level.INFO, "You are running the latest version of InventoryFull+ :)");
        }

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
        updateManager = new UpdateManager(this);
        updateManager.checkForUpdate();
        hookManager = new HooksManager();
        hookManager.loadHooks();
        cooldownManager = new CooldownManager();
        dataManager = new DataManager(this);
        configManager = new ConfigManager(getConfig());
        inventoryManager = new InventoryUtils(this);

        new JoinEvent(this);
        new BreakEvent(this);
    }

    private void loadCommands() {
        getCommand("inventoryfullplus").setExecutor(new InventoryFullCommand(this));
    }

    public UpdateManager getUpdateManager() {
        return updateManager;
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
