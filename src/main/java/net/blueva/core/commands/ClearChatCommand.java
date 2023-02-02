package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class ClearChatCommand implements CommandExecutor {

    private Main main;

    public ClearChatCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if((sender instanceof Player)) {
            if(args.length > 0){
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.clearchat.*") ||
                        sender.hasPermission("bluecore.clearchat.others")){
                    if(args.length == 1){
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target != null){
                            for(int i=0;i<200;i++) {
                                target.sendMessage("");
                            }
                            target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.chat_cleared")));
                            sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.chat_cleared_others").replace("%player%", target.getName())));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                        }
                    }
                } else {
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                }
            }else{
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.clearchat.*") ||
                        sender.hasPermission("bluecore.clearchat")){
                    for(int i=0;i<200;i++) {
                        Bukkit.broadcastMessage("");
                    }
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.success.chat_cleared")));
                } else {
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                }
            }
        } else {
            if(args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null){
                    for(int i=0;i<200;i++) {
                        target.sendMessage("");
                    }
                    target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("console.success.chat_cleared")));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.success.chat_cleared_others")).replace("%player%", target.getName()));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                }
            } else {
                for(int i=0;i<200;i++) {
                    Bukkit.broadcastMessage("");
                }
            }
        }
        return true;
    }
}