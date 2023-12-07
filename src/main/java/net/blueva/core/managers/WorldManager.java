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

package net.blueva.core.managers;

import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
public class WorldManager {

    private Main main;

    public WorldManager(Main main) {
        this.main = main;
    }

    public void loadWorlds() throws IOException {
        File dataFolder = new File(Main.getPlugin().getDataFolder() + "/data/world/");
        File[] files = dataFolder.listFiles();

        if(files != null) {
            if(files.length == 0) {
                Bukkit.getConsoleSender().sendMessage("[BlueCore] [World Module] Importing worlds from the server to BlueCore World Module.");
                for (World world : Bukkit.getWorlds()) {
                    ConfigManager.Data.registerWorldDocument(world.getName());
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".name", world.getName());
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".alias", "&b" + world.getName().replace("_", " "));
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".build", true);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".break", true);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".pvp", true);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".fall_damage", true);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".interact", true);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".drop_items", true);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".mob_spawning", true);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".difficulty", world.getDifficulty().toString());
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".respawnWorld", "");
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".allowWeather", true);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".seed", world.getSeed());
                    if(world.getGenerator() != null) ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".generator", world.getGenerator().toString().split("\\(")[0].trim());
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".environment", world.getEnvironment().toString());
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".type", world.getWorldType().toString());
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".spawnlocation.x", 0.0);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".spawnlocation.y", 65.0);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".spawnlocation.z", 0.0);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".spawnlocation.pitch", 0.0);
                    ConfigManager.Data.getWorldDocument(world.getName()).set("world." + world.getName() + ".spawnlocation.yaw", 0.0);
                    ConfigManager.Data.getWorldDocument(world.getName()).save();
                    ConfigManager.Data.getWorldDocument(world.getName()).reload();
                    Bukkit.getConsoleSender().sendMessage("[BlueCore] [World Module] Imported world: " + world.getName());
                }
            } else {
                for(File file : files) {
                    Bukkit.getConsoleSender().sendMessage("[BlueCore] [World Module] Loading world " + file.getName());
                    WorldCreator setupworld = new WorldCreator(file.getName());
                    setupworld.createWorld();
                }
            }
        }
    }

    public void createWorld(Player player, String name, String environment, String type, String generator) throws IOException {
        if(environment == null) {
            environment = "normal";
        }
        if(type == null) {
            type = "normal";
        }

        player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.info.wm_creating_world")).replace("%worldmanager_worldname%", name));
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
            player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.error.wm_creation_error")));
        }

        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".name", name);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".alias", "&b" + name.replace("_", " "));
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".build", true);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".break", true);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".pvp", true);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".fall_damage", true);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".interact", true);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".drop_items", true);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".mob_spawning", true);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".difficulty", "NORMAL");
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".respawnWorld", "");
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".allowWeather", true);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".seed", Bukkit.getWorld(name).getSeed());
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".generator", "");
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".generator", "");
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".environment", environment.toUpperCase());
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".type", type.toUpperCase());
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".spawnlocation.x", 0.0);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".spawnlocation.y", 65.0);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".spawnlocation.z", 0.0);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".spawnlocation.pitch", 0.0);
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".spawnlocation.yaw", 0.0);
        ConfigManager.Data.getWorldDocument(name).save();
        ConfigManager.Data.getWorldDocument(name).reload();
        player.teleport(Objects.requireNonNull(Bukkit.getWorld(name)).getSpawnLocation());
        player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.success.wm_created_world")).replace("%world_name%", name));

    }

    public void deleteWorld(Player player, String name) {
        if (Bukkit.getWorld(name) == null) {
            player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.error.wm_invalid_world")));
            return;
        }
        if (!Bukkit.getWorld(name).getPlayers().isEmpty()) {
            player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.error.wm_players_in_world")));
            return;
        }
        Bukkit.unloadWorld(name, false);
    }

    public void importWorld(Player player, String name) {
        File worldfile = new File(Bukkit.getServer().getWorldContainer(), name);
        if (!worldfile.exists()) {
            player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.error.wm_invalid_world")));
            return;
        }
        if (Bukkit.getWorld(name) != null) {
            player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.error.wm_already_imported")));
            return;
        }
        Bukkit.unloadWorld(name, true);
        try {
            WorldCreator setupworld = new WorldCreator(name);
            setupworld.createWorld();
            player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.success.wm_imported_world")).replace("%world_world%", name));
            player.teleport(Bukkit.getWorld(name).getSpawnLocation());
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.error.wm_imported_error")));
        }
    }

    public void setWorldSpawn(Player player) throws IOException {
        String name = player.getWorld().getName();
        if (Bukkit.getWorld(name) == null) {
            player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.error.wm_invalid_world")));
            return;
        }
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".spawnlocation.x", player.getLocation().getX());
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".spawnlocation.y", player.getLocation().getY());
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".spawnlocation.z", player.getLocation().getZ());
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".spawnlocation.pitch", player.getLocation().getPitch());
        ConfigManager.Data.getWorldDocument(name).set("world." + name + ".spawnlocation.yaw", player.getLocation().getYaw());
        ConfigManager.Data.getWorldDocument(name).save();
        ConfigManager.Data.getWorldDocument(name).reload();
    }

    public void gotoWorldSpawn(Player player) {
        String name = player.getWorld().getName();
        World world = Bukkit.getWorld(Objects.requireNonNull(ConfigManager.Data.getWorldDocument(name).getString("world." + name + ".name")));
        double x = ConfigManager.Data.getWorldDocument(name).getDouble("world." + name + ".spawnlocation.x");
        double y = ConfigManager.Data.getWorldDocument(name).getDouble("world." + name + ".spawnlocation.y");
        double z = ConfigManager.Data.getWorldDocument(name).getDouble("world." + name + ".spawnlocation.z");
        float pitch = Float.parseFloat(Objects.requireNonNull(ConfigManager.Data.getWorldDocument(name).getString("worlds." + name + ".spawnlocation.pitch")));
        float yaw = Float.parseFloat(Objects.requireNonNull(ConfigManager.Data.getWorldDocument(name).getString("worlds." + name + ".spawnlocation.yaw")));
        Location loc = new Location(world, x, y, z, yaw, pitch);
        player.teleport(loc);
    }

    public void gotoWorld(Player player, String name) {
        if (Bukkit.getWorld(name) == null) {
            player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.error.wm_invalid_world")));
            return;
        }
        World world = Bukkit.getWorld(Objects.requireNonNull(ConfigManager.Data.getWorldDocument(name).getString("worlds." + name + ".name")));
        double x = ConfigManager.Data.getWorldDocument(name).getDouble("world." + name + ".spawnlocation.x");
        double y = ConfigManager.Data.getWorldDocument(name).getDouble("world." + name + ".spawnlocation.y");
        double z = ConfigManager.Data.getWorldDocument(name).getDouble("world." + name + ".spawnlocation.z");
        float pitch = Float.parseFloat(Objects.requireNonNull(ConfigManager.Data.getWorldDocument(name).getString("worlds." + name + ".spawnlocation.pitch")));
        float yaw = Float.parseFloat(Objects.requireNonNull(ConfigManager.Data.getWorldDocument(name).getString("worlds." + name + ".spawnlocation.yaw")));
        Location loc = new Location(world, x, y, z, yaw, pitch);
        player.teleport(loc);
    }
}
