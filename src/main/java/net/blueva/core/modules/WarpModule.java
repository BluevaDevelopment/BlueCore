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

        ConfigManager.Data.changeWarpReference(warp);
        Location l = player.getLocation();
        String world = Objects.requireNonNull(l.getWorld()).getName();
        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();
        float yaw = l.getYaw();
        float pitch = l.getPitch();
        ConfigManager.Data.warp.set("warp." + warp + ".world", world);
        ConfigManager.Data.warp.set("warp." + warp + ".x", x);
        ConfigManager.Data.warp.set("warp." + warp + ".y", y);
        ConfigManager.Data.warp.set("warp." + warp + ".z", z);
        ConfigManager.Data.warp.set("warp." + warp + ".yaw", yaw);
        ConfigManager.Data.warp.set("warp." + warp + ".pitch", pitch);
        ConfigManager.Data.warp.save();
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
        ConfigManager.Data.warp.set("warp." + warp + ".world", world);
        ConfigManager.Data.warp.set("warp." + warp + ".x", x);
        ConfigManager.Data.warp.set("warp." + warp + ".y", y);
        ConfigManager.Data.warp.set("warp." + warp + ".z", z);
        ConfigManager.Data.warp.set("warp." + warp + ".yaw", yaw);
        ConfigManager.Data.warp.set("warp." + warp + ".pitch", pitch);
        ConfigManager.Data.warp.save();
        return true;
    }

    public static void deleteWarp(String warp, Player player) {
        File warpFile = new File(Main.getPlugin().getDataFolder()+"/data/modules/warps/"+warp+".yml");
        if(warpFile.exists()) {
            ConfigManager.Data.changeWarpReference(warp);
            if(warpFile.delete()) {
                MessagesUtils.sendToPlayer(player, Objects.requireNonNull(ConfigManager.language.getString("messages.success.warp_deleted")).replace("%warp%", warp));
            }
        } else {
            MessagesUtils.sendToPlayer(player, ConfigManager.language.getString("messages.error.unknown_warp"));
        }
    }

    public static boolean teleportPlayer(Player player, String warp) {
        ConfigManager.Data.changeWarpReference(warp);
        if (ConfigManager.Data.warp.isString("warp." + warp + ".world")) {
            String world = ConfigManager.Data.warp.getString("warp." + warp + ".world");
            double x = Double.parseDouble(Objects.requireNonNull(ConfigManager.Data.warp.getString("warp." + warp + ".x")));
            double y = Double.parseDouble(Objects.requireNonNull(ConfigManager.Data.warp.getString("warp." + warp + ".y")));
            double z = Double.parseDouble(Objects.requireNonNull(ConfigManager.Data.warp.getString("warp." + warp + ".z")));
            float yaw = Float.parseFloat(Objects.requireNonNull(ConfigManager.Data.warp.getString("warp." + warp + ".yaw")));
            float pitch = Float.parseFloat(Objects.requireNonNull(ConfigManager.Data.warp.getString("warp." + warp + ".pitch")));
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
