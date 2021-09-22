package org.jachi.whirss.thenexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import org.jachi.whirss.thenexus.Main;

public class NexusCommand implements CommandExecutor {
	
	private Main main;
	
	public NexusCommand(Main main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){    	
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to run this command.");
				return true;
			}
			final Player p = (Player)sender;

			if(args.length > 0){
				if(args[0].equalsIgnoreCase("help")){
					if(p.hasPermission("thenexus.admin.help") || sender.hasPermission("thenexus.admin.*")){
						sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "The" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Nexus");
						sender.sendMessage(ChatColor.GRAY + "Version: " + main.version + " | By Whirss");
						sender.sendMessage("");
						sender.sendMessage(ChatColor.AQUA + "/nexus help");
						sender.sendMessage(ChatColor.AQUA + "/nexus wiki");
						sender.sendMessage(ChatColor.AQUA + "/nexus reload");
						
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
					}
				}else if(args[0].equalsIgnoreCase("reload")){
					if(p.hasPermission("thenexus.admin.reload") || sender.hasPermission("thenexus.admin.*")){
						main.reloadConfig();
						main.reloadKits();
						main.reloadMessages();
						main.reloadWarps();
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("reloaded_plugin")));
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
					}
				}else if(args[0].equalsIgnoreCase("wiki")){
					if(p.hasPermission("thenexus.admin.reload") || sender.hasPermission("thenexus.admin.*")){
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("reloaded_plugin")));
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
					}
				}else{
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("unknown_command")));
				}
			}else{
				if(sender.hasPermission("thenexus.admin.help") || sender.hasPermission("thenexus.admin.*")) {
					sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "The" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Nexus");
					sender.sendMessage(ChatColor.GRAY + "Version: " + main.version + " | By Whirss");
					sender.sendMessage("");
					sender.sendMessage(ChatColor.AQUA + "/nexus help");
					sender.sendMessage(ChatColor.AQUA + "/nexus wiki");
					sender.sendMessage(ChatColor.AQUA + "/nexus reload");
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
				}
			}
			return true;
	}

}