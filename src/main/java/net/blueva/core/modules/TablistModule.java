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

package net.blueva.core.modules;

import java.util.ArrayList;

import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.utils.MessagesUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import net.blueva.core.Main;
import net.blueva.core.utils.StringUtils;

public class TablistModule {

	private final Main main;
	int taskID;

	public TablistModule(Main main) {
		this.main = main;
	}

	public void createTablist() {
		BukkitScheduler schedule = Bukkit.getServer().getScheduler();
		taskID = schedule.scheduleSyncRepeatingTask(main, () -> {
			for(Player player : Bukkit.getOnlinePlayers()) {
				updateTablist(player);
			}
		}, 0, ConfigManager.Modules.tablist.getInt("tablist.tablist1.ticks"));
	}

	private void updateTablist(Player p) {
		if(ConfigManager.Modules.tablist.getBoolean("tablist.tablist1.enabled")) {
			p.setPlayerListHeader(MessagesUtils.format(p, StringUtils.listToString((ArrayList<String>) ConfigManager.settings.getStringList("tablist.tablist1.header"), "\n")));
			p.setPlayerListFooter(MessagesUtils.format(p, StringUtils.listToString((ArrayList<String>) ConfigManager.settings.getStringList("tablist.tablist1.footer"), "\n")).replace("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size())));
		}
	}


}
