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

import java.util.Objects;
import java.util.Set;

import net.blueva.core.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class AsyncPlayerChatListener implements Listener {
	
	private final Main main;
	
	public AsyncPlayerChatListener(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void OPCA(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		if(ConfigManager.Modules.chat.getBoolean("chat.antiswear.enabled")) {
			if(!player.hasPermission("bluecore.chat.antiswear.bypass")) {
				for(String blockedWords : ConfigManager.Modules.chat.getStringList("chat.antiswear.list")) {
					if(message.toLowerCase().replaceAll("[-_*. ]", "").contains(blockedWords.toLowerCase())) {
						if(Objects.equals(ConfigManager.Modules.chat.getString("chat.antiswear.mode"), "replace")) {
							StringBuilder a = new StringBuilder();
							for(int c=0;c<blockedWords.length();c++) {
								a.append("*");
							}
							message = message.replace(blockedWords, a.toString());
						}
						if(Objects.equals(ConfigManager.Modules.chat.getString("chat.antiswear.mode"), "block")) {
							player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.info.antiswear_block")));
							event.setCancelled(true);
							return;
						}
						
					}
				}
				event.setMessage(message);
			}
		}
		if(!Objects.equals(ConfigManager.Modules.chat.getString("chat.format"), "none")) {
			String formated_message = Objects.requireNonNull(ConfigManager.Modules.chat.getString("chat.format")).replaceFirst("%player_displayname%", player.getDisplayName()).replaceFirst("%message%", message);
			event.setFormat(MessagesUtil.format(player, formated_message));
		}
		if(ConfigManager.Modules.chat.getBoolean("chat.per_world")) {
			Set<Player> r = event.getRecipients();
		    for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
		      if (!pls.getWorld().getName().equals(player.getWorld().getName()))
		        r.remove(pls); 
		    } 
		}
	}
}
