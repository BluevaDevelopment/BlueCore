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
 * Copyright (c) 2026 Blueva Development. All rights reserved.
 */

package net.blueva.core.listeners;

import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.configuration.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtils;

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
			event.setQuitMessage(MessagesUtils.formatLegacy(event.getPlayer(), ConfigManager.Modules.welcome.getString("welcome.broadcast.leave.message").replace("%player_name%", event.getPlayer().getDisplayName())));
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
		DataManager.Users.changeUserReference(event.getPlayer().getUniqueId().toString());
		DataManager.Users.getUser(event.getPlayer().getUniqueId()).node("logoutlocation", "world").set(world);
		DataManager.Users.getUser(event.getPlayer().getUniqueId()).node("logoutlocation", "x").set(x);
		DataManager.Users.getUser(event.getPlayer().getUniqueId()).node("logoutlocation", "y").set(y);
		DataManager.Users.getUser(event.getPlayer().getUniqueId()).node("logoutlocation", "z").set(z);
		DataManager.Users.getUser(event.getPlayer().getUniqueId()).node("logoutlocation", "yaw").set(yaw);
		DataManager.Users.getUser(event.getPlayer().getUniqueId()).node("logoutlocation", "pitch").set(pitch);
		DataManager.Users.saveUser(event.getPlayer().getUniqueId());
		DataManager.Users.reloadUser(event.getPlayer().getUniqueId());
	}


}
