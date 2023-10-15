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

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MessageCommand implements CommandExecutor {

    private final Main main;

    public MessageCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(MessagesUtil.format((Player) sender, main.configManager.getLang().getString("messages.other.use_message_command")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(MessagesUtil.format((Player) sender, main.configManager.getLang().getString("messages.error.player_offline")));
            return true;
        }

        if (!sender.hasPermission("bluecore.message")) {
            sender.sendMessage(MessagesUtil.format((Player) sender, main.configManager.getLang().getString("messages.error.no_perms")));
            return true;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            messageBuilder.append(args[i]).append(" ");
        }
        String message = messageBuilder.toString().trim();

        sender.sendMessage(MessagesUtil.format(target, Objects.requireNonNull(main.configManager.getLang().getString("messages.info.pm_sender_format"))
                .replace("{from_user}", sender.getName())
                .replace("{to_user}", target.getName())
                .replace("{message}", message)));
        target.sendMessage(MessagesUtil.format(target, Objects.requireNonNull(main.configManager.getLang().getString("messages.info.pm_receiver_format"))
                .replace("{from_user}", sender.getName())
                .replace("{to_user}", target.getName())
                .replace("{message}", message)));


        return true;
    }
}
