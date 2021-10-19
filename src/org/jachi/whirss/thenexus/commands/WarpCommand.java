package org.jachi.whirss.thenexus.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import org.jachi.whirss.thenexus.Main;
import org.jachi.whirss.thenexus.MessageUtil;

public class WarpCommand implements CommandExecutor {

    private Main main;

    public WarpCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //player:
        if ((sender instanceof Player)) {
            if (args.length > 0) {
                if (sender.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.warp.*") ||
                        sender.hasPermission("thenexus.warp."+args[0])) {
                    if (args.length == 2) {
                        String warp = args[0];
                        Player target = Bukkit.getPlayer(args[1]);

                        if(main.getWarps().isSet("warps."+warp+".world")) {
                            String world = main.getWarps().getString("warps."+warp+".world");
                            double x = Double.valueOf(main.getWarps().getString("warps."+warp+".x"));
                            double y = Double.valueOf(main.getWarps().getString("warps."+warp+".y"));
                            double z = Double.valueOf(main.getWarps().getString("warps."+warp+".z"));
                            float yaw = Float.valueOf(main.getWarps().getString("warps."+warp+".yaw"));
                            float pitch = Float.valueOf(main.getWarps().getString("warps."+warp+".pitch"));
                            Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                            if (target != null) {
                                target.teleport(loc);
                                target.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.teleported_to_warp").replace("%warp%", warp), target));
                                sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.teleported_to_warp_others").replace("%warp%", warp).replace("%player%", target.getName()), target));
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                            }
                        } else {
                            sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.unknown_warp"), (Player) sender));
                        }
                    } else if (args.length == 1) {
                        String warp = args[0];
                        String world = main.getWarps().getString("warps."+warp+".world");

                        if(main.getWarps().isSet("warps."+warp+".world")) {
                            double x = Double.valueOf(main.getWarps().getString("warps."+warp+".x"));
                            double y = Double.valueOf(main.getWarps().getString("warps."+warp+".y"));
                            double z = Double.valueOf(main.getWarps().getString("warps."+warp+".z"));
                            float yaw = Float.valueOf(main.getWarps().getString("warps."+warp+".yaw"));
                            float pitch = Float.valueOf(main.getWarps().getString("warps."+warp+".pitch"));
                            Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                            ((Player) sender).teleport(loc);
                            sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.teleported_to_warp").replace("%warp%", warp), ((Player) sender)));
                        } else {
                            sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.unknown_warp"), (Player) sender));
                        }
                    } else {
                        sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.other.use_warp_command").replace("%warp%", args[0]), ((Player) sender).getPlayer()));
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }
        } else {

            //console:
            if (args.length > 0){
                if (args.length == 2) {
                    String warp = args[0];
                    Player target = Bukkit.getPlayer(args[1]);

                    if(main.getWarps().isSet("warps."+warp+".world")) {
                        String world = main.getWarps().getString("warps."+warp+".world");
                        double x = Double.valueOf(main.getWarps().getString("warps."+warp+".x"));
                        double y = Double.valueOf(main.getWarps().getString("warps."+warp+".y"));
                        double z = Double.valueOf(main.getWarps().getString("warps."+warp+".z"));
                        float yaw = Float.valueOf(main.getWarps().getString("warps."+warp+".yaw"));
                        float pitch = Float.valueOf(main.getWarps().getString("warps."+warp+".pitch"));
                        Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                        if (target != null) {
                            target.teleport(loc);
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("messages.success.teleported_to_warp").replace("%warp%", warp)));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("messages.success.teleported_to_warp_others").replace("%warp%", warp).replace("%player%", target.getName())));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.player_offline")));
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.error.unknown_warp")));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getLanguages().getString("console.other.use_warp_command")));
                }
            }
        }
        return true;
    }
}