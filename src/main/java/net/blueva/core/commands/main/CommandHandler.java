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
 * Copyright (c) 2025 Blueva Development. All rights reserved.
 */

package net.blueva.core.commands.main;

import net.blueva.core.Main;
import net.blueva.core.commands.CommandInterface;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.utils.MessagesUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.util.HashMap;

public class CommandHandler implements CommandExecutor
{

    private static final HashMap<String, CommandInterface> commands = new HashMap<>();

    public void register(String name, CommandInterface cmd) {

        commands.put(name, cmd);
    }

    public boolean exists(String name) {
        return commands.containsKey(name);
    }

    public CommandInterface getExecutor(String name) {
        return commands.get(name);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, @NotNull String[] args) {

        if(args.length == 0) {
            try {
                getExecutor("bluecore").onCommand(sender, cmd, commandLabel, args);
            } catch (SerializationException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        if(exists(args[0])){

            try {
                getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {

            Main.getPlugin().adventure().sender(sender).sendMessage(MessagesUtils.format(Bukkit.getPlayer(sender.getName()), ConfigManager.language.getString("messages.error.unknown_command")
                    .replace("{command}", "bw")));
        }
        return true;
    }

}