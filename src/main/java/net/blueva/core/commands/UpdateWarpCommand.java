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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class UpdateWarpCommand implements CommandExecutor {

    private final Main main;

    public UpdateWarpCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(MessagesUtils.format(null, ConfigManager.language.getString("messages.error.only_player")));
            return true;
        }

        if(!sender.hasPermission("bluecore.updatewarp")) {
            player.sendMessage(MessagesUtils.format(player, ConfigManager.language.getString("messages.error.no_permission")));
            return true;
        }

        if(ConfigManager.Modules.warps.getBoolean("warps.enabled")) {
            if(args.length > 0){
                if(args.length == 1){
                    try {
                        if(WarpModule.updateWarp(args[0], player)) {
                            player.sendMessage(MessagesUtils.format(player, Objects.requireNonNull(ConfigManager.language.getString("messages.success.warp_updated")).replace("%warp%", args[0])));
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    player.sendMessage(MessagesUtils.format(player, ConfigManager.language.getString("messages.other.use_updatewarp_command")));
                }
            }
        } else {
            sender.sendMessage(MessagesUtils.format(player, ConfigManager.language.getString("messages.error.module_disabled")
                    .replace("%module%", "Warps")));
        }
        return true;
    }
}
