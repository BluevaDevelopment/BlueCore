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

package net.blueva.core.commands.legacy;

import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.utils.MessagesUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class WorldCommand implements CommandExecutor {

    private final Main main;

    public WorldCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
        if (!(sender instanceof Player)) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.only_player"));
            return true;
        }
        final Player player = (Player)sender;

        if (args.length == 0) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_world_command"));
            return true;
        }

        if(ConfigManager.Modules.worlds.getBoolean("worlds.enabled")) {
            if(args[0].equalsIgnoreCase("create")){
                if(player.hasPermission("bluecore.world.create")){
                    if (args.length == 1) {
                        MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_world_command"));
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) != null) {
                            MessagesUtils.sendToSender(sender, Objects.requireNonNull(ConfigManager.language.getString("messages.error.wm_alredy_exist")).replace("%world_name%",  worldname));
                        } else {
                            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_world_command"));
                        }
                        return true;
                    }
                    if (args.length == 3) {
                        if(args[2].equalsIgnoreCase("normal") || args[2].equalsIgnoreCase("nether") || args[2].equalsIgnoreCase("the_end")) {
                            try {
                                main.worldModule.createWorld(sender, player, args[1], null, null, null);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            return true;
                        } else {
                            MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.error.wm_invalid_args"));
                        }
                    }
                    if (args.length == 4) {
                        if(args[2].equalsIgnoreCase("normal") || args[2].equalsIgnoreCase("nether") || args[2].equalsIgnoreCase("the_end")) {
                            if(args[3].equals("-t")) {
                                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_world_create_command"));
                            } else if(args[3].equals("-g")) {
                                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_world_create_command"));
                            } else {
                                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_world_create_command"));
                            }
                        } else {
                            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_world_create_command"));
                        }

                    }
                    if (args.length == 5) {
                        if(args[2].equalsIgnoreCase("normal") || args[2].equalsIgnoreCase("nether") || args[2].equalsIgnoreCase("the_end")) {
                            if(args[3].equals("-t")) {
                                if(args[4].equals("flat") || args[4].equals("large_biomes") || args[4].equals("normal") || args[4].equals("amplified")) {
                                    try {
                                        main.worldModule.createWorld(sender, player, args[1], args[2].toUpperCase(), args[4].toUpperCase(), null);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    return true;
                                }
                            } else if(args[3].equals("-g")) {
                                if(!args[4].isEmpty()) {
                                    try {
                                        main.worldModule.createWorld(sender, player, args[1], args[2].toUpperCase(), null, args[4]);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            } else {
                                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_world_create_command"));
                            }
                        } else {
                            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.wm_invalid_args"));
                        }
                    }
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                }
            }else if(args[0].equalsIgnoreCase("delete")){
                if(player.hasPermission("bluecore.world.delete")){
                    if (args.length == 1) {
                        MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_world_command"));
                        return true;
                    }
                    if (args.length == 2) {
                        main.worldModule.deleteWorld(player, args[1]);
                        return true;
                    }
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                }
            }else if(args[0].equalsIgnoreCase("setspawn")){
                if(player.hasPermission("bluecore.world.setspawn")){
                    if (args.length == 1) {
                        try {
                            main.worldModule.setWorldSpawn(sender, player);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return true;
                    }
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                }
            }else if(args[0].equalsIgnoreCase("spawn")){
                if(player.hasPermission("bluecore.world.spawn")){
                    if (args.length == 1) {
                        main.worldModule.gotoWorldSpawn(player);
                        return true;
                    }
                } else {
                    MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.error.no_perms"));
                }
            }else if(args[0].equalsIgnoreCase("goto")){
                if(player.hasPermission("bluecore.world.goto")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage /wm goto <worldname>");
                        return true;
                    }
                    if (args.length == 2) {
                        main.worldModule.gotoWorld(sender, player, args[1]);
                        return true;
                    }
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                }
            }else if(args[0].equalsIgnoreCase("import")){
                if(player.hasPermission("bluecore.world.import")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage: /wm import <worldname>");
                        return true;
                    }
                    if (args.length == 2) {
                        main.worldModule.importWorld(player, args[1]);
                        return true;
                    }
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                }
            }else if(args[0].equalsIgnoreCase("list")){
                if(player.hasPermission("bluecore.world.list")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Manager");
                        player.sendMessage(ChatColor.GREEN + "List of worlds [" + Bukkit.getWorlds().size() + "]");
                        player.sendMessage("");
                        for (World worlds : Bukkit.getWorlds())
                            player.sendMessage(ChatColor.AQUA + worlds.getName() + ChatColor.GRAY + " - " + worlds.getWorldType());
                        return true;
                    }
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                }
            }else{
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.unknown_command"));
            }
        } else {
            MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.error.module_disabled")
                    .replace("%module%", "World"));
        }
        return true;
    }

}