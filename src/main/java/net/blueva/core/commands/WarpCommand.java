package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class WarpCommand implements CommandExecutor {

    private final Main main;

    public WarpCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //player:
        if ((sender instanceof Player)) {
            if (args.length > 0) {
                    if (args.length == 2) {
                        if (sender.hasPermission("bluecore.*") ||
                                sender.hasPermission("bluecore.warp.*") ||
                                sender.hasPermission("bluecore.warp."+args[0])) {
                        String warp = args[0];
                        Player target = Bukkit.getPlayer(args[1]);

                        if(main.configManager.getWarps().isSet("warps."+warp+".world")) {
                            String world = main.configManager.getWarps().getString("warps."+warp+".world");
                            double x = Double.valueOf(main.configManager.getWarps().getString("warps."+warp+".x"));
                            double y = Double.valueOf(main.configManager.getWarps().getString("warps."+warp+".y"));
                            double z = Double.valueOf(main.configManager.getWarps().getString("warps."+warp+".z"));
                            float yaw = Float.valueOf(main.configManager.getWarps().getString("warps."+warp+".yaw"));
                            float pitch = Float.valueOf(main.configManager.getWarps().getString("warps."+warp+".pitch"));
                            Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                            if (target != null) {
                                target.teleport(loc);
                                target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.teleported_to_warp").replace("%warp%", warp)));
                                sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.teleported_to_warp_others").replace("%warp%", warp).replace("%player%", target.getName())));
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                            }
                        } else {
                            sender.sendMessage(MessagesUtil.format((Player) sender, main.configManager.getLang().getString("messages.error.unknown_warp")));
                        }
                    } else {
                            sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                        }
                } else if (args.length == 1) {
                        if (sender.hasPermission("bluecore.*") ||
                                sender.hasPermission("bluecore.warp.*") ||
                                sender.hasPermission("bluecore.warp."+args[0])) {
                            String warp = args[0];
                            String world = main.configManager.getWarps().getString("warps."+warp+".world");

                            if(main.configManager.getWarps().isSet("warps."+warp+".world")) {
                                double x = Double.valueOf(main.configManager.getWarps().getString("warps."+warp+".x"));
                                double y = Double.valueOf(main.configManager.getWarps().getString("warps."+warp+".y"));
                                double z = Double.valueOf(main.configManager.getWarps().getString("warps."+warp+".z"));
                                float yaw = Float.valueOf(main.configManager.getWarps().getString("warps."+warp+".yaw"));
                                float pitch = Float.valueOf(main.configManager.getWarps().getString("warps."+warp+".pitch"));
                                Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                                ((Player) sender).teleport(loc);
                                sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.success.teleported_to_warp").replace("%warp%", warp)));
                            } else {
                                sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.unknown_warp")));
                            }
                        } else {
                            sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                        }
                    } else {
                        sender.sendMessage(MessagesUtil.format(((Player) sender).getPlayer(), main.configManager.getLang().getString("messages.other.use_warp_command").replace("%warp%", args[0])));
                    }
            }
        } else {

            //console:
            if (args.length > 0){
                if (args.length == 2) {
                    String warp = args[0];
                    Player target = Bukkit.getPlayer(args[1]);

                    if(main.configManager.getWarps().isSet("warps."+warp+".world")) {
                        String world = main.configManager.getWarps().getString("warps."+warp+".world");
                        double x = Double.valueOf(main.configManager.getWarps().getString("warps."+warp+".x"));
                        double y = Double.valueOf(main.configManager.getWarps().getString("warps."+warp+".y"));
                        double z = Double.valueOf(main.configManager.getWarps().getString("warps."+warp+".z"));
                        float yaw = Float.valueOf(main.configManager.getWarps().getString("warps."+warp+".yaw"));
                        float pitch = Float.valueOf(main.configManager.getWarps().getString("warps."+warp+".pitch"));
                        Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                        if (target != null) {
                            target.teleport(loc);
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("messages.success.teleported_to_warp").replace("%warp%", warp)));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("messages.success.teleported_to_warp_others").replace("%warp%", warp).replace("%player%", target.getName())));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.unknown_warp")));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.other.use_warp_command")));
                }
            }
        }
        return true;
    }
}