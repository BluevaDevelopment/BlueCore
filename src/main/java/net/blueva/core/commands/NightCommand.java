package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class NightCommand implements CommandExecutor {

    private Main main;

    public NightCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        //player:
        if((sender instanceof Player)) {
            if(args.length > 0){
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.time.*") ||
                        sender.hasPermission("bluecore.time.night.others")){
                    if(args.length == 1){
                        World world = Bukkit.getWorld(args[0]);
                        if (world == null) {
                            sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.invalid_world")));
                        } else {
                            world.setTime(13000);
                            sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.success.changed_time"))
                                    .replace("%world%", args[0])
                                    .replace("%time%", "Night")
                                    .replace("%ticks%", "13000"));
                        }
                    }
                } else {
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                }
            }else{
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.time.*") ||
                        sender.hasPermission("bluecore.time.night")) {
                    ((Player) sender).getWorld().setTime(13000);
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.success.changed_time"))
                            .replace("%world%", ((Player) sender).getWorld().getName())
                            .replace("%time%", "Night")
                            .replace("%ticks%", "13000"));
                } else {
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                }
            }
        } else {
            //console:
            if(args.length > 0){
                if(args.length == 1){
                    World world = Bukkit.getWorld(args[0]);
                    if (world == null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.invalid_world")));
                    } else {
                        world.setTime(13000);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.success.changed_time"))
                                .replace("%world%", args[0])
                                .replace("%time%", "Night")
                                .replace("%ticks%", "13000"));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.other.use_night_command")));
            }
        }
        return true;
    }
}