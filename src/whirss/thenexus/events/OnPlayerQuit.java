package whirss.thenexus.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import whirss.thenexus.Main;

public class OnPlayerQuit implements Listener {
	
	private Main main;
	
	public OnPlayerQuit(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(main.getConfig().getBoolean("welcome.broadcast.leave.enabled")) {
			   event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("welcome.broadcast.leave.message").replace("%player_name%", event.getPlayer().getDisplayName())));
		   } else {
			   event.setQuitMessage("");
		   }
	}

}
