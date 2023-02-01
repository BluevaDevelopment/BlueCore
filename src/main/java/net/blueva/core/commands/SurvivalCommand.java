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

public class SurvivalCommand implements CommandExecutor {

    private Main main;

    public SurvivalCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //player:
        if ((sender instanceof Player)) {
            if (args.length > 0) {
                if (sender.hasPermission("xtremecore.*") ||
                        sender.hasPermission("xtremecore.gamemode.*") ||
                        sender.hasPermission("xtremecore.gamemode.survival.*") ||
                        sender.hasPermission("xtremecore.gamemode.survival.others")) {
                    if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            target.setGameMode(GameMode.SURVIVAL);
                            target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "SURVIVAL"), target));
                            sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed_others").replace("%gamemode%", "SURVIVAL").replace("%player%", target.getName()), target));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                        }
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            } else {
                if (sender.hasPermission("xtremecore.*") ||
                        sender.hasPermission("xtremecore.gamemode.*") ||
                        sender.hasPermission("xtremecore.gamemode.survival") ||
                        sender.hasPermission("xtremecore.gamemode.survival.*")) {
                    ((Player) sender).setGameMode(GameMode.SURVIVAL);
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "SURVIVAL"), ((Player) sender)));
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }
        } else {

            //console:
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null){
                    target.setGameMode(GameMode.SURVIVAL);
                    target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "SURVIVAL"), target));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.success.gamemode_changed_others")).replace("%gamemode%", "SURVIVAL").replace("%player%", target.getName()));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_survival_command")));
            }
        }
        return true;
    }
}