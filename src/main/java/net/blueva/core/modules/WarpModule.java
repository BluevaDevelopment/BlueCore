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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class WarpModule {
    public static boolean setWarp(String warp, Player player) throws IOException {
        File warpFile = new File(Main.getPlugin().getDataFolder()+"/data/modules/warps/"+warp+".yml");
        if(warpFile.exists()) {
            MessagesUtils.sendToPlayer(player, ConfigManager.language.getString("messages.error.warp_already_set"));
            return false;
        }

        Location l = player.getLocation();
        String world = Objects.requireNonNull(l.getWorld()).getName();
        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();
        float yaw = l.getYaw();
        float pitch = l.getPitch();
        DataManager.Modules.Warps.get(warp).node("warp", warp, "world").set(world);
        DataManager.Modules.Warps.get(warp).node("warp." + warp + ".x").set(x);
        DataManager.Modules.Warps.get(warp).node("warp." + warp + ".y").set(y);
        DataManager.Modules.Warps.get(warp).node("warp." + warp + ".z").set(z);
        DataManager.Modules.Warps.get(warp).node("warp." + warp + ".yaw").set(yaw);
        DataManager.Modules.Warps.get(warp).node("warp." + warp + ".pitch").set(pitch);
        DataManager.Modules.Warps.save(warp);
        return true;
    }

    public static boolean updateWarp (String warp, Player player) throws IOException {
        File warpFile = new File(Main.getPlugin().getDataFolder()+"/data/modules/warps/"+warp+".yml");
        if(!warpFile.exists()) {
            MessagesUtils.sendToPlayer(player, ConfigManager.language.getString("messages.error.unknown_warp"));
            return false;
        }

        Location l = player.getLocation();
        String world = Objects.requireNonNull(l.getWorld()).getName();
        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();
        float yaw = l.getYaw();
        float pitch = l.getPitch();
        DataManager.Modules.Warps.get(warp).node("warp", warp, "world").set(world);
        DataManager.Modules.Warps.get(warp).node("warp." + warp + ".x").set(x);
        DataManager.Modules.Warps.get(warp).node("warp." + warp + ".y").set(y);
        DataManager.Modules.Warps.get(warp).node("warp." + warp + ".z").set(z);
        DataManager.Modules.Warps.get(warp).node("warp." + warp + ".yaw").set(yaw);
        DataManager.Modules.Warps.get(warp).node("warp." + warp + ".pitch").set(pitch);
        DataManager.Modules.Warps.save(warp);
        return true;
    }

    public static void deleteWarp(String warp, Player player) {
        File warpFile = new File(Main.getPlugin().getDataFolder()+"/data/modules/warps/"+warp+".yml");
        if(warpFile.exists()) {
            if(warpFile.delete()) {
                MessagesUtils.sendToPlayer(player, Objects.requireNonNull(ConfigManager.language.getString("messages.success.warp_deleted")).replace("%warp%", warp));
            }
        } else {
            MessagesUtils.sendToPlayer(player, ConfigManager.language.getString("messages.error.unknown_warp"));
        }
    }

    public static boolean teleportPlayer(Player player, String warp) {
        if (!DataManager.Modules.Warps.get(warp).node("warp", warp, "world").isNull()) {
                String world = DataManager.Modules.Warps.get(warp).node("warp", warp, "world").getString();
            double x = Double.parseDouble(Objects.requireNonNull(DataManager.Modules.Warps.get(warp).node("warp", warp, "x").getString()));
            double y = Double.parseDouble(Objects.requireNonNull(DataManager.Modules.Warps.get(warp).node("warp", warp + "y").getString()));
            double z = Double.parseDouble(Objects.requireNonNull(DataManager.Modules.Warps.get(warp).node("warp", warp + "z").getString()));
            float yaw = Float.parseFloat(Objects.requireNonNull(DataManager.Modules.Warps.get(warp).node("warp", warp + "yaw").getString()));
            float pitch = Float.parseFloat(Objects.requireNonNull(DataManager.Modules.Warps.get(warp).node("warp", warp + "pitch").getString()));
            Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
            player.teleport(loc);
            return true;
        }
        return false;
    }

    public static boolean teleportSpawn(Player target) {
        String spawn = ConfigManager.Modules.warps.getString("warps.spawn");
        File warpFile = new File(Main.getPlugin().getDataFolder()+"/data/modules/warps/"+spawn+".yml");
        if (warpFile.exists()) {
            teleportPlayer(target, spawn);
            return true;
        }
        return false;
    }
}
