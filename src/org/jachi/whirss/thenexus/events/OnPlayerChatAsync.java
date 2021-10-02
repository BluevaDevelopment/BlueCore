package org.jachi.whirss.thenexus.events;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import org.jachi.whirss.thenexus.Main;
import org.jachi.whirss.thenexus.MessageUtil;

public class OnPlayerChatAsync implements Listener {
	
	private Main main;
	
	public OnPlayerChatAsync(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void OnChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		if(main.getConfig().getBoolean("chat.antiswear.enabled")) {
			if(!player.hasPermission("thenexus.chat.antiswear.bypass")) {
				for(String blockedWords : main.getConfig().getStringList("chat.antiswear.list")) {
					if(message.toLowerCase().replaceAll("[-_*. ]", "").contains(blockedWords.toLowerCase())) {
						if(main.getConfig().getString("chat.antiswear.mode").equals("replace")) {
							String a = "";
							for(int c=0;c<blockedWords.length();c++) {
								a = a+"*";
							}
							message = message.replace(blockedWords, a);
						}
						if(main.getConfig().getString("chat.antiswear.mode").equals("block")) {
							player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.info.antiswear_block")));
							event.setCancelled(true);
							return;
						}
						
					}
				}
				event.setMessage(message);
			}
		}
		if(!main.getConfig().getString("chat.format").equals("none")) {
			String formated_message = main.getConfig().getString("chat.format").replaceFirst("%player_displayname%", player.getDisplayName()).replaceFirst("%message%", message);
			event.setFormat(MessageUtil.getColorMessage(formated_message));
		}
		if(main.getConfig().getBoolean("chat.per_world")) {
			Player sender = player;
		    Set<Player> r = event.getRecipients();
		    for (Player pls : Bukkit.getServer().getOnlinePlayers()) {
		      if (!pls.getWorld().getName().equals(sender.getWorld().getName()))
		        r.remove(pls); 
		    } 
		}
	}

}
