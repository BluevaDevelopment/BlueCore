package org.jachi.whirss.thenexus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import org.jachi.whirss.thenexus.Main;
import org.jachi.whirss.thenexus.MessageUtil;

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
				if(p.hasPermission("thenexus.help") || sender.hasPermission("thenexus.*")){
					sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "The" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Nexus");
					sender.sendMessage(ChatColor.GRAY + "Version: " + main.version + " | By Whirss");
					sender.sendMessage("");
					sender.sendMessage(ChatColor.AQUA + "/nexus help");
					sender.sendMessage(ChatColor.AQUA + "/nexus wiki");
					sender.sendMessage(ChatColor.AQUA + "/nexus reload");

				} else {
					sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("no_perms")));
				}
			}else if(args[0].equalsIgnoreCase("reload")){
				if(p.hasPermission("thenexus.reload") || sender.hasPermission("thenexus.*")){
					main.reloadConfig();
					main.reloadKits();
					main.reloadWarps();
					main.reloadCommands();
					main.reloadWorlds();
					main.reloadLanguages();
					sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.reloaded_plugin")));
				} else {
					sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
				}
			}else if(args[0].equalsIgnoreCase("wiki")){
				if(p.hasPermission("thenexus.reload") || sender.hasPermission("thenexus.*")){
					sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.reloaded_plugin")));
				} else {
					sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
				}
			}else{
				sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.unknown_command")));
			}
		}else{
			if(sender.hasPermission("thenexus.help") || sender.hasPermission("thenexus.*")) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "The" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Nexus");
				sender.sendMessage(ChatColor.GRAY + "Version: " + main.version + " | By Whirss");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.AQUA + "/nexus help");
				sender.sendMessage(ChatColor.AQUA + "/nexus wiki");
				sender.sendMessage(ChatColor.AQUA + "/nexus reload");
			} else {
				sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("no_perms")));
			}
		}
		return true;
	}

}