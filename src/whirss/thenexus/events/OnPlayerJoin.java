package whirss.thenexus.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import whirss.thenexus.Main;

public class OnPlayerJoin implements Listener {
	
	private Main main;
	
	public OnPlayerJoin(Main main) {
		this.main = main;
	}

	@EventHandler 
	public void onPlayerJoin(PlayerJoinEvent event) {
		main.registerUserdata(event.getPlayer().getUniqueId());
	}

}