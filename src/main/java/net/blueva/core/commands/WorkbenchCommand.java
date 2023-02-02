package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class WorkbenchCommand implements CommandExecutor {

    private Main main;

    public WorkbenchCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        //player:
        if((sender instanceof Player)) {
            if(args.length > 0){
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.workbench.others") ||
                        sender.hasPermission("bluecore.workbench.*")){
                    if(args.length == 1){
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target != null){
                            target.openInventory(target.openWorkbench(null, true));
                            target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.workbench_open")));
                            sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.workbench_open_others").replace("%player%", target.getName())));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                        }
                    }
                } else {
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                }
            }else{
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.workbench") ||
                        sender.hasPermission("bluecore.workbench.*")){
                    ((Player) sender).openInventory(((Player) sender).openWorkbench(null, true));
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.success.workbench_open")));
                } else {
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                }
            }
        } else {

            //console:
            if(args.length > 0){
                if(args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.openInventory(target.openWorkbench(null, true));
                        target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.workbench_open")));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.success.workbench_open_others")).replace("%player%", target.getName()));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.other.use_workbench_command")));
            }
        }
        return true;
    }
}