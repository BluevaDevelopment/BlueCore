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
package net.blueva.core.utils;

import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.IOException;
import java.util.Objects;

public class LocationUtils {
    private final Main main;
    int taskID;

    public LocationUtils(Main main) {
        this.main = main;
    }

    public void createLastLocation() {
        BukkitScheduler schedule = Bukkit.getServer().getScheduler();
        taskID = schedule.scheduleSyncRepeatingTask(main, () -> {
            for(Player player : Bukkit.getOnlinePlayers()) {
                try {
                    updateLocation(player);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 1200); //1200 Ticks = 60 Seconds (It will be possible to customize it in the settings.yml in the future)
    }

    private void updateLocation(Player p) throws IOException {
        Location l = p.getLocation();
        String world = Objects.requireNonNull(l.getWorld()).getName();
        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();
        float yaw = l.getYaw();
        float pitch = l.getPitch();
        ConfigManager.Data.getUserDocument(p.getUniqueId()).set("lastlocation.world", world);
        ConfigManager.Data.getUserDocument(p.getUniqueId()).set("lastlocation.x", x);
        ConfigManager.Data.getUserDocument(p.getUniqueId()).set("lastlocation.y", y);
        ConfigManager.Data.getUserDocument(p.getUniqueId()).set("lastlocation.z", z);
        ConfigManager.Data.getUserDocument(p.getUniqueId()).set("lastlocation.yaw", yaw);
        ConfigManager.Data.getUserDocument(p.getUniqueId()).set("lastlocation.pitch", pitch);
        ConfigManager.Data.getUserDocument(p.getUniqueId()).save();
        ConfigManager.Data.getUserDocument(p.getUniqueId()).reload();
    }
}
