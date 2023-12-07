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
 * Copyright (c) 2023 Blueva Development. All rights reserved.
 */

package net.blueva.core.commands;

import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.utils.MessagesUtil;
import net.blueva.core.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class TitleCommand implements CommandExecutor {

    private final Main main;

    public TitleCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
        if (args.length < 2) {
            sender.sendMessage(MessagesUtil.format(null, ConfigManager.language.getString("messages.other.use_title_command")));
            return true;
        }

        if(args[0].equalsIgnoreCase("broadcast")){
            if(sender.hasPermission("bluecore.title.broadcast")){
                sendTitle(sender, args, 1, true, null);
            } else {
                sender.sendMessage(MessagesUtil.format(null, ConfigManager.language.getString("messages.error.no_perms")));
            }
        } else if(args[0].equalsIgnoreCase("send")){
            if(sender.hasPermission("bluecore.title.send")){

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(MessagesUtil.format((Player) sender, ConfigManager.language.getString("messages.error.player_offline")));
                    return true;
                }
                sendTitle(sender, args, 2, false, target);

            } else {
                sender.sendMessage(MessagesUtil.format(null, ConfigManager.language.getString("messages.error.no_perms")));
            }
        }else{
            sender.sendMessage(MessagesUtil.format(null, ConfigManager.language.getString("messages.other.use_title_command")));
        }
        return true;
    }

    private void sendTitle(CommandSender sender, String[] args, Integer arg, Boolean broadcast, Player player) {
        if (args.length > arg) {
            StringBuilder messageBuilder = new StringBuilder();

            for (int i = arg; i < args.length; i++) {
                if (i > arg) {
                    messageBuilder.append(" ");
                }
                messageBuilder.append(args[i]);
            }

            String[] parts = messageBuilder.toString().split(";", 5);

            int fadeIn = 20;
            int stay = 80;
            int fadeOut = 20;

            String title = "";
            String subTitle = "";

            if (parts.length >= 4) {
                if (StringUtil.isNumber(parts[0]) && StringUtil.isNumber(parts[1]) && StringUtil.isNumber(parts[2])) {
                    fadeIn = Integer.parseInt(parts[0]);
                    stay = Integer.parseInt(parts[1]);
                    fadeOut = Integer.parseInt(parts[2]);
                } else {
                    if(broadcast) {
                        sender.sendMessage(MessagesUtil.format(null, ConfigManager.language.getString("messages.other.use_title_broadcast_command")));
                    } else {
                        sender.sendMessage(MessagesUtil.format(null, ConfigManager.language.getString("messages.other.use_title_send_command")));
                    }
                }

                title = MessagesUtil.format(null, parts[3].trim());

                if (parts.length == 5) {
                    subTitle = MessagesUtil.format(null, parts[4].trim());
                }
            } else if (parts.length == 1) {
                title = MessagesUtil.format(null, parts[0].trim());
            } else if (parts.length == 2) {
                title = MessagesUtil.format(null, parts[0].trim());
                subTitle = MessagesUtil.format(null, parts[1].trim());
            } else {
                if(broadcast) {
                    sender.sendMessage(MessagesUtil.format(null, ConfigManager.language.getString("messages.other.use_title_broadcast_command")));
                } else {
                    sender.sendMessage(MessagesUtil.format(null, ConfigManager.language.getString("messages.other.use_title_send_command")));
                }
                return;
            }

            if (broadcast) {
                sender.sendMessage(MessagesUtil.format(null, Objects.requireNonNull(ConfigManager.language.getString("messages.success.title_sent_broadcast"))
                        .replace("%title%", title)
                        .replace("%subtitle%", subTitle)));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
                }
            } else {
                player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
                sender.sendMessage(MessagesUtil.format(player, Objects.requireNonNull(ConfigManager.language.getString("messages.success.title_sent"))
                        .replace("%player%", player.getName())
                        .replace("%title%", title)
                        .replace("%subtitle%", subTitle)));
            }
        } else {
            if(broadcast) {
                sender.sendMessage(MessagesUtil.format(null, ConfigManager.language.getString("messages.other.use_title_broadcast_command")));
            } else {
                sender.sendMessage(MessagesUtil.format(null, ConfigManager.language.getString("messages.other.use_title_send_command")));
            }
        }
    }
}