package net.blueva.core.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;

import java.util.Objects;

public class OnPlayerQuit implements Listener {

	private final Main main;

	public OnPlayerQuit(Main main) {
		this.main = main;
	}

	@EventHandler
	public void OPQ(PlayerQuitEvent event) {
		if(main.configManager.getSettings().getBoolean("welcome.broadcast.leave.enabled")) {
			event.setQuitMessage(MessageUtil.getColorMessage(main.configManager.getSettings().getString("welcome.broadcast.leave.message"), event.getPlayer()).replace("%player_name%", event.getPlayer().getDisplayName()));
		} else {
			event.setQuitMessage("");
		}

		Location l = event.getPlayer().getLocation();
		String world = Objects.requireNonNull(l.getWorld()).getName();
		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();
		float yaw = l.getYaw();
		float pitch = l.getPitch();
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.world", world);
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.x", x);
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.y", y);
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.z", z);
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.yaw", yaw);
		main.configManager.getUser(event.getPlayer().getUniqueId()).set("logoutlocation.pitch", pitch);
		main.configManager.saveUser(event.getPlayer().getUniqueId());

	}

}
