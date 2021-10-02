package org.jachi.whirss.thenexus.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import org.jachi.whirss.thenexus.Main;
import org.jachi.whirss.thenexus.MessageUtil;

public class OnPlayerQuit implements Listener {

	private Main main;

	public OnPlayerQuit(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(main.getConfig().getBoolean("welcome.broadcast.leave.enabled")) {
			event.setQuitMessage(MessageUtil.getColorMessage(main.getConfig().getString("welcome.broadcast.leave.message"), event.getPlayer()).replace("%player_name%", event.getPlayer().getDisplayName()));
		} else {
			event.setQuitMessage("");
		}

		Location l = event.getPlayer().getLocation();
		String world = l.getWorld().getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();
		main.getUserdata(event.getPlayer().getUniqueId()).set("logoutlocation.world", world);
		main.getUserdata(event.getPlayer().getUniqueId()).set("logoutlocation.x", x);
		main.getUserdata(event.getPlayer().getUniqueId()).set("logoutlocation.y", y);
		main.getUserdata(event.getPlayer().getUniqueId()).set("logoutlocation.z", z);
		main.getUserdata(event.getPlayer().getUniqueId()).set("logoutlocation.yaw", yaw);
		main.getUserdata(event.getPlayer().getUniqueId()).set("logoutlocation.pitch", pitch);
		main.saveUserdata();

	}

}
