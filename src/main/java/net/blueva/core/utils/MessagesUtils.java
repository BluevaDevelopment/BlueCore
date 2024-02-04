/*
 *  ____  _             ____
 * | __ )| |_   _  ___ / ___|___  _ __ ___
 * |  _ \| | | | |/ _ | |   / _ \| '__/ _ \
 * | |_) | | |_| |  __| |__| (_) | | |  __/
 * |____/|_|\__,_|\___|\____\___/|_|  \___|
 *
 * This file is part of Blue Core.
 *
 * Blue Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation.
 *
 * Blue Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License version 3 for more details.
 *
 * Blue Core plugin developed by Blueva Development.
 * Website: https://blueva.net/
 * GitHub repository: https://github.com/BluevaDevelopment/BlueCore
 *
 * Copyright (c) 2024 Blueva Development. All rights reserved.
 */

package net.blueva.core.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.blueva.core.Main;
import net.blueva.core.modules.economy.EconomyModule;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Objects;

public class MessagesUtils {
    public static void sendToSender(CommandSender sender, String message) {
        if(sender instanceof Player player) {
            sendToPlayer(player, message);
        } else {
            sendToConsole(message);
        }
    }

    public static void sendToPlayer(Player player, String message) {
        if(!Objects.equals(message, "")) {
            Audience audience = Main.getPlugin().adventure().player(player);
            Component component = format(player, message);
            audience.sendMessage(component);
        }
    }

    public static void sendToConsole(String message) {
        if(!Objects.equals(message, "")) {
            Audience audience = Main.getPlugin().adventure().console();
            Component component = format(null, message);
            audience.sendMessage(component);
        }
    }

    public static void broadcast(String message) {
        for(Player players : Bukkit.getOnlinePlayers()) {
            sendToPlayer(players, message);
        }
    }

    public static @NotNull Component format (Player player, String message) {
        if(message == null) {
            return Component.empty();
        }

        String text_final = formatPlaceholders(player, message);
        return formatColors(text_final);
    }

    public static @NotNull String formatLegacy(Player player, String message) {
        if (message == null) {
            return "";
        }

        String textFinal = formatPlaceholders(player, message);
        return ChatColor.translateAlternateColorCodes('&', textFinal);
    }

    public static String formatPlaceholders(Player player, String text) {
        text = text
                .replace("{server_name}", Bukkit.getServer().getName())
                .replace("{server_max_players}", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                .replace("{server_version}", Bukkit.getServer().getVersion())
                .replace("{currency_symbol}", Main.currency_symbol)
                .replace("{prefix}", Main.prefix);

        if(player != null) {
            text = text
                    .replace("{player_name}", player.getName())
                    .replace("{player_display_name}", player.getDisplayName())
                    .replace("{player_ping}", String.valueOf(player.getPing()))
                    .replace("{player_exp}", String.valueOf(player.getExp()))
                    .replace("{player_health}", String.valueOf(player.getHealth()))
                    .replace("{player_level}", String.valueOf(player.getLevel()))
                    .replace("{player_balance}", String.valueOf(EconomyModule.balancePlayer(player, Main.getPlugin())))
                    .replace("{player_location_x}", String.valueOf(Math.round(player.getLocation().getX())))
                    .replace("{player_location_y}", String.valueOf(Math.round(player.getLocation().getY())))
                    .replace("{player_location_z}", String.valueOf(Math.round(player.getLocation().getZ())))
                    .replace("{player_world}", player.getWorld().getName());

            if(Main.placeholderapi) {
                return PlaceholderAPI.setPlaceholders(player, text);
            }
        }

        return text;
    }

    @NotNull
    public static Component formatColors(String message) {
        var mm = MiniMessage.miniMessage();
        return mm.deserialize(message);
    }

    public static void sendTitle(Player player, String maintitle, String subtitle, Integer fadeIn, Integer stay, Integer fadeOut) {
        Audience audience = Main.getPlugin().adventure().player(player);

        final Title.Times times = Title.Times.times(Duration.ofMillis(fadeIn*50), Duration.ofMillis(stay*50), Duration.ofMillis(fadeIn*50));
        final Title title = Title.title(formatColors(maintitle), formatColors(subtitle), times);

        audience.showTitle(title);
    }

    public static void sendActionBar(Player player, String message) {
        Audience audience = Main.getPlugin().adventure().player(player);
        audience.sendActionBar(formatColors(message));
    }
}
