package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class SunCommand implements CommandExecutor {

    private Main main;

    public SunCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        //player:
        if((sender instanceof Player)) {
            if(args.length > 0){
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.weather.*") ||
                        sender.hasPermission("bluecore.weather.clear") ){
                    if(args.length == 1){
                        World world = Bukkit.getWorld(args[0]);
                        if (world == null) {
                            sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.invalid_world")));
                        } else {
                            world.setStorm(false);
                            sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.success.changed_weather"))
                                    .replace("%world%", args[0])
                                    .replace("%weather%", "Clear"));
                        }
                    }
                } else {
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                }
            }else{
                if(sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.weather.*") ||
                        sender.hasPermission("bluecore.weather.clear") ){
                    ((Player) sender).getWorld().setStorm(false);
                    sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.success.changed_weather"))
                            .replace("%world%", ((Player) sender).getWorld().getName())
                            .replace("%weather%", "Clear"));
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
                        world.setTime(1000);
                        world.setStorm(false);
                        sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("console.success.changed_weather"))
                                .replace("%world%", ((Player) sender).getWorld().getName())
                                .replace("%weather%", "Clear"));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.other.use_sun_command")));
            }
        }
        return true;
    }
}