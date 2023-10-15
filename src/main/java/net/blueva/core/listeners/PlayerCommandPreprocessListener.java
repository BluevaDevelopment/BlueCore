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

import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class PlayerCommandPreprocessListener implements Listener {

	private final Main main;

	public PlayerCommandPreprocessListener(Main main) {
		this.main = main;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void OPC(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage().toLowerCase();
		for (String key : Objects.requireNonNull(main.configManager.getCommands().getConfigurationSection("commands")).getKeys(false)) {
			String command = main.configManager.getCommands().getString("commands." + key + ".command");
			List<String> worlds = main.configManager.getCommands().getStringList("commands." + key + ".worlds");
			assert command != null;
			String[] separatedCommands = command.split(" ");
			String[] separatedMessages = message.split(" ");
			if (separatedMessages.length >= separatedCommands.length) {
				boolean ContinueB = false;
				for (int i = 0; i < separatedCommands.length; i++) {
					if (!separatedCommands[i].equals(separatedMessages[i])) {
						ContinueB = true;
						break;
					}
				}
				if (ContinueB)
					continue;
				event.setCancelled(true);
				if(main.configManager.getCommands().isSet("commands." + key + ".worlds")) {
					for (String world : worlds) {
						if (player.getWorld().getName().equals(world)) {
							List<String> commands = main.configManager.getCommands().getStringList("commands." + key + ".run_commands");
							ConsoleCommandSender consoleCommandSender = Bukkit.getServer().getConsoleSender();
							for (String s : commands) {
								String commandToSend = ((String) s).replaceAll("%player%", player.getName());
								if (commandToSend.startsWith("console:")) {
									if (commandToSend.contains("msg " + player.getName())) {
										String msg = commandToSend.replace("msg " + player.getName() + " ", "").replace("console: ", "");
										player.sendMessage(MessagesUtil.format(player, msg));
									} else {
										Bukkit.dispatchCommand((CommandSender) consoleCommandSender, commandToSend.replace("console: ", ""));
									}
								} else {
									player.chat("/" + commandToSend.replace("player: ", ""));
								}
							}
							return;
						}
					}
				} else {
					List<String> commands = main.configManager.getCommands().getStringList("commands." + key + ".run_commands");
					ConsoleCommandSender consoleCommandSender = Bukkit.getServer().getConsoleSender();
					for (String s : commands) {
						String commandToSend = ((String) s).replaceAll("%player%", player.getName());
						if (commandToSend.startsWith("console:")) {
							if (commandToSend.contains("msg " + player.getName())) {
								String msg = commandToSend.replace("msg " + player.getName() + " ", "").replace("console: ", "");
								player.sendMessage(MessagesUtil.format(player, msg));
							} else {
								Bukkit.dispatchCommand((CommandSender) consoleCommandSender, commandToSend.replace("console: ", ""));
							}
						} else {
							player.chat("/" + commandToSend.replace("player: ", ""));
						}
					}
					return;
				}
			}
		}

		if(main.configManager.getSettings().getBoolean("chat.command_blocker.enabled")) {
			String[] arrayOfString;
			int j = (arrayOfString = event.getMessage().split(" ")).length;
			for (int i = 0; i < j; i++) {
				String bcmd = arrayOfString[i];
				if (main.configManager.getSettings().getStringList("chat.command_blocker.list").contains(bcmd.toLowerCase())) {
					event.setCancelled(true);
					event.getPlayer().sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_perms")));
				}
			}
		}
	}

}
