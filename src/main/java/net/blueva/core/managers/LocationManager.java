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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import net.blueva.core.Main;

public class LocationManager {
	
	private Main main;
	int taskID;
	
	public LocationManager(Main main) {
		this.main = main;
	}
	
	public void createLastLocation() {
		BukkitScheduler schedule = Bukkit.getServer().getScheduler();
		taskID = schedule.scheduleSyncRepeatingTask(main, new Runnable() {
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()) {
					updateLocation(player);
				}
			}
			}, 0, 1200); //1200 Ticks = 60 Seconds (It will be possible to customize it in the settings.yml in the future)
}

	private void updateLocation(Player p) {
		Location l = p.getLocation();
		String world = l.getWorld().getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();
		main.configManager.getUser(p.getUniqueId()).set("lastlocation.world", world);
		main.configManager.saveUser(p.getUniqueId());
		main.configManager.getUser(p.getUniqueId()).set("lastlocation.x", x);
		main.configManager.saveUser(p.getUniqueId());
		main.configManager.getUser(p.getUniqueId()).set("lastlocation.y", y);
		main.configManager.saveUser(p.getUniqueId());
		main.configManager.getUser(p.getUniqueId()).set("lastlocation.z", z);
		main.configManager.saveUser(p.getUniqueId());
		main.configManager.getUser(p.getUniqueId()).set("lastlocation.yaw", yaw);
		main.configManager.saveUser(p.getUniqueId());
		main.configManager.getUser(p.getUniqueId()).set("lastlocation.pitch", pitch);
		main.configManager.saveUser(p.getUniqueId());
		main.configManager.reloadUser(p.getUniqueId());
	}

}
