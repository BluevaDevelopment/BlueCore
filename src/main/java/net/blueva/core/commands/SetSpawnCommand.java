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
 * GitHub repository: https://github.com/BluevaDevelopment/BlueMenu
 *
 * Copyright (c) 2023 Blueva Development. All rights reserved.
 */

package net.blueva.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;
import org.jetbrains.annotations.NotNull;

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
            sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_permission")));
            return true;
        }

        if(args.length != 1) {
            sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_setspawn_command")));
            return true;
        }

        if(!main.configManager.getWarps().isSet("warps." + args[0])) {
            sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.unknown_warp")));
            return true;
        }

        main.configManager.getWarps().set("spawn", args[0]);
        main.configManager.saveWarps();
        main.configManager.reloadWarps();
        sender.sendMessage(MessagesUtil.format(player, Objects.requireNonNull(main.configManager.getLang().getString("messages.success.spawn_set")).replace("%warp%", args[0])));
        return true;
    }
}
