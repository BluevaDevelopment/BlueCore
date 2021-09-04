package whirss.thenexus.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import whirss.thenexus.ConfigManager;

public class OnPlayerJoin implements Listener {
	
	@EventHandler 
	public void onPlayerJoin(PlayerJoinEvent event, ConfigManager cm) {
		cm.registerUserdata(event.getPlayer().getUniqueId());
	}

}