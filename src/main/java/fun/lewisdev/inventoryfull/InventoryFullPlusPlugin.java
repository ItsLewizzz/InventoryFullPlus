package fun.lewisdev.inventoryfull;

import fun.lewisdev.inventoryfull.actions.ActionManager;
import fun.lewisdev.inventoryfull.config.Messages;
import fun.lewisdev.inventoryfull.hologram.HologramManager;
import fun.lewisdev.inventoryfull.listeners.DropsToInventoryListener;
import fun.lewisdev.inventoryfull.player.PlayerManager;
import fun.lewisdev.inventoryfull.utils.Metrics;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import fun.lewisdev.inventoryfull.cooldown.CooldownManager;
import fun.lewisdev.inventoryfull.command.InventoryFullCommand;
import fun.lewisdev.inventoryfull.listeners.BlockBreakListener;

public class InventoryFullPlusPlugin extends JavaPlugin {

    private CooldownManager cooldownManager;
    private ActionManager actionManager;
    private PlayerManager playerManager;
    private HologramManager hologramManager;
    private EventProcessor eventProcessor;

    public void onEnable() {
        saveDefaultConfig();
        Messages.setConfiguration(getConfig());

        (actionManager = new ActionManager(this)).loadActions();
        (playerManager = new PlayerManager(this)).onEnable();
        (hologramManager = new HologramManager(this)).onEnable();
        (eventProcessor = new EventProcessor(this)).loadConfigValues();
        cooldownManager = new CooldownManager();

        CommandManager commandManager = new CommandManager(this, true);
        commandManager.getMessageHandler().register("cmd.no.permission", Messages.NO_PERMISSION::send);
        commandManager.register(new InventoryFullCommand(this));

        new BlockBreakListener(this);

        if(getServer().getPluginManager().getPlugin("AutoSell") != null) {
            getLogger().info("Hooked into AutoSell");
            new DropsToInventoryListener(this);
        }

        new Metrics(this, 3163);
    }

    public void onDisable() {
        playerManager.onDisable();
    }

    public void onReload() {
        reloadConfig();
        Messages.setConfiguration(getConfig());

        hologramManager.onEnable();
        eventProcessor.loadConfigValues();
    }
    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public HologramManager getHologramManager() {
        return hologramManager;
    }

    public EventProcessor getEventProcessor() {
        return eventProcessor;
    }
}
