package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;

public class GodCommand implements CommandExecutor {

    private Main main;

    public GodCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        //player:
        if((sender instanceof Player)) {
            if(args.length > 0){
                if(sender.hasPermission("xtremecore.*") ||
                        sender.hasPermission("xtremecore.god.*") ||
                        sender.hasPermission("xtremecore.god") ||
                        sender.hasPermission("xtremecore.god.others")){
                    if(args.length == 1){
                        Player target = Bukkit.getPlayer(args[0]);
                        if(target != null){
                            if(main.getUserdata(target.getUniqueId()).getBoolean("godMode")) {
                                target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.god_mode_disabled"), target));
                                sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.god_mode_disabled_other"), target).replace("%player%", target.getName()));
                                main.getUserdata(target.getUniqueId()).set("godMode", false);
                                main.saveUserdata();
                            } else {
                                target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.god_mode_enabled"), target));
                                sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.god_mode_enabled_other"), target).replace("%player%", target.getName()));
                                main.getUserdata(target.getUniqueId()).set("godMode", true);
                                main.saveUserdata();
                            }
                        } else {
                            sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("console.error.player_offline"), ((Player) sender)));
                        }
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }else{
                if(sender.hasPermission("xtremecore.*") ||
                        sender.hasPermission("xtremecore.god.*") ||
                        sender.hasPermission("xtremecore.god")) {
                    ((Player) sender).setFoodLevel(20);
                    if(main.getUserdata(((Player) sender).getUniqueId()).getBoolean("godMode")) {
                        sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.god_mode_disabled"), ((Player) sender)).replace("%player%", ((Player) sender).getName()));
                        main.getUserdata(((Player) sender).getUniqueId()).set("godMode", false);
                        main.saveUserdata();
                    } else {
                        main.getUserdata(((Player) sender).getUniqueId()).set("godMode", true);
                        sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.god_mode_enabled"), ((Player) sender)).replace("%player%", ((Player) sender).getName()));
                        main.saveUserdata();
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }
        } else {

            //console:
            if(args.length > 0){
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                        if(main.getUserdata(target.getUniqueId()).getBoolean("godMode")) {
                            main.getUserdata(target.getUniqueId()).set("godMode", false);
                            main.saveUserdata();
                            target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.god_mode_disabled"), target));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.success.god_mode_disabled_others")).replace("%player%", target.getName()));
                        } else {
                            main.getUserdata(target.getUniqueId()).set("godMode", true);
                            main.saveUserdata();
                            target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.god_mode_enabled"), target));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.success.god_mode_enabled_others")).replace("%player%", target.getName()));
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_god_command")));
            }
        }
        return true;
    }
}