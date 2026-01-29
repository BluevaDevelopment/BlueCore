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

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.configuration.DataManager;
import net.blueva.core.modules.economy.EconomyModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.Bukkit;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtils;

public class PlayerJoinListener implements Listener {

	private final Main main;

	public PlayerJoinListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void OPJ(PlayerJoinEvent event) throws IOException {
		DataManager.Users.changeUserReference(event.getPlayer().getUniqueId().toString());
		if(Main.vaultapi) {
			if(!main.playerBank.containsKey(event.getPlayer().getUniqueId())) {
				main.playerBank.put(event.getPlayer().getUniqueId(), DataManager.Users.getUser(event.getPlayer().getUniqueId()).node("money").getDouble());
			}
		}

		if(DataManager.Users.getUser(event.getPlayer().getUniqueId()).node("logoutlocation", "world").getString() != null) {
			EconomyModule.setMoney(event.getPlayer(), ConfigManager.settings.getDouble("economy.starting_balance"), main);
		}

		if(ConfigManager.Modules.welcome.getBoolean("welcome.enabled")) {
			event.setJoinMessage("");
			if(ConfigManager.Modules.welcome.getBoolean("welcome.broadcast.join.enabled")) {
				for(Player player : Bukkit.getOnlinePlayers()) {
					main.adventure().player(player).sendMessage(MessagesUtils.format(event.getPlayer(), ConfigManager.Modules.welcome.getString("welcome.broadcast.join.message").replace("%player_name%", event.getPlayer().getDisplayName())));
				}
			}

			if(ConfigManager.Modules.welcome.getBoolean("welcome.message.enabled")) {
				List<String> description = ConfigManager.Modules.welcome.getStringList("welcome.message.list");
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> {
					for (String message : description) {
						MessagesUtils.sendToPlayer(event.getPlayer(), message);
					}
				}, ConfigManager.Modules.welcome.getInt("welcome.message.wait"));
			}

			if(ConfigManager.Modules.welcome.getBoolean("welcome.title.enabled")) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> MessagesUtils.sendTitle(event.getPlayer(), ConfigManager.Modules.welcome.getString("welcome.title.title"), ConfigManager.Modules.welcome.getString("welcome.title.subtitle"), ConfigManager.Modules.welcome.getInt("welcome.title.fade_in"), ConfigManager.Modules.welcome.getInt("welcome.title.stay"), ConfigManager.Modules.welcome.getInt("welcome.title.fade_out")));
			}

			if(ConfigManager.Modules.welcome.getBoolean("welcome.actionbar.enabled")) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> MessagesUtils.sendActionBar(event.getPlayer(), ConfigManager.Modules.welcome.getString("welcome.actionbar.message")), ConfigManager.Modules.welcome.getInt("welcome.actionbar.wait"));
			}

			if(ConfigManager.Modules.welcome.getBoolean("welcome.broadcast.first_join.enabled")) {
				if(DataManager.Users.getUser(event.getPlayer().getUniqueId()).getString("logoutlocation.world") != null) {
					MessagesUtils.broadcast(Objects.requireNonNull(ConfigManager.Modules.welcome.getString("welcome.broadcast.first_join.message")).replace("%player_name%", event.getPlayer().getName()));
				}
			}
		}

		if(event.getPlayer().hasPermission("bluecore.fly.safelogin")) {
			event.getPlayer().setAllowFlight(true);
			event.getPlayer().setFlying(true);
		}
	}

}
