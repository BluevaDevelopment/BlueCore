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
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MidnightCommand implements CommandExecutor {

    private final Main main;

    public MidnightCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        World world = null;
        if (args.length > 0) {
            world = Bukkit.getWorld(args[0]);
        }
        if (sender instanceof Player) {
            if (world == null) {
                world = ((Player) sender).getWorld();
            }
            if (!sender.hasPermission("bluecore.time.midnight" + (world == ((Player) sender).getWorld() ? "" : ".others"))) {
                sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }
            world.setTime(18000);
            sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.success.changed_time"))
                    .replace("%world%", world.getName())
                    .replace("%time%", "Midnight")
                    .replace("%ticks%", "6000"));
        } else {
            if (world == null) {
                sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_noon_command")));
                return true;
            }
            world.setTime(18000);
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.success.changed_time"))
                    .replace("%world%", world.getName())
                    .replace("%time%", "Midnight")
                    .replace("%ticks%", "6000"));
        }
        return true;
    }
}