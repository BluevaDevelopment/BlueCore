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

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

import java.util.Objects;

public class PlayerQuitListener implements Listener {

	private final Main main;

	public PlayerQuitListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void OPQ(PlayerQuitEvent event) {
		if(main.configManager.getSettings().getBoolean("welcome.broadcast.leave.enabled")) {
			event.setQuitMessage(MessagesUtil.format(event.getPlayer(), main.configManager.getSettings().getString("welcome.broadcast.leave.message").replace("%player_name%", event.getPlayer().getDisplayName())));
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
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.world", world);
		main.configManager.saveUser(event.getPlayer().getUniqueId());
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.x", x);
		main.configManager.saveUser(event.getPlayer().getUniqueId());
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.y", y);
		main.configManager.saveUser(event.getPlayer().getUniqueId());
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.z", z);
		main.configManager.saveUser(event.getPlayer().getUniqueId());
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.yaw", yaw);
		main.configManager.saveUser(event.getPlayer().getUniqueId());
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.pitch", pitch);
		main.configManager.saveUser(event.getPlayer().getUniqueId());
		main.configManager.reloadUser(event.getPlayer().getUniqueId());

	}

}
