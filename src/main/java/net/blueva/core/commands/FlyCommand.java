package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;

public class FlyCommand implements CommandExecutor {

    private Main main;

    public FlyCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        //player:
        if((sender instanceof Player)) {
            if(args.length > 0){
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.fly") ||
                        sender.hasPermission("bluecore.fly.others")){
                    if(args.length == 1){
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target != null){
                            if(target.isFlying()) {
                                target.setFlying(false);
                                target.setAllowFlight(false);
                                target.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.fly_mode_disabled"), target));
                                sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.fly_mode_disabled_other"), target).replace("%player%", target.getName()));
                            } else {
                                target.setAllowFlight(true);
                                target.setFlying(true);
                                target.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.fly_mode_enabled"), target));
                                sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.fly_mode_enabled_other"), target).replace("%player%", target.getName()));
                            }
                        } else {
                            sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("console.error.player_offline"), ((Player) sender)));
                        }
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }else{
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.fly.*") ||
                        sender.hasPermission("bluecore.fly")) {
                    if(((Player) sender).isFlying()) {
                        ((Player) sender).setAllowFlight(false);
                        ((Player) sender).setFlying(false);
                        sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.fly_mode_disabled"), ((Player) sender)).replace("%player%", ((Player) sender).getName()));
                    } else {
                        ((Player) sender).setAllowFlight(true);
                        ((Player) sender).setFlying(true);
                        sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.fly_mode_enabled"), ((Player) sender)).replace("%player%", ((Player) sender).getName()));
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }
        } else {
            //console:
            if(args.length > 0){
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                        if(target.isFlying()) {
                            target.setAllowFlight(false);
                            target.setFlying(false);
                            target.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.fly_mode_disabled"), target));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.success.fly_mode_disabled_others")).replace("%player%", target.getName()));
                        } else {
                            target.setAllowFlight(true);
                            target.setFlying(true);
                            target.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.fly_mode_enabled"), target));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.success.fly_mode_enabled_others")).replace("%player%", target.getName()));
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.other.use_fly_command")));
            }
        }
        return true;
    }
}