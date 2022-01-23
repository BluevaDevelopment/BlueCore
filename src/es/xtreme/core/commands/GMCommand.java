package es.xtreme.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import es.xtreme.core.Main;
import es.xtreme.core.utils.MessageUtil;

public class GMCommand implements CommandExecutor {

    private Main main;

    public GMCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("survival") ||
                    args[0].equalsIgnoreCase("surv") ||
                    args[0].equalsIgnoreCase("su") ||
                    args[0].equalsIgnoreCase("s")){
                if (!(sender instanceof Player)) {
                    if(args.length == 1){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_gm_command")));
                    }
                    if(args.length == 2){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null){
                            target.setGameMode(GameMode.SURVIVAL);
                            target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "SURVIVAL"), target));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.success.gamemode_changed_others")).replace("%gamemode%", "SURVIVAL").replace("%player%", target.getName()));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                        }
                    }
                    return true;
                }
                if(sender.hasPermission("xtremecore.*") ||
                        sender.hasPermission("xtremecore.gamemode.survival") ||
                        sender.hasPermission("xtremecore.gamemode.*")){
                    if(args.length == 1) {
                        ((Player) sender).setGameMode(GameMode.SURVIVAL);
                        sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "SURVIVAL"), ((Player) sender)));
                    }
                    if(args.length == 2){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null){
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
            }else if(args[0].equalsIgnoreCase("creative") ||
                    args[0].equalsIgnoreCase("crea") ||
                    args[0].equalsIgnoreCase("cr") ||
                    args[0].equalsIgnoreCase("c")){
                if (!(sender instanceof Player)) {
                    if(args.length == 1){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_gm_command")));
                    }
                    if(args.length == 2){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null){
                            target.setGameMode(GameMode.CREATIVE);
                            target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "CREATIVE"), target));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.success.gamemode_changed_others")).replace("%gamemode%", "CREATIVE").replace("%player%", target.getName()));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                        }
                    }
                    return true;
                }
                if(sender.hasPermission("xtremecore.*") ||
                        sender.hasPermission("xtremecore.gamemode.creative") ||
                        sender.hasPermission("xtremecore.gamemode.*")){
                    if(args.length == 1) {
                        ((Player) sender).setGameMode(GameMode.CREATIVE);
                        sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "CREATIVE"), ((Player) sender)));
                    }
                    if(args.length == 2){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null){
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
            }else if(args[0].equalsIgnoreCase("adventure") ||
                    args[0].equalsIgnoreCase("adven") ||
                    args[0].equalsIgnoreCase("adv") ||
                    args[0].equalsIgnoreCase("ad") ||
                    args[0].equalsIgnoreCase("a")){
                if (!(sender instanceof Player)) {
                    if(args.length == 1){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_gm_command")));
                    }
                    if(args.length == 2){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null){
                            target.setGameMode(GameMode.ADVENTURE);
                            target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "ADVENTURE"), target));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.success.gamemode_changed_others")).replace("%gamemode%", "ADVENTURE").replace("%player%", target.getName()));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                        }
                    }
                    return true;
                }
                if(sender.hasPermission("xtremecore.*") ||
                        sender.hasPermission("xtremecore.gamemode.adventure") ||
                        sender.hasPermission("xtremecore.gamemode.*")){
                    if(args.length == 1) {
                        ((Player) sender).setGameMode(GameMode.ADVENTURE);
                        sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "ADVENTURE"), ((Player) sender)));
                    }
                    if(args.length == 2){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null){
                            target.setGameMode(GameMode.ADVENTURE);
                            target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "ADVENTURE"), target));
                            sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed_others").replace("%gamemode%", "ADVENTURE").replace("%player%", target.getName()), target));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                        }
                    }

                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }else if(args[0].equalsIgnoreCase("spectator") ||
                    args[0].equalsIgnoreCase("spec") ||
                    args[0].equalsIgnoreCase("sp")){
                if (!(sender instanceof Player)) {
                    if(args.length == 1){
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_gm_command")));
                    }
                    if(args.length == 2){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null){
                            target.setGameMode(GameMode.SPECTATOR);
                            target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "SPECTATOR"), target));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.success.gamemode_changed_others")).replace("%gamemode%", "SPECTATOR").replace("%player%", target.getName()));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                        }
                    }
                    return true;
                }
                if(sender.hasPermission("xtremecore.*") ||
                        sender.hasPermission("xtremecore.gamemode.spectator") ||
                        sender.hasPermission("xtremecore.gamemode.*")){
                    if(args.length == 1) {
                        ((Player) sender).setGameMode(GameMode.SPECTATOR);
                        sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "SPECTATOR"), ((Player) sender)));
                    }
                    if(args.length == 2){
                        Player target = Bukkit.getPlayer(args[1]);
                        if(target != null){
                            target.setGameMode(GameMode.SPECTATOR);
                            target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "SPECTATOR"), target));
                            sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed_others").replace("%gamemode%", "SPECTATOR").replace("%player%", target.getName()), target));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                        }
                    }

                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }else{
                if (!(sender instanceof Player)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_gm_command")));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("messages.other.use_gm_command")));
                }
            }
        }else{
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_gm_command")));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("messages.other.use_gm_command")));
            }
        }
        return true;
    }

}