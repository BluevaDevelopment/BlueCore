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

public class FlyCommand implements CommandExecutor {

    private final Main main;

    public FlyCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player) && args.length != 1) {
            MessagesUtils.sendToConsole(ConfigManager.language.getString("messages.other.use_fly_command"));
            return true;
        }

        Player target;
        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                assert sender instanceof Player;
                MessagesUtils.sendToSender((Player) sender, ConfigManager.language.getString("messages.error.player_offline"));
                return true;
            }
            if (!sender.hasPermission("bluecore.fly.others")) {
                assert sender instanceof Player;
                MessagesUtils.sendToSender((Player) sender, ConfigManager.language.getString("messages.error.no_perms"));
                return true;
            }
        } else {
            target = (Player) sender;
            if (!sender.hasPermission("bluecore.fly")) {
                MessagesUtils.sendToSender(target, ConfigManager.language.getString("messages.error.no_perms"));
                return true;
            }
        }

        if(target.isFlying()) {
            target.setAllowFlight(false);
            target.setFlying(false);
            MessagesUtils.sendToPlayer(target, ConfigManager.language.getString("messages.success.fly_mode_disabled"));
        } else {
            target.setAllowFlight(true);
            target.setFlying(true);
            MessagesUtils.sendToPlayer(target, ConfigManager.language.getString("messages.success.fly_mode_enabled"));
        }
        if (args.length == 1) {
            if(target.isFlying()) {
                MessagesUtils.sendToSender(target, ConfigManager.language.getString("messages.success.fly_mode_disabled_others").replace("%player%", target.getName()));
            } else {
                MessagesUtils.sendToSender(target, ConfigManager.language.getString("messages.success.fly_mode_enabled_others").replace("%player%", target.getName()));
            }
        }

        return true;
    }
}