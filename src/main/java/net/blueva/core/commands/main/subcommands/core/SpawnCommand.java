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

import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.modules.WarpModule;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import net.blueva.core.commands.CommandInterface;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.io.IOException;

public class SpawnCommand implements CommandInterface {

    private final Main main;

    public SpawnCommand(Main main) {
        this.main = main;
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) throws IOException {
        if (!(sender instanceof Player) && args.length != 1) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_spawn_command"));
            return;
        }

        Player target;
        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                assert sender instanceof Player;
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.player_offline"));
                return;
            }
            if (!sender.hasPermission("bluecore.spawn.others")) {
                assert sender instanceof Player;
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                return;
            }
        } else {
            target = (Player) sender;
            if (!sender.hasPermission("bluecore.spawn")) {
                MessagesUtils.sendToSender(target, ConfigManager.language.getString("messages.error.no_perms"));
                return;
            }
        }

        if(WarpModule.teleportSpawn(target)) {
            MessagesUtils.sendToPlayer(target, ConfigManager.language.getString("messages.success.teleported_to_spawn"));
        } else {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.spawn_not_set"));
        }
        if (args.length == 1) {
            MessagesUtils.sendToSender(target, Objects.requireNonNull(ConfigManager.language.getString("messages.success.teleported_to_spawn_others")).replace("%player%", target.getName()));
        }

        return;
    }
}