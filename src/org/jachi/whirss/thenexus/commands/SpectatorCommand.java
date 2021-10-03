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

public class SpectatorCommand implements CommandExecutor {

    private Main main;

    public SpectatorCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        //final Player player = (Player)sender;

        if(args.length > 0){
            if (!(sender instanceof Player)) {
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                        target.setGameMode(GameMode.SPECTATOR);
                        target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "SPECTATOR"), target));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.success.other_player_gamemode_changed")).replace("%gamemode%", "SPECTATOR").replace("%player%", target.getName()));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                    }
                }
                return true;
            }
            if(sender.hasPermission("thenexus.*") ||
                    sender.hasPermission("thenexus.gamemode.spectator") ||
                    sender.hasPermission("thenexus.gamemode.*")){
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                        target.setGameMode(GameMode.SPECTATOR);
                        target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "SPECTATOR"), target));
                        sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.other_player_gamemode_changed").replace("%gamemode%", "SPECTATOR").replace("%player%", target.getName()), target));
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
                        sender.hasPermission("thenexus.gamemode.spectator") ||
                        sender.hasPermission("thenexus.gamemode.*")){
                    ((Player) sender).setGameMode(GameMode.SPECTATOR);
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.gamemode_changed").replace("%gamemode%", "SPECTATOR"), ((Player) sender)));
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_spectator_command")));
            }
        }
        return true;
    }

}