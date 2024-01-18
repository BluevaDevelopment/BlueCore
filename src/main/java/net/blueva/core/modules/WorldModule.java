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
 * Copyright (c) 2024 Blueva Development. All rights reserved.
 */

package net.blueva.core.modules;

import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.configuration.DataManager;
import net.blueva.core.utils.MessagesUtils;
import net.kyori.adventure.audience.Audience;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
public class WorldModule {

    private final Main main;

    public WorldModule(Main main) {
        this.main = main;
    }

    private void registerWorldData(World world, String environment, String type) throws SerializationException {
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "name").set(world.getName());
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "alias").set("<aqua>" + world.getName().replace("_", " "));
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "build").set(ConfigManager.Modules.worlds.getString("worlds.defaults.build"));
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "break").set(ConfigManager.Modules.worlds.getString("worlds.defaults.break"));
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "pvp").set(ConfigManager.Modules.worlds.getString("worlds.defaults.pvp"));
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "fall_damage").set(ConfigManager.Modules.worlds.getString("worlds.defaults.fall_damage"));
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "interact").set(ConfigManager.Modules.worlds.getString("worlds.defaults.interact"));
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "drop_items").set(ConfigManager.Modules.worlds.getString("worlds.defaults.drop_items"));
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "mob_spawning").set(ConfigManager.Modules.worlds.getString("worlds.defaults.mob_spawning"));
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "difficulty").set(ConfigManager.Modules.worlds.getString("worlds.defaults.difficulty").toUpperCase());
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "respawn_world").set(ConfigManager.Modules.worlds.getString("worlds.defaults.respawn_world"));
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "allow_weather").set(ConfigManager.Modules.worlds.getString("worlds.defaults.allow_weather"));
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "seed").set(world.getSeed());
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "seed").set(world.getSeed());
        if(world.getGenerator() != null) DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "generator").set(world.getGenerator().toString().split("\\(")[0].trim());
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "environment").set(environment);
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "type").set(type);
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "spawn_location", "x").set(world.getSpawnLocation().getX());
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "spawn_location", "y").set(world.getSpawnLocation().getY());
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "spawn_location", "z").set(world.getSpawnLocation().getZ());
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "spawn_location", "pitch").set(world.getSpawnLocation().getPitch());
        DataManager.Modules.Worlds.get(world.getName()).node("world", world.getName(), "spawn_location", "yaw").set(world.getSpawnLocation().getYaw());
        DataManager.Modules.Worlds.save(world.getName());
        DataManager.Modules.Worlds.reload(world.getName());
    }

    public void loadWorlds() throws IOException {
        File dataFolder = new File(Main.getPlugin().getDataFolder() + "/data/modules/worlds/");
        File[] files = dataFolder.listFiles();

        if(files != null) {
            if(files.length == 0) {
                Bukkit.getConsoleSender().sendMessage("[BlueCore] [World Module] Importing worlds from the server to BlueCore World Module.");
                for (World world : Bukkit.getWorlds()) {
                    registerWorldData(world, world.getEnvironment().name(), world.getWorldType().name());
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

        World world = Bukkit.getWorld(name);
        if(world != null) {
            registerWorldData(world, environment, type);
        }

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
        DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "x").set(player.getLocation().getX());
        DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "y").set(player.getLocation().getY());
        DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "z").set(player.getLocation().getZ());
        DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "pitch").set(player.getLocation().getPitch());
        DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "yaw").set(player.getLocation().getYaw());
        DataManager.Modules.Worlds.save(name);
        DataManager.Modules.Worlds.reload(name);
    }

    public void gotoWorldSpawn(Player player) {
        String name = player.getWorld().getName();
        World world = Bukkit.getWorld(Objects.requireNonNull(DataManager.Modules.Worlds.get(name).node("world", name, "name").getString()));
        double x = DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "x").getDouble();
        double y = DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "y").getDouble();
        double z = DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "z").getDouble();
        float pitch = DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "pitch").getFloat();
        float yaw = DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "yaw").getFloat();
        Location loc = new Location(world, x, y, z, yaw, pitch);
        player.teleport(loc);
    }

    public void gotoWorld(CommandSender sender, Player player, String name) {
        if (Bukkit.getWorld(name) == null) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.wm_invalid_world"));
            return;
        }
        World world = Bukkit.getWorld(Objects.requireNonNull(DataManager.Modules.Worlds.get(name).node("world", name, "name").getString()));
        double x = DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "x").getDouble();
        double y = DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "y").getDouble();
        double z = DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "z").getDouble();
        float pitch = DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "pitch").getFloat();
        float yaw = DataManager.Modules.Worlds.get(name).node("world", name, "spawn_location", "yaw").getFloat();
        Location loc = new Location(world, x, y, z, yaw, pitch);
        if(player != null) {
            player.teleport(loc);
        }
    }
}
