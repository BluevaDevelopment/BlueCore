package net.blueva.core.managers;

import java.util.List;

import net.blueva.core.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import net.blueva.core.Main;

public class ScoreboardManager {

	private Main main;
	int taskID;

	public ScoreboardManager(Main main) {
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
		}, 0, Integer.valueOf(main.configManager.getSettings().getInt("scoreboard.ticks")));
	}

	private void updateScoreboard(Player p) {
		org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard scoreboard = manager.getNewScoreboard();
		Objective objetive = scoreboard.registerNewObjective("TheNexus", "dummy", "TNScore");
		objetive.setDisplaySlot(DisplaySlot.SIDEBAR);
		objetive.setDisplayName(MessagesUtil.format(p, main.configManager.getSettings().getString("scoreboard.title")));
		List<String> lines = main.configManager.getSettings().getStringList("scoreboard.lines");
		for(int i=0;i<lines.size();i++) {
			Score score = objetive.getScore(MessagesUtil.format(p, lines.get(i)));
			score.setScore(lines.size()-(i));
		}
		p.setScoreboard(scoreboard);
	}

}
