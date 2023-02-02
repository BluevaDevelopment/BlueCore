package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;

public class CreativeCommand implements CommandExecutor {

    private Main main;

    public CreativeCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //player:
        if ((sender instanceof Player)) {
            if (args.length > 0) {
                if (sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.gamemode.*") ||
                        sender.hasPermission("bluecore.gamemode.creative.*") ||
                        sender.hasPermission("bluecore.gamemode.creative.others")) {
                    if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            target.setGameMode(GameMode.CREATIVE);
                            target.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.gamemode_changed").replace("%gamemode%", "CREATIVE"), target));
                            sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.gamemode_changed_others").replace("%gamemode%", "CREATIVE").replace("%player%", target.getName()), target));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                        }
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), ((Player) sender)));
                }
            } else {
                if (sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.gamemode.*") ||
                        sender.hasPermission("bluecore.gamemode.creative") ||
                        sender.hasPermission("bluecore.gamemode.creative.*")) {
                    ((Player) sender).setGameMode(GameMode.CREATIVE);
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.gamemode_changed").replace("%gamemode%", "CREATIVE"), ((Player) sender)));
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }
        } else {

            //console:
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null){
                    target.setGameMode(GameMode.CREATIVE);
                    target.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.gamemode_changed").replace("%gamemode%", "CREATIVE"), target));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.success.gamemode_changed_others")).replace("%gamemode%", "CREATIVE").replace("%player%", target.getName()));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.other.use_creative_command")));
            }
        }
        return true;
    }
}