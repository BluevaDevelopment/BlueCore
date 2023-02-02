package net.blueva.core.events;

import java.util.Objects;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class OnPlayerChatAsync implements Listener {
	
	private final Main main;
	
	public OnPlayerChatAsync(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void OPCA(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		if(main.configManager.getSettings().getBoolean("chat.antiswear.enabled")) {
			if(!player.hasPermission("bluecore.chat.antiswear.bypass")) {
				for(String blockedWords : main.configManager.getSettings().getStringList("chat.antiswear.list")) {
					if(message.toLowerCase().replaceAll("[-_*. ]", "").contains(blockedWords.toLowerCase())) {
						if(Objects.equals(main.configManager.getSettings().getString("chat.antiswear.mode"), "replace")) {
							StringBuilder a = new StringBuilder();
							for(int c=0;c<blockedWords.length();c++) {
								a.append("*");
							}
							message = message.replace(blockedWords, a.toString());
						}
						if(Objects.equals(main.configManager.getSettings().getString("chat.antiswear.mode"), "block")) {
							player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.info.antiswear_block")));
							event.setCancelled(true);
							return;
						}
						
					}
				}
				event.setMessage(message);
			}
		}
		if(!Objects.equals(main.configManager.getSettings().getString("chat.format"), "none")) {
			String formated_message = Objects.requireNonNull(main.configManager.getSettings().getString("chat.format")).replaceFirst("%player_displayname%", player.getDisplayName()).replaceFirst("%message%", message);
			event.setFormat(MessagesUtil.format(player, formated_message));
		}
		if(main.configManager.getSettings().getBoolean("chat.per_world")) {
			Set<Player> r = event.getRecipients();
		    for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
		      if (!pls.getWorld().getName().equals(player.getWorld().getName()))
		        r.remove(pls); 
		    } 
		}
	}

}
