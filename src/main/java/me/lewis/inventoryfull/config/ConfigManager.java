package me.lewis.inventoryfull.config;

import java.util.List;

import me.lewis.inventoryfull.utils.ChatUtils;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private FileConfiguration config;

    // Config values
    private boolean updateCheck;
    private Integer cooldownTime;
    private boolean stopBlockBreak;
    private List<String> disabledGamemodes;
    private List<String> disabledWorlds;
    private boolean toggleGUI;
    private String toggleGUIName;
    private String toggleGUIOnName;
    private String toggleGUIOffName;

    private boolean titleEnabled;
    private String mainTitle;
    private String subTitle;
    private Integer titleFadeIn;
    private Integer titleStay;
    private Integer titleFadeOut;
    private boolean soundEnabled;
    private String sound;
    private boolean chatEnabled;
    private List<String> chatMessage;
    private boolean actionBarEnabled;
    private String actionBarMessage;
    private boolean hologramEnabled;
    private List<String> hologramMessage;

    private String messagePrefix;
    private String messageReload;
    private String messageNoPermission;
    private String messageInvalidArguments;
    private String messageToggleEnable;
    private String messageToggleDisable;

    public ConfigManager(FileConfiguration config) {
        this.config = config;
        load();
    }

    private void load() {
        updateCheck = config.getBoolean("update-check");
        cooldownTime = config.getInt("cooldown-time");
        stopBlockBreak = config.getBoolean("stop-block-breaking-when-full");
        disabledGamemodes = config.getStringList("gamemode-blacklist");
        disabledWorlds = config.getStringList("disabled-worlds");
        toggleGUI = config.getBoolean("toggle_gui.enabled");
        toggleGUIName = ChatUtils.color(config.getString("toggle_gui.title"));
        toggleGUIOnName = ChatUtils.color(config.getString("toggle_gui.toggle_on.name"));
        toggleGUIOffName = ChatUtils.color(config.getString("toggle_gui.toggle_off.name"));

        titleEnabled = config.getBoolean("title.enabled");
        mainTitle = ChatUtils.color(config.getString("title.main-title"));
        subTitle = ChatUtils.color(config.getString("title.sub-title"));
        titleFadeIn = config.getInt("title.fade-in");
        titleStay = config.getInt("title.stay");
        titleFadeOut = config.getInt("title.fade-out");
        soundEnabled = config.getBoolean("sound.enabled");
        sound = config.getString("sound.sound");
        chatEnabled = config.getBoolean("chat-message.enabled");
        chatMessage = config.getStringList("chat-message.message");
        actionBarEnabled = config.getBoolean("actionbar.enabled");
        actionBarMessage = ChatUtils.color(config.getString("actionbar.message"));
        hologramEnabled = config.getBoolean("hologram.enabled");
        hologramMessage = config.getStringList("hologram.message");

        messagePrefix = ChatUtils.color(config.getString("messages.PREFIX"));
        messageReload = ChatUtils.color(config.getString("messages.RELOAD_SUCCESS").replace("%prefix%", messagePrefix));
        messageNoPermission = ChatUtils.color(config.getString("messages.NO_PERMISSION").replace("%prefix%", messagePrefix));
        messageInvalidArguments = ChatUtils.color(config.getString("messages.INVALID_ARGUMENTS").replace("%prefix%", messagePrefix));
        messageToggleEnable = ChatUtils.color(config.getString("messages.TOGGLE_ENABLE").replace("%prefix%", messagePrefix));
        messageToggleDisable = ChatUtils.color(config.getString("messages.TOGGLE_DISABLE").replace("%prefix%", messagePrefix));
    }

    public boolean isUpdateCheck() {
        return updateCheck;
    }

    public Integer getCooldownTime() {
        return cooldownTime;
    }

    public boolean isStopBlockBreak() {
        return stopBlockBreak;
    }

    public List<String> getDisabledGamemodes() {
        return disabledGamemodes;
    }

    public List<String> getDisabledWorlds() {
        return disabledWorlds;
    }

    public boolean isToggleGUI() {
        return toggleGUI;
    }

    public String getToggleGUIName() {
        return toggleGUIName;
    }

    public String getToggleGUIOnName() {
        return toggleGUIOnName;
    }

    public String getToggleGUIOffName() {
        return toggleGUIOffName;
    }

    public boolean isTitleEnabled() {
        return titleEnabled;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Integer getTitleFadeIn() {
        return titleFadeIn;
    }

    public Integer getTitleStay() {
        return titleStay;
    }

    public Integer getTitleFadeOut() {
        return titleFadeOut;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public String getSound() {
        return sound;
    }

    public boolean isChatEnabled() {
        return chatEnabled;
    }

    public List<String> getChatMessage() {
        return chatMessage;
    }

    public boolean isActionBarEnabled() {
        return actionBarEnabled;
    }

    public String getActionBarMessage() {
        return actionBarMessage;
    }

    public boolean isHologramEnabled() {
        return hologramEnabled;
    }

    public List<String> getHologramMessage() {
        return hologramMessage;
    }

    public String getMessagePrefix() {
        return messagePrefix;
    }

    public String getMessageReload() {
        return messageReload;
    }

    public String getMessageNoPermission() {
        return messageNoPermission;
    }

    public String getMessageInvalidArguments() {
        return messageInvalidArguments;
    }

    public String getMessageToggleEnable() {
        return messageToggleEnable;
    }

    public String getMessageToggleDisable() {
        return messageToggleDisable;
    }

}
