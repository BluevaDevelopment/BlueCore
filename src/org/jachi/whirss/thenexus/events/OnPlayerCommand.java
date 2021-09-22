package org.jachi.whirss.thenexus.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import org.jachi.whirss.thenexus.Main;

public class OnPlayerCommand implements Listener {
	
	private Main main;
	
	public OnPlayerCommand(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void OnCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage().toLowerCase();
		for (String key : main.getCommands().getConfigurationSection("commands").getKeys(false)) {
			String command = main.getCommands().getString("commands." + key + ".command");
			List<String> worlds = main.getCommands().getStringList("commands." + key + ".worlds");
			String[] separatedCommands = command.split(" ");
			String[] separatedMessages = command.split(" ");
			if (separatedMessages.length >= separatedCommands.length) {
				boolean ContinueB = false;
				for (int i = 0; i < separatedCommands.length; i++) {
					if (!separatedCommands[i].equals(separatedMessages[i])) {
						ContinueB = true;
						break;
					}
				}
				if (ContinueB)
					continue;
				event.setCancelled(true);
				if(main.getCommands().isSet("commands." + key + ".worlds")) {
					for (int c = 0; c < worlds.size(); c++) {
						if (player.getWorld().getName().equals(worlds.get(c))) {
							List<String> commandsList = main.getCommands().getStringList("commands." + key + ".run_commands");
							ConsoleCommandSender consoleCommandSender = Bukkit.getServer().getConsoleSender();
							for (int j = 0; j < commandsList.size(); j++) {
								String commandToSend = ((String)commandsList.get(j)).replaceAll("%player%", player.getName());
								if (commandToSend.startsWith("console:")) {
									if (commandToSend.contains("msg " + player.getName())) {
										String msg = commandToSend.replace("msg " + player.getName() + " ", "").replace("console: ", "");
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
									} else {
										Bukkit.dispatchCommand((CommandSender)consoleCommandSender, commandToSend.replace("console: ", ""));
									}
								} else {
									player.chat("/" + commandToSend.replace("player: ", ""));
								}
							}
							return;
						} else {
							player.sendMessage("este no es tu mundo bro");
						}
					}
				} else {
					List<String> commandsList = main.getCommands().getStringList("commands." + key + ".run_commands");
					ConsoleCommandSender consoleCommandSender = Bukkit.getServer().getConsoleSender();
					for (int j = 0; j < commandsList.size(); j++) {
						String commandToSend = ((String)commandsList.get(j)).replaceAll("%player%", player.getName());
						if (commandToSend.startsWith("console:")) {
							if (commandToSend.contains("msg " + player.getName())) {
								String msg = commandToSend.replace("msg " + player.getName() + " ", "").replace("console: ", "");
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
							} else {
								Bukkit.dispatchCommand((CommandSender)consoleCommandSender, commandToSend.replace("console: ", ""));
							}
						} else {
							player.chat("/" + commandToSend.replace("player: ", ""));
						}
					}
					return;
				}

			}
		}
		if(main.getConfig().getBoolean("command_blocker.enabled")) {
	    	String[] arrayOfString;
	        int j = (arrayOfString = event.getMessage().split(" ")).length;
	        for (int i = 0; i < j; i++) {
	          String bcmd = arrayOfString[i];
	          if (main.getConfig().getStringList("command_blocker.list").contains(bcmd.toLowerCase())) {
	            event.setCancelled(true);
	            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
	          } 
	        }
	    }
	}

}
