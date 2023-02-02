package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;

public class SpawnCommand implements CommandExecutor {

    private Main main;

    public SpawnCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        //player:
        if ((sender instanceof Player)) {
            if (args.length > 0) {
                if (sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.spawn.*") ||
                        sender.hasPermission("bluecore.spawn") ||
                        sender.hasPermission("bluecore.spawn.others")) {
                    if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        String spawn = main.configManager.getWarps().getString("spawn");
                        if(main.configManager.getWarps().isSet("warps."+spawn+".world")) {
                            String world = main.configManager.getWarps().getString("warps."+spawn+".world");
                            double x = Double.valueOf(main.configManager.getWarps().getString("warps."+spawn+".x"));
                            double y = Double.valueOf(main.configManager.getWarps().getString("warps."+spawn+".y"));
                            double z = Double.valueOf(main.configManager.getWarps().getString("warps."+spawn+".z"));
                            float yaw = Float.valueOf(main.configManager.getWarps().getString("warps."+spawn+".yaw"));
                            float pitch = Float.valueOf(main.configManager.getWarps().getString("warps."+spawn+".pitch"));
                            Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                            if (target != null) {
                                target.teleport(loc);
                                target.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.teleported_to_spawn").replace("%warp%", spawn), target));
                                sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.teleported_to_spawn_others").replace("%warp%", spawn).replace("%player%", target.getName()), target));
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                            }
                        } else {
                            sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.unknown_warp"), (Player) sender));
                        }
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), ((Player) sender)));
                }
            } else {
                if (sender.hasPermission("bluecore.*") ||
                        sender.hasPermission("bluecore.spawn.*") ||
                        sender.hasPermission("bluecore.spawn")) {
                    String spawn = main.configManager.getWarps().getString("spawn");
                    if(main.configManager.getWarps().isSet("warps."+spawn+".world")) {
                        String world = main.configManager.getWarps().getString("warps."+spawn+".world");
                        double x = Double.valueOf(main.configManager.getWarps().getString("warps."+spawn+".x"));
                        double y = Double.valueOf(main.configManager.getWarps().getString("warps."+spawn+".y"));
                        double z = Double.valueOf(main.configManager.getWarps().getString("warps."+spawn+".z"));
                        float yaw = Float.valueOf(main.configManager.getWarps().getString("warps."+spawn+".yaw"));
                        float pitch = Float.valueOf(main.configManager.getWarps().getString("warps."+spawn+".pitch"));
                        Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                        ((Player) sender).teleport(loc);
                        ((Player) sender).sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.teleported_to_spawn").replace("%warp%", spawn), ((Player) sender)));
                    } else {
                        sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.spawn_not_set"), (Player) sender));
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), ((Player) sender)));
                }
            }
        } else {

            //console:
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                String spawn = main.configManager.getWarps().getString("spawn");
                if(main.configManager.getWarps().isSet("warps."+spawn+".world")) {
                    String world = main.configManager.getWarps().getString("warps."+spawn+".world");
                    double x = Double.valueOf(main.configManager.getWarps().getString("warps."+spawn+".x"));
                    double y = Double.valueOf(main.configManager.getWarps().getString("warps."+spawn+".y"));
                    double z = Double.valueOf(main.configManager.getWarps().getString("warps."+spawn+".z"));
                    float yaw = Float.valueOf(main.configManager.getWarps().getString("warps."+spawn+".yaw"));
                    float pitch = Float.valueOf(main.configManager.getWarps().getString("warps."+spawn+".pitch"));
                    Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                    if (target != null) {
                        target.teleport(loc);
                        target.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.teleported_to_spawn").replace("%warp%", spawn), target));
                        sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("console.success.teleported_to_spawn_others").replace("%warp%", spawn).replace("%player%", target.getName()), target));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.error.player_offline")));
                    }
                } else {
                    sender.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("console.error.spawn_not_set"), (Player) sender));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.configManager.getLang().getString("console.other.use_spawn_command")));
            }
        }
        return true;
    }
}