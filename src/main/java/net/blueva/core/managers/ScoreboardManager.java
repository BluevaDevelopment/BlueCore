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

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import net.blueva.core.libraries.fastboard.FastBoard;
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import net.blueva.core.Main;

public class ScoreboardManager {

	private final Main main;
	private final Map<UUID, FastBoard> boards = new HashMap<>();
	private final Map<String, Integer> taskIDs = new HashMap<>();
	private final Map<Player, Boolean> playerWithBoard = new HashMap<>();

	public ScoreboardManager(Main main) {
		this.main = main;
	}

	public void createScoreboard() {
		for (String scoreboard : Objects.requireNonNull(main.configManager.getScoreboards().getConfigurationSection("scoreboards")).getKeys(false)) {
			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
			int taskID = scheduler.scheduleSyncRepeatingTask(main, () -> {
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (!boards.containsKey(player.getUniqueId())) {
						FastBoard board = new FastBoard(player);
						this.boards.put(player.getUniqueId(), board);
					}
					updateScoreboards(player, boards.get(player.getUniqueId()), scoreboard);

					String scoreboardPath = "scoreboards." + scoreboard;
					if(shouldDisplayScoreboard(player, scoreboardPath) && scoreboardHasPriority(scoreboardPath) && playerWithBoard.containsKey(player)) {
						playerWithBoard.replace(player, true);
					} else {
						playerWithBoard.replace(player, false);
					}
				}
			}, 0L, main.configManager.getScoreboards().getInt("scoreboards." + scoreboard + ".ticks"));
			taskIDs.put(scoreboard, taskID);
		}
	}

	private void updateScoreboards(Player player, FastBoard board, String scoreboard) {
		String scoreboardPath = "scoreboards." + scoreboard;
		if (shouldDisplayScoreboard(player, scoreboardPath) && scoreboardHasPriority(scoreboardPath)) {
			String title = MessagesUtil.format(player, main.configManager.getScoreboards().getString(scoreboardPath + ".title"));
			board.updateTitle(title);
			List<String> lines = main.configManager.getScoreboards().getStringList(scoreboardPath + ".lines");
			List<String> formattedLines = formatLines(player, lines);
			board.updateLines(formattedLines);
		} else if(!checkPlayer(player)) {
			if(board != null) {
				BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
				scheduler.runTaskLater(main, () -> {
					board.delete();
					boards.remove(player.getUniqueId());
				}, 20L);
			}
		}
	}

	private boolean checkPlayer(Player player) {
		playerWithBoard.putIfAbsent(player, false);
		return playerWithBoard.get(player);
	}

	private boolean shouldDisplayScoreboard(Player player, String scoreboardPath) {
		String displayCondition = MessagesUtil.format(player, main.configManager.getScoreboards().getString(scoreboardPath + ".display_condition"));
		return displayCondition.equals("*") || evaluateCondition(displayCondition, player);
	}

	private boolean evaluateCondition(String condition, Player player) {
		String[] parts = condition.split(" ");

		String operand1 = parts[0];
		String operator = parts[1];
		String operand2 = parts[2];

		return compareVariables(operator, operand1, operand2, player);
	}

	private boolean compareVariables(String operator, String operand1, String operand2, Player player) {
		operand1 = MessagesUtil.format(player, operand1);
		operand2 = MessagesUtil.format(player, operand2);

		switch (operator) {
			case "=":
				return operand1.equals(operand2);
			case "!=":
				return !operand1.equals(operand2);
			case "<":
				return Double.parseDouble(operand1) < Double.parseDouble(operand2);
			case ">":
				return Double.parseDouble(operand1) > Double.parseDouble(operand2);
			case "<=":
				return Double.parseDouble(operand1) <= Double.parseDouble(operand2);
			case ">=":
				return Double.parseDouble(operand1) >= Double.parseDouble(operand2);
			default:
				return false;
		}
	}

	private boolean scoreboardHasPriority(String scoreboardPath) {
		boolean bool = false;

		int priority = main.configManager.getScoreboards().getInt(scoreboardPath + ".priority");
		for (String scoreboard : Objects.requireNonNull(main.configManager.getScoreboards().getConfigurationSection("scoreboards")).getKeys(false)) {
			if(main.configManager.getScoreboards().getInt("scoreboards."+scoreboard+".priority") <= priority) {
				bool = true;
			}
		}

		return bool;
	}

	private List<String> formatLines(Player player, List<String> lines) {
		List<String> formattedLines = new ArrayList<>();
		for (String line : lines) {
			String formattedLine = MessagesUtil.format(player, line);
			formattedLines.add(formattedLine);
		}
		return formattedLines;
	}

	public void cancelScoreboards() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		for (int taskID : taskIDs.values()) {
			scheduler.cancelTask(taskID);
		}
	}

	public void reloadScoreboards() {
		cancelScoreboards();
		boards.clear();
		taskIDs.clear();
		createScoreboard();
	}
}


