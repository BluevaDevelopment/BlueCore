package es.xtreme.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import es.xtreme.core.Main;
import es.xtreme.core.utils.MessageUtil;

public class SunCommand implements CommandExecutor {

    private Main main;

    public SunCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        //player:
        if((sender instanceof Player)) {
            if(args.length > 0){
                if(sender.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.weather.*") ||
                        sender.hasPermission("thenexus.weather.clear") ){
                    if(args.length == 1){
                        World world = Bukkit.getWorld(args[0]);
                        if (world == null) {
                            sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.invalid_world"), ((Player) sender)));
                        } else {
                            world.setStorm(false);
                            sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.changed_weather"), ((Player) sender))
                                    .replace("%world%", args[0])
                                    .replace("%weather%", "Clear"));
                        }
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }else{
                if(sender.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.weather.*") ||
                        sender.hasPermission("thenexus.weather.clear") ){
                    ((Player) sender).getWorld().setStorm(false);
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.changed_weather"), ((Player) sender))
                            .replace("%world%", ((Player) sender).getWorld().getName())
                            .replace("%weather%", "Clear"));
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }
        } else {
            //console:
            if(args.length > 0){
                if(args.length == 1){
                    World world = Bukkit.getWorld(args[0]);
                    if (world == null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.invalid_world")));
                    } else {
                        world.setTime(1000);
                        world.setStorm(false);
                        sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("console.success.changed_weather"), ((Player) sender))
                                .replace("%world%", ((Player) sender).getWorld().getName())
                                .replace("%weather%", "Clear"));
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_sun_command")));
            }
        }
        return true;
    }
}