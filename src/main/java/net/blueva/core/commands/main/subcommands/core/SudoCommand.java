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
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import net.blueva.core.commands.CommandInterface;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtils;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;

public class SudoCommand implements CommandInterface {

    private final Main main;

    public SudoCommand(Main main) {
        this.main = main;
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) throws IOException {
        if (args.length < 2) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_sudo_command"));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.player_offline"));
            return;
        }

        if (!sender.hasPermission("bluecore.sudo")) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
            return;
        }

        StringBuilder commandBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            commandBuilder.append(args[i]).append(" ");
        }
        String command = commandBuilder.toString().trim();

        target.chat("/"+ MessagesUtils.formatLegacy(target, command));

        return;
    }
}
