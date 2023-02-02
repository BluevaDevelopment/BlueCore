package net.blueva.core.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;
import org.jetbrains.annotations.NotNull;

public class CoreCommand implements CommandExecutor {

	private final Main main;

	public CoreCommand(Main main) {
		this.main = main;
	}

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to run this command.");
			return true;
		}
		final Player p = (Player)sender;

		if(args.length > 0){
			if(args[0].equalsIgnoreCase("help")){
				if(p.hasPermission("bluecore.help") || sender.hasPermission("bluecore.*")){
					sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "The" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Nexus");
					sender.sendMessage(ChatColor.GRAY + "Version: " + main.pluginversion + " | By Whirss");
					sender.sendMessage("");
					sender.sendMessage(ChatColor.AQUA + "/nexus help");
					sender.sendMessage(ChatColor.AQUA + "/nexus wiki");
					sender.sendMessage(ChatColor.AQUA + "/nexus reload");

				} else {
					sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("no_perms"), p));
				}
			}else if(args[0].equalsIgnoreCase("reload")){
				if(p.hasPermission("bluecore.reload") || sender.hasPermission("bluecore.*")){
					main.configManager.reloadSettings();
					main.configManager.reloadWarps();
					main.configManager.reloadCommands();
					main.configManager.reloadWorlds();
					main.configManager.reloadLang();
					sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.reloaded_plugin"), p));
				} else {
					sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), p));
				}
			}else if(args[0].equalsIgnoreCase("wiki")){
				if(p.hasPermission("bluecore.reload") || sender.hasPermission("bluecore.*")){
					sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.reloaded_plugin"), p));
				} else {
					sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), p));
				}
			}else{
				sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.unknown_command"), p));
			}
		}else{
			if(sender.hasPermission("bluecore.help") || sender.hasPermission("bluecore.*")) {
				sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "The" + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Nexus");
				sender.sendMessage(ChatColor.GRAY + "Version: " + main.pluginversion + " | By Whirss");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.AQUA + "/nexus help");
				sender.sendMessage(ChatColor.AQUA + "/nexus wiki");
				sender.sendMessage(ChatColor.AQUA + "/nexus reload");
			} else {
				sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("no_perms"), p));
			}
		}
		return true;
	}

}