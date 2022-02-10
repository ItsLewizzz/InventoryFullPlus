package fun.lewisdev.inventoryfull.utils.color;

import com.google.common.collect.ImmutableMap;
import fun.lewisdev.inventoryfull.utils.color.patterns.GradientPattern;
import fun.lewisdev.inventoryfull.utils.color.patterns.Pattern;
import fun.lewisdev.inventoryfull.utils.color.patterns.RainbowPattern;
import fun.lewisdev.inventoryfull.utils.color.patterns.SolidPattern;
import fun.lewisdev.inventoryfull.utils.reflection.ReflectionUtils;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IridiumColorAPI {

    /**
     * Cached result if the server version is after the v1.16 RGB update.
     *
     * @since 1.0.0
     */
    private static final boolean SUPPORTS_RGB = ReflectionUtils.supports(16);

    private static final List<String> SPECIAL_COLORS = Arrays.asList("&l", "&n", "&o", "&k", "&m");

    /**
     * Cached result of all legacy colors.
     *
     * @since 1.0.0
     */
    private static final Map<Color, ChatColor> COLORS = ImmutableMap.<Color, ChatColor>builder()
            .put(new Color(0), ChatColor.getByChar('0'))
            .put(new Color(170), ChatColor.getByChar('1'))
            .put(new Color(43520), ChatColor.getByChar('2'))
            .put(new Color(43690), ChatColor.getByChar('3'))
            .put(new Color(11141120), ChatColor.getByChar('4'))
            .put(new Color(11141290), ChatColor.getByChar('5'))
            .put(new Color(16755200), ChatColor.getByChar('6'))
            .put(new Color(11184810), ChatColor.getByChar('7'))
            .put(new Color(5592405), ChatColor.getByChar('8'))
            .put(new Color(5592575), ChatColor.getByChar('9'))
            .put(new Color(5635925), ChatColor.getByChar('a'))
            .put(new Color(5636095), ChatColor.getByChar('b'))
            .put(new Color(16733525), ChatColor.getByChar('c'))
            .put(new Color(16733695), ChatColor.getByChar('d'))
            .put(new Color(16777045), ChatColor.getByChar('e'))
            .put(new Color(16777215), ChatColor.getByChar('f')).build();

    /**
     * Cached result of patterns.
     *
     * @since 1.0.2
     */
    private static final List<Pattern> PATTERNS = Arrays.asList(new GradientPattern(), new SolidPattern(), new RainbowPattern());

    /**
     * Processes a string to add color to it.
     * Thanks to Distressing for helping with the regex <3
     *
     * @param string The string we want to process
     * @since 1.0.0
     */
    public static String process(String string) {
        for (Pattern pattern : PATTERNS) {
            string = pattern.process(string);
        }

        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Processes multiple strings in a list.
     *
     * @param strings The list of the strings we are processing
     * @return The list of processed strings
     * @since 1.0.3
     */
    public static List<String> process(List<String> strings) {
        return strings.stream().map(IridiumColorAPI::process).collect(Collectors.toList());
    }

    /**
     * Colors a String.
     *
     * @param string The string we want to color
     * @param color  The color we want to set it to
     * @since 1.0.0
     */
    public static String color(String string, Color color) {
        return (SUPPORTS_RGB ? ChatColor.of(color) : getClosestColor(color)) + string;
    }

    /**
     * Colors a String with a gradiant.
     *
     * @param string The string we want to color
     * @param start  The starting gradiant
     * @param end    The ending gradiant
     * @since 1.0.0
     */
    public static String color(String string, Color start, Color end) {
        StringBuilder specialColors = new StringBuilder();
        for (String color : SPECIAL_COLORS) {
            if (string.contains(color)) {
                specialColors.append(color);
                string = string.replace(color, "");
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        ChatColor[] colors = createGradient(start, end, string.length());
        String[] characters = string.split("");
        for (int i = 0; i < string.length(); i++) {
            stringBuilder.append(colors[i]).append(specialColors).append(characters[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Colors a String with rainbow colors.
     *
     * @param string     The string which should have rainbow colors
     * @param saturation The saturation of the rainbow colors
     * @since 1.0.3
     */
    public static String rainbow(String string, float saturation) {
        StringBuilder specialColors = new StringBuilder();
        for (String color : SPECIAL_COLORS) {
            if (string.contains(color)) {
                specialColors.append(color);
                string = string.replace(color, "");
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        ChatColor[] colors = createRainbow(string.length(), saturation);
        String[] characters = string.split("");
        for (int i = 0; i < string.length(); i++) {
            stringBuilder.append(colors[i]).append(specialColors).append(characters[i]);
        }
        return stringBuilder.toString();
    }

    /**
     * Gets a color from hex code.
     *
     * @param string The hex code of the color
     * @since 1.0.0
     */
    public static ChatColor getColor(String string) {
        return SUPPORTS_RGB ? ChatColor.of(new Color(Integer.parseInt(string, 16))) : getClosestColor(new Color(Integer.parseInt(string, 16)));
    }

    /**
     * Removes all color codes from the provided String, including IridiumColorAPI patterns.
     *
     * @param string    The String which should be stripped
     * @return          The stripped string without color codes
     * @since 1.0.5
     */
    public static String stripColorFormatting(String string) {
        return string.replaceAll("<#[0-9A-F]{6}>|[&§][a-f0-9lnokm]|<[/]?[A-Z]{5,8}(:[0-9A-F]{6})?[0-9]*>", "");
    }

    /**
     * Returns a rainbow array of chat colors.
     *
     * @param step       How many colors we return
     * @param saturation The saturation of the rainbow
     * @return The array of colors
     * @since 1.0.3
     */
    private static ChatColor[] createRainbow(int step, float saturation) {
        ChatColor[] colors = new ChatColor[step];
        double colorStep = (1.00 / step);
        for (int i = 0; i < step; i++) {
            Color color = Color.getHSBColor((float) (colorStep * i), saturation, saturation);
            if (SUPPORTS_RGB) {
                colors[i] = ChatColor.of(color);
            } else {
                colors[i] = getClosestColor(color);
            }
        }
        return colors;
    }

    /**
     * Returns a gradient array of chat colors.
     *
     * @param start The starting color.
     * @param end   The ending color.
     * @param step  How many colors we return.
     * @author TheViperShow
     * @since 1.0.0
     */
    private static ChatColor[] createGradient(Color start, Color end, int step) {
        ChatColor[] colors = new ChatColor[step];
        int stepR = Math.abs(start.getRed() - end.getRed()) / (step - 1);
        int stepG = Math.abs(start.getGreen() - end.getGreen()) / (step - 1);
        int stepB = Math.abs(start.getBlue() - end.getBlue()) / (step - 1);
        int[] direction = new int[]{
                start.getRed() < end.getRed() ? +1 : -1,
                start.getGreen() < end.getGreen() ? +1 : -1,
                start.getBlue() < end.getBlue() ? +1 : -1
        };

        for (int i = 0; i < step; i++) {
            Color color = new Color(start.getRed() + ((stepR * i) * direction[0]), start.getGreen() + ((stepG * i) * direction[1]), start.getBlue() + ((stepB * i) * direction[2]));
            if (SUPPORTS_RGB) {
                colors[i] = ChatColor.of(color);
            } else {
                colors[i] = getClosestColor(color);
            }
        }
        return colors;
    }


    /**
     * Returns the closest legacy color from an rgb color
     *
     * @param color The color we want to transform
     * @since 1.0.0
     */
    private static ChatColor getClosestColor(Color color) {
        Color nearestColor = null;
        double nearestDistance = Integer.MAX_VALUE;

        for (Color constantColor : COLORS.keySet()) {
            double distance = Math.pow(color.getRed() - constantColor.getRed(), 2) + Math.pow(color.getGreen() - constantColor.getGreen(), 2) + Math.pow(color.getBlue() - constantColor.getBlue(), 2);
            if (nearestDistance > distance) {
                nearestColor = constantColor;
                nearestDistance = distance;
            }
        }
        return COLORS.get(nearestColor);
    }

}