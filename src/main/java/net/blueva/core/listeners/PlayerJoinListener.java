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

import java.util.List;
import java.util.Objects;

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
	public void OPJ(PlayerJoinEvent event) {
		main.configManager.registerUser(event.getPlayer().getUniqueId());

		if(Main.vaultapi) {
			if(!main.playerBank.containsKey(event.getPlayer().getUniqueId())) {
				main.playerBank.put(event.getPlayer().getUniqueId(), main.configManager.getUser(event.getPlayer().getUniqueId()).getDouble("money"));
			}
		}

		if(!main.configManager.getUser(event.getPlayer().getUniqueId()).isSet("logoutlocation.world")) {
			EconomyManager.setMoney(event.getPlayer(), main.configManager.getSettings().getDouble("economy.starting_balance"), main);
		}

		if(main.configManager.getSettings().getBoolean("welcome.broadcast.join.enabled")) {
			event.setJoinMessage(MessagesUtil.format(event.getPlayer(), main.configManager.getSettings().getString("welcome.broadcast.join.message")).replace("%player_name%", event.getPlayer().getDisplayName()));
		} else {
			event.setJoinMessage("");
		}

		if(main.configManager.getSettings().getBoolean("welcome.message.enabled")) {
			List<String> description = main.configManager.getSettings().getStringList("welcome.message.list");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> {
				for (String message : description) {
					event.getPlayer().sendMessage(MessagesUtil.format(event.getPlayer(), message));
				}
			}, main.configManager.getSettings().getInt("welcome.message.wait"));
		}

		if(main.configManager.getSettings().getBoolean("welcome.title.enabled")) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> event.getPlayer().sendTitle(MessagesUtil.format(event.getPlayer(), main.configManager.getSettings().getString("welcome.title.title")), MessagesUtil.format(event.getPlayer(), main.configManager.getSettings().getString("welcome.title.subtitle")), main.configManager.getSettings().getInt("welcome.title.fade_in"), main.configManager.getSettings().getInt("welcome.title.stay"), main.configManager.getSettings().getInt("welcome.title.fade_out")), main.configManager.getSettings().getInt("welcome.title.wait"));

		}

		if(main.configManager.getSettings().getBoolean("welcome.actionbar.enabled")) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> {
				TextComponent text_component = new TextComponent(MessagesUtil.format(event.getPlayer(), main.configManager.getSettings().getString("welcome.actionbar.message")));
				event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, text_component);
			}, main.configManager.getSettings().getInt("welcome.actionbar.wait"));
		}

		if(main.configManager.getSettings().getBoolean("welcome.broadcast.first_join.enabled")) {
			if(!main.configManager.getUser(event.getPlayer().getUniqueId()).isSet("logoutlocation.world")) {
				Bukkit.broadcastMessage(MessagesUtil.format(event.getPlayer(), Objects.requireNonNull(main.configManager.getSettings().getString("welcome.broadcast.first_join.message")).replace("%player_name%", event.getPlayer().getName())));
			}
		}

		if(event.getPlayer().hasPermission("bluecore.fly.safelogin")) {
			event.getPlayer().setAllowFlight(true);
			event.getPlayer().setFlying(true);
		}
	}

}