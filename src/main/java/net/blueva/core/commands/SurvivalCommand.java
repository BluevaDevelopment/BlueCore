package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class SurvivalCommand implements CommandExecutor {

    private Main main;

    public SurvivalCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //player:
        if ((sender instanceof Player)) {
            if (args.length > 0) {
                if (sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.gamemode.*") ||
                        sender.hasPermission("bluecore.gamemode.survival.*") ||
                        sender.hasPermission("bluecore.gamemode.survival.others")) {
                    if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            target.setGameMode(GameMode.SURVIVAL);
                            target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.gamemode_changed").replace("%gamemode%", "SURVIVAL")));
                            sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.gamemode_changed_others").replace("%gamemode%", "SURVIVAL").replace("%player%", target.getName())));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                        }
                    }
                } else {
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                }
            } else {
                if (sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.gamemode.*") ||
                        sender.hasPermission("bluecore.gamemode.survival") ||
                        sender.hasPermission("bluecore.gamemode.survival.*")) {
                    ((Player) sender).setGameMode(GameMode.SURVIVAL);
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.success.gamemode_changed").replace("%gamemode%", "SURVIVAL")));
                } else {
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                }
            }
        } else {

            //console:
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null){
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.gamemode_changed").replace("%gamemode%", "SURVIVAL")));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.success.gamemode_changed_others")).replace("%gamemode%", "SURVIVAL").replace("%player%", target.getName()));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.other.use_survival_command")));
            }
        }
        return true;
    }
}