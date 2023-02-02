package net.blueva.core.admins;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import net.blueva.core.Main;

public class LastLocationAdmin {
	
	private Main main;
	int taskID;
	
	public LastLocationAdmin(Main main) {
		this.main = main;
	}
	
	public void createLastLocation() {
		BukkitScheduler schedule = Bukkit.getServer().getScheduler();
		taskID = schedule.scheduleSyncRepeatingTask(main, new Runnable() {
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()) {
					updateLocation(player);
				}
			}
			}, 0, 1200); //1200 Ticks = 60 Seconds (It will be possible to customize it in the settings.yml in the future)
}

	private void updateLocation(Player p) {
		Location l = p.getLocation();
		String world = l.getWorld().getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();
		main.getUserdata(p.getUniqueId()).set("lastlocation.world", world);
		main.getUserdata(p.getUniqueId()).set("lastlocation.x", x);
		main.getUserdata(p.getUniqueId()).set("lastlocation.y", y);
		main.getUserdata(p.getUniqueId()).set("lastlocation.z", z);
		main.getUserdata(p.getUniqueId()).set("lastlocation.yaw", yaw);
		main.getUserdata(p.getUniqueId()).set("lastlocation.pitch", pitch);
		main.saveUserdata();
	}

}
