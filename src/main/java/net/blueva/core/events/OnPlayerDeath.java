package net.blueva.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.blueva.core.Main;

import java.util.Objects;

public class OnPlayerDeath implements Listener {
	
	private final Main main;
	
	public OnPlayerDeath(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void OPD(PlayerDeathEvent event) {
		Player player = (Player) event.getEntity();
		Location l = Objects.requireNonNull(event.getEntity().getPlayer()).getLocation();
		String world = Objects.requireNonNull(l.getWorld()).getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.world", world);
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.x", x);
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.y", y);
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.z", z);
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.yaw", yaw);
		main.configManager.getUser(event.getEntity().getPlayer().getUniqueId()).set("lastlocation.pitch", pitch);
		main.configManager.saveUser(event.getEntity().getPlayer().getUniqueId());


		String respawnWorld = main.configManager.getWorlds().getString("worlds." + world + ".respawnWorld");
		if(world.equals(main.configManager.getWorlds().getStringList("worlds")) && main.configManager.getWorlds().getBoolean("worlds." + world + ".drop_items")) {
			if(main.configManager.getWorlds().isSet("worlds." + world + ".respawnWorld")) {
				double xRespawn = Double.parseDouble(main.configManager.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.x"));
				double yRespawn = Double.parseDouble(main.configManager.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.y"));
				double zRespawn = Double.parseDouble(main.configManager.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.z"));
				float pitchRespawn = Float.parseFloat(main.configManager.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.pitch"));
				float yawRespawn = Float.parseFloat(main.configManager.getWorlds().getString("worlds." + respawnWorld + ".spawnlocation.yaw"));
				Location loc = new Location(Bukkit.getWorld(respawnWorld), xRespawn, yRespawn, zRespawn, yawRespawn, pitchRespawn);
				player.teleport(loc);
			}
		}
	}

}
