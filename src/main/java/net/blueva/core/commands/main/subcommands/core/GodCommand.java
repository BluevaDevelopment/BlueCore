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
import net.blueva.core.configuration.DataManager;
import net.blueva.core.utils.MessagesUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import net.blueva.core.commands.CommandInterface;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.serialize.SerializationException;
import java.io.IOException;

public class GodCommand implements CommandInterface {

    private final Main main;

    public GodCommand(Main main) {
        this.main = main;
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) throws IOException {
        if (!(sender instanceof Player) && args.length != 1) {
            MessagesUtils.sendToConsole(ConfigManager.language.getString("messages.other.use_god_command"));
            return;
        }

        Player target;
        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.player_offline"));
                return;
            }
            if (!sender.hasPermission("bluecore.god.others")) {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                return;
            }
        } else {
            target = (Player) sender;
            if (!sender.hasPermission("bluecore.god")) {
                MessagesUtils.sendToSender(target, ConfigManager.language.getString("messages.error.no_perms"));
                return;
            }
        }
        DataManager.Users.changeUserReference(target.getUniqueId().toString());
        if(DataManager.Users.getUser(target.getUniqueId()).node("god_mode").getBoolean()) {
            try {
                DataManager.Users.getUser(target.getUniqueId()).node("god_mode").set(false);
            } catch (SerializationException e) {
                throw new RuntimeException(e);
            }

            MessagesUtils.sendToSender(target, ConfigManager.language.getString("messages.success.god_mode_disabled"));
        } else {
            try {
                DataManager.Users.getUser(target.getUniqueId()).node("god_mode").set(true);
            } catch (SerializationException e) {
                throw new RuntimeException(e);
            }
            DataManager.Users.saveUser(target.getUniqueId());
            DataManager.Users.reloadUser(target.getUniqueId());
            MessagesUtils.sendToSender(target, ConfigManager.language.getString("messages.success.god_mode_enabled"));
        }
        if (args.length == 1) {
            if(DataManager.Users.getUser(target.getUniqueId()).node("god_mode").getBoolean()) {
                MessagesUtils.sendToSender(target, ConfigManager.language.getString("messages.success.god_mode_disabled_others").replace("%player%", target.getName()));
            } else {
                MessagesUtils.sendToSender(target, ConfigManager.language.getString("messages.success.god_mode_enabled_others").replace("%player%", target.getName()));
            }
        }

        return;
    }
}