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

import net.blueva.core.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class KillCommand implements CommandExecutor {

    private final Main main;

    public KillCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(MessagesUtils.format(null, ConfigManager.language.getString("messages.other.use_kill_command")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        Player cmdsender = null;
        if(sender instanceof Player) {
            cmdsender = (Player) sender;
        }

        if (!sender.hasPermission("bluecore.kill")) {
            sender.sendMessage(MessagesUtils.format(target, ConfigManager.language.getString("messages.error.no_perms")));
            return true;
        }

        if (target == null) {
            sender.sendMessage(MessagesUtils.format(cmdsender, ConfigManager.language.getString("messages.error.player_offline")));
            return true;
        }

        target.setHealth(0);
        sender.sendMessage(MessagesUtils.format(cmdsender, Objects.requireNonNull(ConfigManager.language.getString("messages.success.player_killed")).replace("%player%", target.getName())));

        return true;
    }
}