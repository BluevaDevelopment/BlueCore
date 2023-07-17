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

package net.blueva.core.managers;

import java.util.ArrayList;

import net.blueva.core.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import net.blueva.core.Main;
import net.blueva.core.utils.StringUtil;

public class TablistManager {

	private Main main;
	int taskID;

	public TablistManager(Main main) {
		this.main = main;
	}

	public void createTablist() {
		BukkitScheduler schedule = Bukkit.getServer().getScheduler();
		taskID = schedule.scheduleSyncRepeatingTask(main, new Runnable() {
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()) {
					updateTablist(player);
				}
			}
		}, 0, Integer.valueOf(main.configManager.getSettings().getInt("tablist.ticks")));
	}

	private void updateTablist(Player p) {
		p.setPlayerListHeader(MessagesUtil.format(p, StringUtil.listToString((ArrayList<String>) main.configManager.getSettings().getStringList("tablist.header"), "\n")));
		p.setPlayerListFooter(MessagesUtil.format(p, StringUtil.listToString((ArrayList<String>) main.configManager.getSettings().getStringList("tablist.footer"), "\n")).replace("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size())));
	}


}
