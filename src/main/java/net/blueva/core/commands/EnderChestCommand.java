package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;

public class EnderChestCommand implements CommandExecutor {

    private Main main;

    public EnderChestCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        //player:
        if((sender instanceof Player)) {
            if(args.length > 0){
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.enderchest.others") ||
                        sender.hasPermission("bluecore.enderchest.*")){
                    if(args.length == 1){
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target != null){
                            ((Player) sender).openInventory(target.getEnderChest());
                            target.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.enderchest_open"), target));
                            sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.enderchest_open_others").replace("%player%", target.getName()), target));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                        }
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }else{
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.enderchest") ||
                        sender.hasPermission("bluecore.enderchest.*")){
                    ((Player) sender).openInventory(((Player) sender).getEnderChest());
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.enderchest_open"), ((Player) sender)));
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }
        } else {

            //console:
            if(args.length > 0){
                if(args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.openInventory(target.getEnderChest());
                        target.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.enderchest_open"), target));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.success.enderchest_open_others")).replace("%player%", target.getName()));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.other.use_enderchest_command")));
            }
        }
        return true;
    }
}