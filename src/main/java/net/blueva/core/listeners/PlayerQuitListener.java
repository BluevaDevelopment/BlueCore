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

package net.blueva.core.listeners;

import net.blueva.core.configuration.ConfigManager;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

import java.io.IOException;
import java.util.Objects;

public class PlayerQuitListener implements Listener {

	private final Main main;

	public PlayerQuitListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void OPQ(PlayerQuitEvent event) throws IOException {
		if(ConfigManager.Modules.welcome.getBoolean("welcome.broadcast.leave.enabled")) {
			event.setQuitMessage(MessagesUtil.format(event.getPlayer(), ConfigManager.Modules.welcome.getString("welcome.broadcast.leave.message").replace("%player_name%", event.getPlayer().getDisplayName())));
		} else {
			event.setQuitMessage("");
		}

		Location l = event.getPlayer().getLocation();
		String world = Objects.requireNonNull(l.getWorld()).getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();
		ConfigManager.Data.getUserDocument(event.getPlayer().getUniqueId()).set("logoutlocation.world", world);
		ConfigManager.Data.getUserDocument(event.getPlayer().getUniqueId()).set("logoutlocation.x", x);
		ConfigManager.Data.getUserDocument(event.getPlayer().getUniqueId()).set("logoutlocation.y", y);
		ConfigManager.Data.getUserDocument(event.getPlayer().getUniqueId()).set("logoutlocation.z", z);
		ConfigManager.Data.getUserDocument(event.getPlayer().getUniqueId()).set("logoutlocation.yaw", yaw);
		ConfigManager.Data.getUserDocument(event.getPlayer().getUniqueId()).set("logoutlocation.pitch", pitch);
		ConfigManager.Data.getUserDocument(event.getPlayer().getUniqueId()).save();
		ConfigManager.Data.getUserDocument(event.getPlayer().getUniqueId()).reload();

	}

}
