package org.jachi.whirss.thenexus.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
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
	public void OnPlayerDeath(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
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


		String respawnWorld = main.getWorlds().getString("worlds." + world + ".respawnWorld");
		if(world.equals(main.getWorlds().getStringList("worlds")) && main.getWorlds().getBoolean("worlds." + world + ".drop_items")) {
			if(main.getWorlds().isSet("worlds." + world + ".respawnWorld")) {
				double xRespawn = Double.valueOf(main.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.x"));
				double yRespawn = Double.valueOf(main.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.y"));
				double zRespawn = Double.valueOf(main.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.z"));
				float pitchRespawn = Float.valueOf(main.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.pitch"));
				float yawRespawn = Float.valueOf(main.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.yaw"));
				Location loc = new Location(Bukkit.getWorld(respawnWorld), xRespawn, yRespawn, zRespawn, yawRespawn, pitchRespawn);
				player.teleport(loc);
			}
		}
	}

}
