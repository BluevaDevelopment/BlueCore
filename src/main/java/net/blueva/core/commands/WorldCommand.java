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

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class WorldCommand implements CommandExecutor {

    private final Main main;

    public WorldCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.only_player")));
            return true;
        }
        final Player player = (Player)sender;

        if (args.length == 0) {
            sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_world_command")));
            return true;
        }

        if(args[0].equalsIgnoreCase("create")){
            if(player.hasPermission("bluecore.worldmanager.create")){
                if (args.length == 1) {
                    player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_world_command")));
                    return true;
                }
                if (args.length == 2) {
                    String worldname = args[1];
                    if (Bukkit.getWorld(worldname) != null) {
                        player.sendMessage(MessagesUtil.format(player, Objects.requireNonNull(main.configManager.getLang().getString("messages.error.wm_alredy_exist")).replace("%worldmanager_worldname%",  worldname)));
                    } else {
                        player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_world_command")));
                    }
                    return true;
                }
                if (args.length == 3) {
                    if(args[2].equalsIgnoreCase("normal") || args[2].equalsIgnoreCase("nether") || args[2].equalsIgnoreCase("the_end")) {
                        main.worldManager.createWorld(player, args[1], null, null, null);
                        return true;
                    } else {
                        player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.wm_invalid_args")));
                    }
                }
                if (args.length == 4) {
                    if(args[2].equalsIgnoreCase("normal") || args[2].equalsIgnoreCase("nether") || args[2].equalsIgnoreCase("the_end")) {
                        if(args[3].equals("-t")) {
                            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_world_create_command")));
                        } else if(args[3].equals("-g")) {
                            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_world_create_command")));
                        } else {
                            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_world_create_command")));
                        }
                    } else {
                        player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_world_create_command")));
                    }

                }
                if (args.length == 5) {
                    if(args[2].equalsIgnoreCase("normal") || args[2].equalsIgnoreCase("nether") || args[2].equalsIgnoreCase("the_end")) {
                        if(args[3].equals("-t")) {
                            if(args[4].equals("flat") || args[4].equals("large_biomes") || args[4].equals("normal") || args[4].equals("amplified")) {
                                main.worldManager.createWorld(player, args[1], args[2].toUpperCase(), args[4].toUpperCase(), null);
                                return true;
                            }
                        } else if(args[3].equals("-g")) {
                            if(!args[4].isEmpty()) {
                                main.worldManager.createWorld(player, args[1], args[2].toUpperCase(), null, args[4]);
                            }
                        } else {
                            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_world_create_command")));
                        }
                    } else {
                        player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.wm_invalid_args")));
                    }
                }
            } else {
                player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_perms")));
            }
        }else if(args[0].equalsIgnoreCase("delete")){
            if(player.hasPermission("bluecore.worldmanager.delete")){
                if (args.length == 1) {
                    player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_world_command")));
                    return true;
                }
                if (args.length == 2) {
                    main.worldManager.deleteWorld(player, args[1]);
                    return true;
                }
            } else {
                player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_perms")));
            }
        }else if(args[0].equalsIgnoreCase("setspawn")){
            if(player.hasPermission("bluecore.worldmanager.setspawn")){
                if (args.length == 1) {
                    main.worldManager.setWorldSpawn(player);
                    return true;
                }
            } else {
                player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_perms")));
            }
        }else if(args[0].equalsIgnoreCase("spawn")){
            if(player.hasPermission("bluecore.worldmanager.spawn")){
                if (args.length == 1) {
                    main.worldManager.gotoWorldSpawn(player);
                    return true;
                }
            } else {
                player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_perms")));
            }
        }else if(args[0].equalsIgnoreCase("goto")){
            if(player.hasPermission("bluecore.worldmanager.goto")){
                if (args.length == 1) {
                    player.sendMessage(ChatColor.RED + "Usage /wm goto <worldname>");
                    return true;
                }
                if (args.length == 2) {
                    main.worldManager.gotoWorld(player, args[1]);
                    return true;
                }
            } else {
                player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_perms")));
            }
        }else if(args[0].equalsIgnoreCase("import")){
            if(player.hasPermission("bluecore.worldmanager.import")){
                if (args.length == 1) {
                    player.sendMessage(ChatColor.RED + "Usage: /wm import <worldname>");
                    return true;
                }
                if (args.length == 2) {
                    main.worldManager.importWorld(player, args[1]);
                    return true;
                }
            } else {
                player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_perms")));
            }
        }else if(args[0].equalsIgnoreCase("list")){
            if(player.hasPermission("bluecore.worldmanager.list")){
                if (args.length == 1) {
                    player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Manager");
                    player.sendMessage(ChatColor.GREEN + "List of worlds [" + Bukkit.getWorlds().size() + "]");
                    player.sendMessage("");
                    for (World worlds : Bukkit.getWorlds())
                        player.sendMessage(ChatColor.AQUA + worlds.getName() + ChatColor.GRAY + " - " + worlds.getWorldType());
                    return true;
                }
            } else {
                player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_perms")));
            }
        }else{
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.unknown_command")));
        }
        return true;
    }

}