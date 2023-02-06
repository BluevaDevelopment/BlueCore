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
