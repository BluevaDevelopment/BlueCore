package org.jachi.whirss.thenexus.admins;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import org.jachi.whirss.thenexus.Main;
import org.jachi.whirss.thenexus.MessageUtil;

public class ScoreboardAdmin {

	private Main main;
	int taskID;

	public ScoreboardAdmin(Main main) {
		this.main = main;
	}

	public void createScoreboard() {
		BukkitScheduler schedule = Bukkit.getServer().getScheduler();
		taskID = schedule.scheduleSyncRepeatingTask(main, new Runnable() {
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()) {
					updateScoreboard(player);
				}
			}
		}, 0, Integer.valueOf(main.getConfig().getInt("scoreboard.ticks")));
	}

	private void updateScoreboard(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		Objective objetive = scoreboard.registerNewObjective("TheNexus", "dummy", "TNScore");
		objetive.setDisplaySlot(DisplaySlot.SIDEBAR);
		objetive.setDisplayName(MessageUtil.getColorMessage(main.getConfig().getString("scoreboard.title")));
		List<String> lines = main.getConfig().getStringList("scoreboard.lines");
		for(int i=0;i<lines.size();i++) {
			Score score = objetive.getScore(MessageUtil.getColorMessage(lines.get(i)));
			score.setScore(lines.size()-(i));
		}
		p.setScoreboard(scoreboard);
	}

}
