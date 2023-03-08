package net.blueva.core.managers;

import java.util.List;

import net.blueva.core.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import net.blueva.core.Main;
import net.blueva.core.libraries.netherboard.BPlayerBoard;
import net.blueva.core.libraries.netherboard.Netherboard;

public class ScoreboardManager {

	private Main main;
	int taskID;

	public ScoreboardManager(Main main) {
		this.main = main;
	}

	public void createScoreboard() {
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		taskID = scheduler.scheduleSyncRepeatingTask(main, () -> {
			for(Player player : Bukkit.getOnlinePlayers()) {
				updateScoreboard(player);
			}
		}, 0L, main.configManager.getSettings().getInt("scoreboard.ticks"));
	}

	private void updateScoreboard(Player p) {
		if(main.configManager.getSettings().getBoolean("scoreboard.enabled") && !main.configManager.getSettings().getStringList("scoreboard.disabled_worlds").contains(p.getWorld().getName())) {
			BPlayerBoard board = Netherboard.instance().getBoard(p);
			if(board == null) {
				board = Netherboard.instance().createBoard(p, MessagesUtil.format(p, main.configManager.getSettings().getString("scoreboard.title")));
			}
			board.setName(MessagesUtil.format(p, main.configManager.getSettings().getString("scoreboard.title")));
			List<String> lines = main.configManager.getSettings().getStringList("scoreboard.lines");
			for(int i = lines.size() - 1; i >= 0; i--) {
				int pos = lines.size() - 1 - i;
				board.set(MessagesUtil.format(p, lines.get(i)), pos);
			}
		} else {
			if(Netherboard.instance().getBoard(p) != null) {
				Netherboard.instance().deleteBoard(p);
			}
		}
	}

}
