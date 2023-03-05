package net.blueva.core.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessagesUtil {
    public static @NotNull String format (Player player, String text) {
        String textfinal = formatPlaceholders(player, text);
        return formatColors(textfinal);
    }

    public static String formatPlaceholders(Player player, String text) {
        if(player != null) {
            text = text
                    .replace("{player_name}", player.getName())
                    .replace("{player_displayname}", player.getDisplayName())
                    .replace("{player_ping}", String.valueOf(player.getPing()))
                    .replace("{player_exp}", String.valueOf(player.getExp()))
                    .replace("{player_health}", String.valueOf(player.getHealth()))
                    .replace("{player_level}", String.valueOf(player.getLevel()))
                    .replace("{player_location_x}", String.valueOf(Math.round(player.getLocation().getX())))
                    .replace("{player_location_y}", String.valueOf(Math.round(player.getLocation().getY())))
                    .replace("{player_location_z}", String.valueOf(Math.round(player.getLocation().getZ())))
                    .replace("{server_name}", Bukkit.getServer().getName())
                    .replace("{server_max_players}", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                    .replace("{server_version}", Bukkit.getServer().getVersion());

            if(Main.placeholderapi) {
                return PlaceholderAPI.setPlaceholders(player, text);
            }
        }

        return text;
    }

    public static @NotNull String formatColors(String text) {
        if (Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18") || Bukkit.getVersion().contains("1.19")) {
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher match = pattern.matcher(text);

            while (match.find()) {
                String color = text.substring(match.start(), match.end());
                text = text.replace(color, ChatColor.of(color) + "");

                match = pattern.matcher(text);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
