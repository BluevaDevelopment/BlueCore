package org.jachi.whirss.thenexus.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import org.jachi.whirss.thenexus.Main;

public class OnPlayerDeath implements Listener {
	
	private Main main;
	
	public OnPlayerDeath(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void OnDeath(PlayerDeathEvent event) {
		Location l = event.getEntity().getPlayer().getLocation();
		String world = l.getWorld().getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();
		main.getUserdata(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.world", world);
		main.getUserdata(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.x", x);
		main.getUserdata(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.y", y);
		main.getUserdata(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.z", z);
		main.getUserdata(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.yaw", yaw);
		main.getUserdata(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.pitch", pitch);
		main.saveUserdata();
	}

}
