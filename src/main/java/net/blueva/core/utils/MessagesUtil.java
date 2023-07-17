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
 * GitHub repository: https://github.com/BluevaDevelopment/BlueMenu
 *
 * Copyright (c) 2023 Blueva Development. All rights reserved.
 */

package net.blueva.core.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.libraries.iridiumcolor.IridiumColorAPI;

import org.jetbrains.annotations.NotNull;

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
                    .replace("{player_world}", player.getWorld().getName())
                    .replace("{server_name}", Bukkit.getServer().getName())
                    .replace("{server_max_players}", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                    .replace("{server_version}", Bukkit.getServer().getVersion())
                    .replace("{prefix}", Main.prefix);

            if(Main.placeholderapi) {
                return PlaceholderAPI.setPlaceholders(player, text);
            }
        } else {
            text = text
                    .replace("{server_name}", Bukkit.getServer().getName())
                    .replace("{server_max_players}", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                    .replace("{server_version}", Bukkit.getServer().getVersion())
                    .replace("{prefix}", Main.prefix);
        }

        return text;
    }

    public static @NotNull String formatColors(String text) {
        if(text == null) {
            return "";
        }

        return IridiumColorAPI.process(text);
    }
}
