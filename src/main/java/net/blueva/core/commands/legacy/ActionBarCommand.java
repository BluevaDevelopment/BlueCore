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
 * Copyright (c) 2026 Blueva Development. All rights reserved.
 */

package net.blueva.core.commands.legacy;

import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.utils.MessagesUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ActionBarCommand implements CommandExecutor {

    private final Main main;

    public ActionBarCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
        if (args.length < 2) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_actionbar_command"));
            return true;
        }
        if(args[0].equalsIgnoreCase("broadcast")){
            if(sender.hasPermission("bluecore.actionbar.broadcast")){
                StringBuilder messageBuilder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    messageBuilder.append(args[i]).append(" ");
                }
                String message = messageBuilder.toString().trim();

                for(Player player : Bukkit.getOnlinePlayers()) {
                    MessagesUtils.sendActionBar(player, message);
                }
            } else {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
            }
        }else if(args[0].equalsIgnoreCase("send")){
            if(sender.hasPermission("bluecore.title.send")){
                if(args.length < 3) {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_actionbar_command"));
                    return true;
                }

                StringBuilder messageBuilder = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    messageBuilder.append(args[i]).append(" ");
                }
                String message = messageBuilder.toString().trim();

                Player player = Bukkit.getPlayer(args[1]);
                if(player != null) {
                    MessagesUtils.sendActionBar(player, message);
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.player_offline"));
                }
            } else {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
            }
        }else{
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_actionbar_command"));
        }
        return true;
    }
}