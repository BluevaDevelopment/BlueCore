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

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.managers.EconomyManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class PlayerJoinListener implements Listener {

	private final Main main;

	public PlayerJoinListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void OPJ(PlayerJoinEvent event) throws IOException {
		ConfigManager.Data.registerUserDocument(event.getPlayer().getUniqueId());

		if(Main.vaultapi) {
			if(!main.playerBank.containsKey(event.getPlayer().getUniqueId())) {
				main.playerBank.put(event.getPlayer().getUniqueId(), ConfigManager.Data.getUserDocument(event.getPlayer().getUniqueId()).getDouble("money"));
			}
		}

		if(ConfigManager.Data.getUserDocument(event.getPlayer().getUniqueId()).get("logoutlocation.world") != null) {
			EconomyManager.setMoney(event.getPlayer(), ConfigManager.settings.getDouble("economy.starting_balance"), main);
		}

		if(ConfigManager.Modules.welcome.getBoolean("welcome.enabled")) {
			if(ConfigManager.Modules.welcome.getBoolean("welcome.broadcast.join.enabled")) {
				event.setJoinMessage(MessagesUtil.format(event.getPlayer(), ConfigManager.Modules.welcome.getString("welcome.broadcast.join.message")).replace("%player_name%", event.getPlayer().getDisplayName()));
			} else {
				event.setJoinMessage("");
			}

			if(ConfigManager.Modules.welcome.getBoolean("welcome.message.enabled")) {
				List<String> description = ConfigManager.Modules.welcome.getStringList("welcome.message.list");
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> {
					for (String message : description) {
						event.getPlayer().sendMessage(MessagesUtil.format(event.getPlayer(), message));
					}
				}, ConfigManager.Modules.welcome.getInt("welcome.message.wait"));
			}

			if(ConfigManager.Modules.welcome.getBoolean("welcome.title.enabled")) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> event.getPlayer().sendTitle(MessagesUtil.format(event.getPlayer(), ConfigManager.Modules.welcome.getString("welcome.title.title")), MessagesUtil.format(event.getPlayer(), ConfigManager.Modules.welcome.getString("welcome.title.subtitle")), ConfigManager.Modules.welcome.getInt("welcome.title.fade_in"), ConfigManager.Modules.welcome.getInt("welcome.title.stay"), ConfigManager.Modules.welcome.getInt("welcome.title.fade_out")), ConfigManager.Modules.welcome.getInt("welcome.title.wait"));

			}

			if(ConfigManager.Modules.welcome.getBoolean("welcome.actionbar.enabled")) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> {
					TextComponent text_component = new TextComponent(MessagesUtil.format(event.getPlayer(), ConfigManager.Modules.welcome.getString("welcome.actionbar.message")));
					event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, text_component);
				}, ConfigManager.Modules.welcome.getInt("welcome.actionbar.wait"));
			}

			if(ConfigManager.Modules.welcome.getBoolean("welcome.broadcast.first_join.enabled")) {
				if(ConfigManager.Data.getUserDocument(event.getPlayer().getUniqueId()).getString("logoutlocation.world") != null) {
					Bukkit.broadcastMessage(MessagesUtil.format(event.getPlayer(), Objects.requireNonNull(ConfigManager.Modules.welcome.getString("welcome.broadcast.first_join.message")).replace("%player_name%", event.getPlayer().getName())));
				}
			}
		}

		if(event.getPlayer().hasPermission("bluecore.fly.safelogin")) {
			event.getPlayer().setAllowFlight(true);
			event.getPlayer().setFlying(true);
		}
	}

}