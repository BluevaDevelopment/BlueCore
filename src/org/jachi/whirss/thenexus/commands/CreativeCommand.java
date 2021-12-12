package org.jachi.whirss.thenexus.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import org.jachi.whirss.thenexus.Main;
import org.jachi.whirss.thenexus.MessageUtil;

public class CreativeCommand implements CommandExecutor {

    private Main main;

    public CreativeCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //player:
        if ((sender instanceof Player)) {
            if (args.length > 0) {
                if (sender.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.gamemode.*") ||
                        sender.hasPermission("thenexus.gamemode.creative.*") ||
                        sender.hasPermission("thenexus.gamemode.creative.others")) {
                    if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            target.setGameMode(GameMode.CREATIVE);
                            target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "CREATIVE"), target));
                            sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed_others").replace("%gamemode%", "CREATIVE").replace("%player%", target.getName()), target));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                        }
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            } else {
                if (sender.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.gamemode.*") ||
                        sender.hasPermission("thenexus.gamemode.creative") ||
                        sender.hasPermission("thenexus.gamemode.creative.*")) {
                    ((Player) sender).setGameMode(GameMode.CREATIVE);
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "CREATIVE"), ((Player) sender)));
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }
        } else {

            //console:
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null){
                    target.setGameMode(GameMode.CREATIVE);
                    target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "CREATIVE"), target));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.success.gamemode_changed_others")).replace("%gamemode%", "CREATIVE").replace("%player%", target.getName()));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_creative_command")));
            }
        }
        return true;
    }
}