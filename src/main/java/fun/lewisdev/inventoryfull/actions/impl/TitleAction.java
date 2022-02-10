package fun.lewisdev.inventoryfull.actions.impl;

import fun.lewisdev.inventoryfull.actions.Action;
import fun.lewisdev.inventoryfull.utils.TextUtil;
import fun.lewisdev.inventoryfull.utils.reflection.ReflectionUtils;
import fun.lewisdev.inventoryfull.utils.reflection.Titles;
import org.bukkit.entity.Player;

public class TitleAction implements Action {

    @Override
    public String getIdentifier() {
        return "TITLE";
    }

    @Override
    public void execute(Player player, String data) {
        final String[] args = data.split(";");
        final String mainTitle = TextUtil.color(args[0]);
        final String subTitle = TextUtil.color(args[1]);

        int fadeIn;
        int stay;
        int fadeOut;
        try {
            fadeIn = Integer.parseInt(args[2]);
            stay = Integer.parseInt(args[3]);
            fadeOut = Integer.parseInt(args[4]);
        } catch (NumberFormatException ex) {
            fadeIn = 1;
            stay = 3;
            fadeOut = 1;
        }

        Titles.sendTitle(player, fadeIn * 20, stay * 20, fadeOut * 20, mainTitle, subTitle);
    }
}
