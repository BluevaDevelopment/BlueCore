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
import org.bukkit.command.Command;
import net.blueva.core.commands.CommandInterface;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtils;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

public class DeleteWarpCommand implements CommandInterface {

    private final Main main;

    public DeleteWarpCommand(Main main) {
        this.main = main;
    }

    public void onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) throws IOException {
        if (!(sender instanceof Player player)) {
            MessagesUtils.sendToConsole(ConfigManager.language.getString("messages.error.only_player"));
            return;
        }

        if(!sender.hasPermission("bluecore.updatewarp")) {
            MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.error.no_permission"));
            return;
        }

        if(ConfigManager.Modules.warps.getBoolean("warps.enabled")) {
            if(args.length > 0){
                if(args.length == 1){
                    WarpModule.deleteWarp(args[0], player);
                } else {
                    MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.other.use_deletewarp_command"));
                }
            }
        } else {
            MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.error.module_disabled")
                    .replace("%module%", "Warps"));
        }

        return;
    }
}
