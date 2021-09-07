package whirss.thenexus.admins;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import whirss.thenexus.Main;
import whirss.thenexus.StringUtil;



public class TablistAdmin {
	
	private Main main;
	int taskID;
	
	public TablistAdmin(Main main) {
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
 			}, 0, Integer.valueOf(main.getConfig().getInt("tablist.ticks")));
	}
	
	private void updateTablist(Player p) {
		p.setPlayerListHeader(ChatColor.translateAlternateColorCodes('&', StringUtil.listToString((ArrayList<String>) main.getConfig().getStringList("tablist.header"), "\n")));
		p.setPlayerListFooter(ChatColor.translateAlternateColorCodes('&', StringUtil.listToString((ArrayList<String>) main.getConfig().getStringList("tablist.footer"), "\n")).replace("%online%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size())));
	}


}
