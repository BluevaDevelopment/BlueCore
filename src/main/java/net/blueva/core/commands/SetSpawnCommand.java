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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SetSpawnCommand implements CommandExecutor {

    private final Main main;

    public SetSpawnCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player player = null;
        if(sender instanceof Player) {
            player = (Player)sender;
        }

        if(!sender.hasPermission("bluecore.setspawn")) {
            sender.sendMessage(MessagesUtils.format(player, ConfigManager.language.getString("messages.error.no_permission")));
            return true;
        }

        if(ConfigManager.Modules.warps.getBoolean("warps.enabled")) {
            if(args.length != 1) {
                sender.sendMessage(MessagesUtils.format(player, ConfigManager.language.getString("messages.other.use_setspawn_command")));
                return true;
            }

            String warp = args[0];
            File warpFile = new File(Main.getPlugin().getDataFolder()+"/data/modules/warps/"+warp+".yml");
            if(!warpFile.exists()) {
                sender.sendMessage(MessagesUtils.format(player, ConfigManager.language.getString("messages.error.unknown_warp")));
                return true;
            }

            ConfigManager.Data.changeWarpReference(warp);
            ConfigManager.Modules.warps.set("warps.spawn", args[0]);
            try {
                ConfigManager.Modules.warps.save();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sender.sendMessage(MessagesUtils.format(player, Objects.requireNonNull(ConfigManager.language.getString("messages.success.spawn_set")).replace("%warp%", args[0])));
        } else {
            sender.sendMessage(MessagesUtils.format(player, ConfigManager.language.getString("messages.error.module_disabled")
                    .replace("%module%", "Warps")));
        }

        return true;
    }
}
