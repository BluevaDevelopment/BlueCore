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

package net.blueva.core.listeners;

import net.blueva.core.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.blueva.core.Main;

import java.io.IOException;
import java.util.Objects;

public class PlayerDeathListener implements Listener {
	
	private final Main main;
	
	public PlayerDeathListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void OPD(PlayerDeathEvent event) throws IOException {
		Player player = (Player) event.getEntity();
		Location l = Objects.requireNonNull(event.getEntity().getPlayer()).getLocation();
		String world = Objects.requireNonNull(l.getWorld()).getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();
		ConfigManager.Data.getUserDocument(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.world", world);
		ConfigManager.Data.getUserDocument(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.x", x);
		ConfigManager.Data.getUserDocument(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.y", y);
		ConfigManager.Data.getUserDocument(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.z", z);
		ConfigManager.Data.getUserDocument(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.yaw", yaw);
		ConfigManager.Data.getUserDocument(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.pitch", pitch);
		ConfigManager.Data.getUserDocument(event.getEntity().getPlayer().getUniqueId()).save();
		ConfigManager.Data.getUserDocument(event.getEntity().getPlayer().getUniqueId()).reload();


		ConfigManager.Data.changeWorldReference(world);
		String respawnWorld = ConfigManager.Data.world.getString("world." + world + ".respawnWorld");
		if(ConfigManager.Data.world.getBoolean("world." + world + ".drop_items")) {
			if(ConfigManager.Data.world.isString("world." + world + ".respawnWorld")) {
				double xRespawn = Double.parseDouble(ConfigManager.Data.world.getString("world." + respawnWorld + ".spawnlocation.x"));
				double yRespawn = Double.parseDouble(ConfigManager.Data.world.getString("world." + respawnWorld + ".spawnlocation.y"));
				double zRespawn = Double.parseDouble(ConfigManager.Data.world.getString("world." + respawnWorld + ".spawnlocation.z"));
				float pitchRespawn = Float.parseFloat(ConfigManager.Data.world.getString("world." + respawnWorld + ".spawnlocation.pitch"));
				float yawRespawn = Float.parseFloat(ConfigManager.Data.world.getString("world." + respawnWorld + ".spawnlocation.yaw"));
				Location loc = new Location(Bukkit.getWorld(respawnWorld), xRespawn, yRespawn, zRespawn, yawRespawn, pitchRespawn);
				player.teleport(loc);
			}
		}
	}

}
