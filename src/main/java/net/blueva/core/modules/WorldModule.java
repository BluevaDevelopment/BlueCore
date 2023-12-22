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

package net.blueva.core.modules;

import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.utils.MessagesUtils;
import net.kyori.adventure.audience.Audience;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
public class WorldModule {

    private final Main main;

    public WorldModule(Main main) {
        this.main = main;
    }

    public void loadWorlds() throws IOException {
        File dataFolder = new File(Main.getPlugin().getDataFolder() + "/data/modules/worlds/");
        File[] files = dataFolder.listFiles();

        if(files != null) {
            if(files.length == 0) {
                Bukkit.getConsoleSender().sendMessage("[BlueCore] [World Module] Importing worlds from the server to BlueCore World Module.");
                for (World world : Bukkit.getWorlds()) {
                    ConfigManager.Data.changeWorldReference(world.getName());
                    ConfigManager.Data.world.set("world." + world.getName() + ".name", world.getName());
                    ConfigManager.Data.world.set("world." + world.getName() + ".alias", "&b" + world.getName().replace("_", " "));
                    ConfigManager.Data.world.set("world." + world.getName() + ".build", true);
                    ConfigManager.Data.world.set("world." + world.getName() + ".break", true);
                    ConfigManager.Data.world.set("world." + world.getName() + ".pvp", true);
                    ConfigManager.Data.world.set("world." + world.getName() + ".fall_damage", true);
                    ConfigManager.Data.world.set("world." + world.getName() + ".interact", true);
                    ConfigManager.Data.world.set("world." + world.getName() + ".drop_items", true);
                    ConfigManager.Data.world.set("world." + world.getName() + ".mob_spawning", true);
                    ConfigManager.Data.world.set("world." + world.getName() + ".difficulty", world.getDifficulty().toString());
                    ConfigManager.Data.world.set("world." + world.getName() + ".respawnWorld", "");
                    ConfigManager.Data.world.set("world." + world.getName() + ".allowWeather", true);
                    ConfigManager.Data.world.set("world." + world.getName() + ".seed", world.getSeed());
                    if(world.getGenerator() != null) ConfigManager.Data.world.set("world." + world.getName() + ".generator", world.getGenerator().toString().split("\\(")[0].trim());
                    ConfigManager.Data.world.set("world." + world.getName() + ".environment", world.getEnvironment().toString());
                    ConfigManager.Data.world.set("world." + world.getName() + ".type", world.getWorldType().toString());
                    ConfigManager.Data.world.set("world." + world.getName() + ".spawnlocation.x", 0.0);
                    ConfigManager.Data.world.set("world." + world.getName() + ".spawnlocation.y", 65.0);
                    ConfigManager.Data.world.set("world." + world.getName() + ".spawnlocation.z", 0.0);
                    ConfigManager.Data.world.set("world." + world.getName() + ".spawnlocation.pitch", 0.0);
                    ConfigManager.Data.world.set("world." + world.getName() + ".spawnlocation.yaw", 0.0);
                    ConfigManager.Data.world.save();
                    Bukkit.getConsoleSender().sendMessage("[BlueCore] [World Module] Imported world: " + world.getName());
                }
            } else {
                for(File file : files) {
                    Bukkit.getConsoleSender().sendMessage("[BlueCore] [World Module] Loading world " + file.getName());
                    WorldCreator setupworld = new WorldCreator(file.getName().replace(".yml", ""));
                    setupworld.createWorld();
                }
            }
        }
    }

    public void createWorld(CommandSender sender, Player player, String name, String environment, String type, String generator) throws IOException {
        if(environment == null) {
            environment = "normal";
        }
        if(type == null) {
            type = "normal";
        }

        MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.info.wm_creating_world").replace("%worldmanager_worldname%", name));
        WorldCreator setupworld = new WorldCreator(name);
        setupworld.environment(World.Environment.valueOf(environment.toUpperCase()));
        setupworld.type(WorldType.valueOf(type.toUpperCase()));
        setupworld.generateStructures(true);

        if(generator != null) {
            setupworld.generator(generator);
        }

        try {
            setupworld.createWorld();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.wm_creation_error"));
        }

        ConfigManager.Data.changeWorldReference(name);
        ConfigManager.Data.world.set("world." + name + ".name", name);
        ConfigManager.Data.world.set("world." + name + ".alias", "&b" + name.replace("_", " "));
        ConfigManager.Data.world.set("world." + name + ".build", ConfigManager.Modules.worlds.getString("worlds.defaults.build"));
        ConfigManager.Data.world.set("world." + name + ".break", ConfigManager.Modules.worlds.getString("worlds.defaults.break"));
        ConfigManager.Data.world.set("world." + name + ".pvp", ConfigManager.Modules.worlds.getString("worlds.defaults.pvp"));
        ConfigManager.Data.world.set("world." + name + ".fall_damage", ConfigManager.Modules.worlds.getString("worlds.defaults.fall_damage"));
        ConfigManager.Data.world.set("world." + name + ".interact", ConfigManager.Modules.worlds.getString("worlds.defaults.interact"));
        ConfigManager.Data.world.set("world." + name + ".drop_items", ConfigManager.Modules.worlds.getString("worlds.defaults.drop_items"));
        ConfigManager.Data.world.set("world." + name + ".mob_spawning", ConfigManager.Modules.worlds.getString("worlds.defaults.mob_spawning"));
        ConfigManager.Data.world.set("world." + name + ".difficulty", "NORMAL");
        ConfigManager.Data.world.set("world." + name + ".respawn_world", ConfigManager.Modules.worlds.getString("worlds.defaults.respawn_world"));
        ConfigManager.Data.world.set("world." + name + ".allow_weather", ConfigManager.Modules.worlds.getString("worlds.defaults.allow_weather"));
        ConfigManager.Data.world.set("world." + name + ".seed", Objects.requireNonNull(Bukkit.getWorld(name)).getSeed());
        ConfigManager.Data.world.set("world." + name + ".generator", "");
        ConfigManager.Data.world.set("world." + name + ".environment", environment.toUpperCase());
        ConfigManager.Data.world.set("world." + name + ".type", type.toUpperCase());
        ConfigManager.Data.world.set("world." + name + ".spawn_location.x", ConfigManager.Modules.worlds.getFloat("worlds.defaults.spawn_location.x"));
        ConfigManager.Data.world.set("world." + name + ".spawn_location.y", ConfigManager.Modules.worlds.getFloat("worlds.defaults.spawn_location.y"));
        ConfigManager.Data.world.set("world." + name + ".spawn_location.z", ConfigManager.Modules.worlds.getFloat("worlds.defaults.spawn_location.z"));
        ConfigManager.Data.world.set("world." + name + ".spawn_location.pitch", ConfigManager.Modules.worlds.getFloat("worlds.defaults.spawn_location.pitch"));
        ConfigManager.Data.world.set("world." + name + ".spawn_location.yaw", ConfigManager.Modules.worlds.getFloat("worlds.defaults.spawn_location.yaw"));
        ConfigManager.Data.world.save();

        MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.success.wm_created_world").replace("%world_name%", name));

        if(player != null) {
            player.teleport(Objects.requireNonNull(Bukkit.getWorld(name)).getSpawnLocation());
        }
    }

    public void deleteWorld(CommandSender sender, String name) {
        if (Bukkit.getWorld(name) == null) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.wm_invalid_world"));
            return;
        }
        if (!Bukkit.getWorld(name).getPlayers().isEmpty()) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.wm_players_in_world"));
            return;
        }
        Bukkit.unloadWorld(name, false);
    }

    public void importWorld(Player player, String name) {
        File worldfile = new File(Bukkit.getServer().getWorldContainer(), name);
        if (!worldfile.exists()) {
            MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.error.wm_invalid_world"));
            return;
        }
        if (Bukkit.getWorld(name) != null) {
            MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.error.wm_already_imported"));
            return;
        }
        Bukkit.unloadWorld(name, true);
        try {
            WorldCreator setupworld = new WorldCreator(name);
            setupworld.createWorld();
            MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.success.wm_imported_world").replace("%world_world%", name));
            player.teleport(Bukkit.getWorld(name).getSpawnLocation());
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.error.wm_imported_error"));
        }
    }

    public void setWorldSpawn(CommandSender sender, Player player) throws IOException {
        String name = player.getWorld().getName();
        if (Bukkit.getWorld(name) == null) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.wm_invalid_world"));
            return;
        }
        ConfigManager.Data.changeWorldReference(name);
        ConfigManager.Data.world.set("world." + name + ".spawnlocation.x", player.getLocation().getX());
        ConfigManager.Data.world.set("world." + name + ".spawnlocation.y", player.getLocation().getY());
        ConfigManager.Data.world.set("world." + name + ".spawnlocation.z", player.getLocation().getZ());
        ConfigManager.Data.world.set("world." + name + ".spawnlocation.pitch", player.getLocation().getPitch());
        ConfigManager.Data.world.set("world." + name + ".spawnlocation.yaw", player.getLocation().getYaw());
        ConfigManager.Data.world.save();
        ConfigManager.Data.world.reload();
    }

    public void gotoWorldSpawn(Player player) {
        String name = player.getWorld().getName();
        ConfigManager.Data.changeWorldReference(name);
        World world = Bukkit.getWorld(Objects.requireNonNull(ConfigManager.Data.world.getString("world." + name + ".name")));
        double x = ConfigManager.Data.world.getDouble("world." + name + ".spawnlocation.x");
        double y = ConfigManager.Data.world.getDouble("world." + name + ".spawnlocation.y");
        double z = ConfigManager.Data.world.getDouble("world." + name + ".spawnlocation.z");
        float pitch = Float.parseFloat(Objects.requireNonNull(ConfigManager.Data.world.getString("world." + name + ".spawnlocation.pitch")));
        float yaw = Float.parseFloat(Objects.requireNonNull(ConfigManager.Data.world.getString("world." + name + ".spawnlocation.yaw")));
        Location loc = new Location(world, x, y, z, yaw, pitch);
        player.teleport(loc);
    }

    public void gotoWorld(CommandSender sender, Player player, String name) {
        if (Bukkit.getWorld(name) == null) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.wm_invalid_world"));
            return;
        }
        ConfigManager.Data.changeWorldReference(name);
        World world = Bukkit.getWorld(Objects.requireNonNull(ConfigManager.Data.world.getString("world." + name + ".name")));
        double x = ConfigManager.Data.world.getDouble("world." + name + ".spawnlocation.x");
        double y = ConfigManager.Data.world.getDouble("world." + name + ".spawnlocation.y");
        double z = ConfigManager.Data.world.getDouble("world." + name + ".spawnlocation.z");
        float pitch = Float.parseFloat(Objects.requireNonNull(ConfigManager.Data.world.getString("world." + name + ".spawnlocation.pitch")));
        float yaw = Float.parseFloat(Objects.requireNonNull(ConfigManager.Data.world.getString("world." + name + ".spawnlocation.yaw")));
        Location loc = new Location(world, x, y, z, yaw, pitch);
        if(player != null) {
            player.teleport(loc);
        }
    }
}
