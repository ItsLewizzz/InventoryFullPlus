package fun.lewisdev.inventoryfull.utils;

import fun.lewisdev.inventoryfull.utils.color.IridiumColorAPI;

import java.util.List;

public class TextUtil {

    public static String color(String string) {
        return IridiumColorAPI.process(string);
    }

    public static String fromList(List<?> list) {
        if (list == null || list.isEmpty()) return null;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i != 0 && list.get(i).toString().equals(""))
                builder.append("&r\n ");
            else
                builder.append(list.get(i).toString());
            builder.append(i + 1 != list.size() ? "\n" : "");
        }
        return builder.toString();
    }
}
