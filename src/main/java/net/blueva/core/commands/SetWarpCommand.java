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

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class SetWarpCommand implements CommandExecutor {

    private Main main;

    public SetWarpCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.only_player")));
            return true;
        }

        final Player player = (Player)sender;
        if(!player.hasPermission("bluecore.setwarp")) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_permission")));
            return true;
        }

        if(args.length < 1) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_setwarp_command")));
            return true;
        }

        if(main.configManager.getWarps().isSet("warps." + args[0])) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.warp_already_set")));
            return true;
        }

        Location l = player.getLocation();
        String world = l.getWorld().getName();
        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();
        float yaw = l.getYaw();
        float pitch = l.getPitch();
        main.configManager.getWarps().set("warps." + args[0] + ".world", world);
        main.configManager.getWarps().set("warps." + args[0] + ".x", x);
        main.configManager.getWarps().set("warps." + args[0] + ".y", y);
        main.configManager.getWarps().set("warps." + args[0] + ".z", z);
        main.configManager.getWarps().set("warps." + args[0] + ".yaw", yaw);
        main.configManager.getWarps().set("warps." + args[0] + ".pitch", pitch);
        main.configManager.saveWarps();
        main.configManager.reloadWarps();
        player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.success.warp_set").replace("%warp%", args[0])));

        return true;
    }
}
