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

public class SpeedCommand implements CommandExecutor {

    private final Main main;

    public SpeedCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_speed_command"));
            return true;
        }

        if (!sender.hasPermission("bluecore.speed")) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
            return true;
        }

        float speed;
        try {
            speed = Float.parseFloat(args[0]);
        } catch (NumberFormatException e) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.invalid_speed"));
            return true;
        }

        if (speed < 0 || speed > 1) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.invalid_speed"));
            return true;
        }

        Player target;
        if (args.length == 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.player_offline"));
                return true;
            }
            if (!sender.hasPermission("bluecore.speed.others")) {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                return true;
            }
        } else if (sender instanceof Player) {
            target = (Player) sender;
        } else {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_speed_command"));
            return true;
        }

        target.setWalkSpeed(speed);
        target.setFlySpeed(speed);
        MessagesUtils.sendToPlayer(target, Objects.requireNonNull(ConfigManager.language.getString("messages.success.speed_changed")).replace("%speed%", String.valueOf(speed)));
        if (args.length == 2) {
            MessagesUtils.sendToPlayer(target, Objects.requireNonNull(ConfigManager.language.getString("messages.success.speed_changed_others")).replace("%speed%", String.valueOf(speed)).replace("%player%", target.getName()));
        }

        return true;
    }
}