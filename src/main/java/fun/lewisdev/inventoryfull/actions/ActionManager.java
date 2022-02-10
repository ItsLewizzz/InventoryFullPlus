package fun.lewisdev.inventoryfull.actions;

import fun.lewisdev.inventoryfull.actions.impl.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ActionManager {

    private final JavaPlugin javaPlugin;
    private final Map<String, Action> actions;

    public ActionManager(final JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        actions = new HashMap<>();
    }

    public void loadActions() {
        registerAction(
                new MessageAction(),
                new BroadcastMessageAction(),
                new CommandAction(),
                new ConsoleCommandAction(),
                new SoundAction(),
                new ActionbarAction(),
                new TitleAction()
        );
    }

    public void registerAction(final Action... actions) {
        Arrays.asList(actions).forEach(action -> this.actions.put(action.getIdentifier(), action));
    }

    public void executeActions(final Player player, final List<String> items) {
        items.forEach(item -> {

            String actionName = StringUtils.substringBetween(item, "[", "]");
            Action action = actionName == null ? null : actions.get(actionName.toUpperCase());

            if (action != null) {
                item = item.contains(" ") ? item.split(" ", 2)[1] : "";
                item = item.replace("{PLAYER}", player.getName());

                action.execute(player, item);
            } else {
                javaPlugin.getLogger().warning("There was a problem attempting to process action: '" + item + "'");
            }
        });
    }
}
