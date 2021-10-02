package org.jachi.whirss.thenexus;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {

    public static String getColorMessage(String text, Player player) {
        if(Bukkit.getVersion().contains("1.17")) {
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            if(Main.getInstance().placeholderapi) {
                Matcher match = pattern.matcher(text);

                while(match.find()) {
                    String color = text.substring(match.start(),match.end());
                    text = text.replace(color, ChatColor.of(color)+"");

                    match = pattern.matcher(PlaceholderAPI.setPlaceholders(player, text));
                }
            } else {
                Matcher match = pattern.matcher(PlaceholderAPI.setPlaceholders(player, text));

                while(match.find()) {
                    String color = text.substring(match.start(),match.end());
                    text = text.replace(color, ChatColor.of(color)+"");

                    match = pattern.matcher(PlaceholderAPI.setPlaceholders(player, text));
                }
            }
        }

        if(Main.getInstance().placeholderapi) {
            return PlaceholderAPI.setPlaceholders(player, ChatColor.translateAlternateColorCodes('&', text));
        } else {
            return ChatColor.translateAlternateColorCodes('&', text);
        }
    }
}
