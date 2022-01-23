package es.xtreme.core.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.xtreme.core.Main;
import es.xtreme.core.utils.MessageUtil;

public class CoreCommand implements CommandExecutor {

	private Main main;

	public CoreCommand(Main main) {
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
				if(p.hasPermission("xtremecore.help") || sender.hasPermission("xtremecore.*")){
					sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "The" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Nexus");
					sender.sendMessage(ChatColor.GRAY + "Version: " + main.version + " | By Whirss");
					sender.sendMessage("");
					sender.sendMessage(ChatColor.AQUA + "/nexus help");
					sender.sendMessage(ChatColor.AQUA + "/nexus wiki");
					sender.sendMessage(ChatColor.AQUA + "/nexus reload");

				} else {
					sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("no_perms"), p));
				}
			}else if(args[0].equalsIgnoreCase("reload")){
				if(p.hasPermission("xtremecore.reload") || sender.hasPermission("xtremecore.*")){
					main.reloadConfig();
					main.reloadKits();
					main.reloadWarps();
					main.reloadCommands();
					main.reloadWorlds();
					main.reloadLanguages();
					sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.reloaded_plugin"), p));
				} else {
					sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), p));
				}
			}else if(args[0].equalsIgnoreCase("wiki")){
				if(p.hasPermission("xtremecore.reload") || sender.hasPermission("xtremecore.*")){
					sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.reloaded_plugin"), p));
				} else {
					sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), p));
				}
			}else{
				sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.unknown_command"), p));
			}
		}else{
			if(sender.hasPermission("xtremecore.help") || sender.hasPermission("xtremecore.*")) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "The" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Nexus");
				sender.sendMessage(ChatColor.GRAY + "Version: " + main.version + " | By Whirss");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.AQUA + "/nexus help");
				sender.sendMessage(ChatColor.AQUA + "/nexus wiki");
				sender.sendMessage(ChatColor.AQUA + "/nexus reload");
			} else {
				sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("no_perms"), p));
			}
		}
		return true;
	}

}