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

package net.blueva.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.blueva.core.Main;

import java.util.Objects;

public class PlayerDeathListener implements Listener {
	
	private final Main main;
	
	public PlayerDeathListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void OPD(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
		Location l = Objects.requireNonNull(event.getEntity().getPlayer()).getLocation();
		String world = Objects.requireNonNull(l.getWorld()).getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.world", world);
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.x", x);
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.y", y);
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.z", z);
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.yaw", yaw);
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.pitch", pitch);
		main.configManager.saveUser(event.getEntity().getPlayer().getUniqueId());


		String respawnWorld = main.configManager.getWorlds().getString("worlds." + world + ".respawnWorld");
		if(world.equals(main.configManager.getWorlds().getStringList("worlds")) && main.configManager.getWorlds().getBoolean("worlds." + world + ".drop_items")) {
			if(main.configManager.getWorlds().isSet("worlds." + world + ".respawnWorld")) {
				double xRespawn = Double.parseDouble(main.configManager.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.x"));
				double yRespawn = Double.parseDouble(main.configManager.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.y"));
				double zRespawn = Double.parseDouble(main.configManager.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.z"));
				float pitchRespawn = Float.parseFloat(main.configManager.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.pitch"));
				float yawRespawn = Float.parseFloat(main.configManager.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.yaw"));
				Location loc = new Location(Bukkit.getWorld(respawnWorld), xRespawn, yRespawn, zRespawn, yawRespawn, pitchRespawn);
				player.teleport(loc);
			}
		}
	}

}
