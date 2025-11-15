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
 * Copyright (c) 2025 Blueva Development. All rights reserved.
 */

package net.blueva.core.modules.scoreboard;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import fr.mrmicky.fastboard.adventure.FastBoard;
import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardModule {

	private final Main main;

	public ScoreboardModule(Main main) {
		this.main = main;
	}

	private final Map<UUID, FastBoard> playerBoards = new HashMap<>();
	private final Map<UUID, ScoreboardData> playerScoreboardData = new ConcurrentHashMap<>();
	private final Map<Object, ScoreboardData> scoreboards = new HashMap<>();

	public void loadScoreboards() {
		if (ConfigManager.Modules.scoreboards.getBoolean("scoreboard.enabled")) {
			Section scoreboardSection = ConfigManager.Modules.scoreboards.getSection("scoreboard.scoreboards");

			for (Object scoreboardKey : scoreboardSection.getKeys()) {
				Section scoreboardData = scoreboardSection.getSection(scoreboardKey.toString());
				ScoreboardData data = new ScoreboardData(scoreboardData);
				scoreboards.put(scoreboardKey, data);
			}
		}
	}

	public void updatePlayerScoreboards() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			updatePlayerScoreboard(player);
		}
	}

	private void updatePlayerScoreboard(Player player) {
		UUID playerId = player.getUniqueId();

		if (playerBoards.containsKey(playerId)) {
			ScoreboardData currentData = getHighestPriorityValidScoreboard(player);

			if (currentData != null ) {
				updateScoreboard(player, currentData);
			} else {
				removePlayerScoreboard(player);
			}
		} else {
			ScoreboardData newData = getHighestPriorityValidScoreboard(player);
			if (newData != null) {
				addPlayerScoreboard(player, newData);
			}
		}
	}

	private void addPlayerScoreboard(Player player, ScoreboardData data) {
		FastBoard board = new FastBoard(player);
		playerBoards.put(player.getUniqueId(), board);
		playerScoreboardData.put(player.getUniqueId(), data);
		data.updateBoard(board, player);
	}

	private void removePlayerScoreboard(Player player) {
		UUID playerId = player.getUniqueId();
		playerBoards.remove(playerId);
		playerScoreboardData.remove(playerId);
	}

	private void updateScoreboard(Player player, ScoreboardData data) {
		FastBoard board = playerBoards.get(player.getUniqueId());
		playerScoreboardData.put(player.getUniqueId(), data);
		data.updateBoard(board, player);
	}

	private ScoreboardData getHighestPriorityValidScoreboard(Player player) {
		String worldCondition = player.getWorld().getName();
		ScoreboardData highestPriorityValid = null;

		for (ScoreboardData data : scoreboards.values()) {
			if (data.shouldDisplay(worldCondition) && (highestPriorityValid == null || data.getPriority() > highestPriorityValid.getPriority())) {
				highestPriorityValid = data;
			}
		}

		return highestPriorityValid;
	}
}
