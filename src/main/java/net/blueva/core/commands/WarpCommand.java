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
import net.blueva.core.modules.WarpModule;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WarpCommand implements CommandExecutor {

    private final Main main;

    public WarpCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player finalsender = null;
        if(sender instanceof Player) {
            finalsender = (Player) sender;
        }

        if(ConfigManager.Modules.warps.getBoolean("warps.enabled")) {
            if (args.length > 0) {
                String warp = args[0];
                if (args.length == 2) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target != null) {
                        if(WarpModule.teleportPlayer(target, warp)) {
                            sender.sendMessage(MessagesUtils.format(target, Objects.requireNonNull(ConfigManager.language.getString("messages.success.teleported_to_warp_others")).replace("%warp%", warp).replace("%player%", target.getName())));
                        } else {
                            sender.sendMessage(MessagesUtils.format(finalsender, ConfigManager.language.getString("messages.error.unknown_warp")));
                        }
                    } else {
                        sender.sendMessage(MessagesUtils.format(finalsender, ConfigManager.language.getString("messages.error.player_offline")));
                    }
                } else if (args.length == 1) {
                    if (sender instanceof Player player) {
                        if(WarpModule.teleportPlayer(player, warp)) {
                            player.sendMessage(MessagesUtils.format(player, Objects.requireNonNull(ConfigManager.language.getString("messages.success.teleported_to_warp")).replace("%warp%", warp)));
                        } else {
                            player.sendMessage(MessagesUtils.format(player, ConfigManager.language.getString("messages.error.unknown_warp")));
                        }
                    } else {
                        sender.sendMessage(MessagesUtils.format(null, ConfigManager.language.getString("messages.other.use_warp_command")));
                    }
                } else {
                    sender.sendMessage(MessagesUtils.format(finalsender, ConfigManager.language.getString("messages.other.use_warp_command")));
                }
            } else {
                sender.sendMessage(MessagesUtils.format(finalsender, ConfigManager.language.getString("messages.other.use_warp_command")));
            }
        } else {
            sender.sendMessage(MessagesUtils.format(finalsender, ConfigManager.language.getString("messages.error.module_disabled")
                    .replace("%module%", "Warps")));
        }

        return true;
    }
}