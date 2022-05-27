package org.saberdev.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> colorList(List<String> s) {
        List<String> string = new ArrayList<>();
        for (String result : s) {
            string.add(color(result));
        }
        return string;
    }

}
