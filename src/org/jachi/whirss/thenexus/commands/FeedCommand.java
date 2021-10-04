package org.jachi.whirss.thenexus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import org.jachi.whirss.thenexus.Main;
import org.jachi.whirss.thenexus.MessageUtil;

public class FeedCommand implements CommandExecutor {

    private Main main;

    public FeedCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(args.length > 0){
            if (!(sender instanceof Player)) {
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                        target.setFoodLevel(20);
                        target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed"), target));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.success.satisfied_appetite_others")).replace("%player%", target.getName()));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                    }
                }
                return true;
            }
            if(sender.hasPermission("thenexus.*") ||
                    sender.hasPermission("thenexus.feed") ||
                    sender.hasPermission("thenexus.feed.others")){
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                        target.setFoodLevel(20);
                        target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.satisfied_appetite"), target));
                        sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.satisfied_appetite_others").replace("%player%", target.getName()), target));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                    }
                }
            } else {
                sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
            }
        }else{
            if ((sender instanceof Player)) {
                if(sender.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.feed") ||
                        sender.hasPermission("thenexus.feed.others")){
                    ((Player) sender).setFoodLevel(20);
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.satisfied_appetite"), ((Player) sender)));
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_creative_command")));
            }
        }
        return true;
    }
}