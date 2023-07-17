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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.blueva.core.libraries.fastboard.FastBoard;
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import net.blueva.core.Main;

public class ScoreboardManager {

	private final Main main;
	private final Map<UUID, FastBoard> boards = new HashMap<>();
	int taskID;

	public ScoreboardManager(Main main) {
		this.main = main;
	}

	public void createScoreboard() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		taskID = scheduler.scheduleSyncRepeatingTask(main, () -> {
			for(Player player : Bukkit.getOnlinePlayers()) {
				if(!boards.containsKey(player.getUniqueId())) {
					FastBoard board = new FastBoard(player);
					this.boards.put(player.getUniqueId(), board);
				}
				updateScoreboard(player, boards.get(player.getUniqueId()));
			}
		}, 0L, main.configManager.getSettings().getInt("scoreboard.ticks"));
	}

	private void updateScoreboard(Player p, FastBoard board) {
		if(main.configManager.getSettings().getBoolean("scoreboard.enabled") && !main.configManager.getSettings().getStringList("scoreboard.disabled_worlds").contains(p.getWorld().getName())) {
			board.updateTitle(MessagesUtil.format(p, main.configManager.getSettings().getString("scoreboard.title")));
			List<String> lines = main.configManager.getSettings().getStringList("scoreboard.lines");
			board.updateLines(lines);
		} else {
			if(board != null) {
				BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
				scheduler.runTaskLater(main, () -> {
					board.delete();
					boards.remove(p.getUniqueId());
				}, 20L);
			}
		}
	}

}
