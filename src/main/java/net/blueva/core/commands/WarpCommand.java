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
        Player finalsender = null;
        if(sender instanceof Player) {
            finalsender = (Player) sender;
        }

        if (args.length > 0) {
            if (args.length == 2) {
                String warp = args[0];
                Player target = Bukkit.getPlayer(args[1]);
                teleportWarp(sender, target, warp);
                sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.teleported_to_warp_others").replace("%warp%", warp).replace("%player%", target.getName())));
            } else if (args.length == 1) {
                if (sender instanceof Player) {
                    String warp = args[0];
                    Player player = (Player) sender;
                    teleportWarp(sender, player, warp);
                } else {
                    sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_warp_command")));
                }
            } else {
                sender.sendMessage(MessagesUtil.format(finalsender, main.configManager.getLang().getString("messages.other.use_warp_command")));
            }
        } else {
            sender.sendMessage(MessagesUtil.format(finalsender, main.configManager.getLang().getString("messages.other.use_warp_command")));
        }

        return true;
    }

    private void teleportWarp(CommandSender sender, Player target, String warp) {
        if (sender.hasPermission("bluecore.warp." + warp)) {
            if (main.configManager.getWarps().isSet("warps." + warp + ".world")) {
                String world = main.configManager.getWarps().getString("warps." + warp + ".world");
                double x = Double.parseDouble(main.configManager.getWarps().getString("warps." + warp + ".x"));
                double y = Double.parseDouble(main.configManager.getWarps().getString("warps." + warp + ".y"));
                double z = Double.parseDouble(main.configManager.getWarps().getString("warps." + warp + ".z"));
                float yaw = Float.parseFloat(main.configManager.getWarps().getString("warps." + warp + ".yaw"));
                float pitch = Float.parseFloat(main.configManager.getWarps().getString("warps." + warp + ".pitch"));
                Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
                target.teleport(loc);
                target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.teleported_to_warp").replace("%warp%", warp)));
            }
        }
    }
}