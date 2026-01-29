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
import net.blueva.core.commands.CommandInterface;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.utils.MessagesUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class InfoSubCommand implements CommandInterface
{

    @Override
    public void onCommand(CommandSender sender, Command cmd,
                          String commandLabel, String[] args) {

        if(sender.hasPermission("bluecore.help") || sender.isOp()) {
            Player player = null;
            if (sender instanceof Player p) {
                player = p.getPlayer();
            }

            //to do
            List<String> bc_info = ConfigManager.language.getStringList("commands.bluecore.info");
            for(String message : bc_info) {
                message = message.replace("{plugin_version}", Main.getPlugin().pluginversion);
                Main.getPlugin().adventure().sender(sender).sendMessage(MessagesUtils.format(player, message));
            }
        }
    }
}