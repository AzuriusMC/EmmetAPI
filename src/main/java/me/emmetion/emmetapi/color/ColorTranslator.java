package me.emmetion.emmetapi.color;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ColorTranslator {
    public static String getColor(String text){
        return ChatColor.translateAlternateColorCodes('&',text);
    }

    public static List<String> getColor(List<String> list){
        return list.stream().map(ColorTranslator::getColor).collect(Collectors.toList());
    }
}
