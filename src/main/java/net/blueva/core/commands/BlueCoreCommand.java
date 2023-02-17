package net.blueva.core.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlueCoreCommand implements CommandExecutor {

	private final Main main;

	public BlueCoreCommand(Main main) {
		this.main = main;
	}

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
		if(sender.hasPermission("bluecore.info") || sender.hasPermission("bluecore.*")) {
			List<String> bcinfo = main.configManager.getLang().getStringList("commands.bluecore.info");
			for(int i=0;i<bcinfo.size();i++) {
				String message = bcinfo.get(i);
				Player player = null;
				if(sender instanceof Player) {
					player = (Player) sender;
				}
				sender.sendMessage(MessagesUtil.format(player, message.replace("{plugin_version}", main.pluginversion)));
			}
		}
		return true;
	}

}