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

package net.blueva.core.commands.main.subcommands.core;

import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.utils.MessagesUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import net.blueva.core.commands.CommandInterface;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

public class StormCommand implements CommandInterface {

    private final Main main;

    public StormCommand(Main main) {
        this.main = main;
    }

    public void onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) throws IOException {
        World world = null;
        if (args.length > 0) {
            world = Bukkit.getWorld(args[0]);
        }
        if (sender instanceof Player) {
            if (world == null) {
                world = ((Player) sender).getWorld();
            }
            if (!sender.hasPermission("bluecore.weather.storm")) {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                return;
            }
            world.setStorm(true);
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.success.changed_weather")
                    .replace("%world_name%", world.getName())
                    .replace("%weather%", "Storm"));
        } else {
            if (world == null) {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_storm_command"));
                return;
            }
            world.setStorm(true);
            MessagesUtils.sendToSender(null, ConfigManager.language.getString("messages.success.changed_weather")
                    .replace("%world%", world.getName())
                    .replace("%weather%", "Storm"));
        }
        return;
    }
}