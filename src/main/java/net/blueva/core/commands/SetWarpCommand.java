package net.blueva.core.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class SetWarpCommand implements CommandExecutor {

    private Main main;

    public SetWarpCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.only_player")));
            return true;
        }

        final Player player = (Player)sender;
        if(!player.hasPermission("bluecore.setwarp")) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_permission")));
            return true;
        }

        if(args.length < 1) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_setwarp_command")));
            return true;
        }

        if(main.configManager.getWarps().isSet("warps." + args[0])) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.warp_already_set")));
            return true;
        }

        Location l = player.getLocation();
        String world = l.getWorld().getName();
        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();
        float yaw = l.getYaw();
        float pitch = l.getPitch();
        main.configManager.getWarps().set("warps." + args[0] + ".world", world);
        main.configManager.getWarps().set("warps." + args[0] + ".x", x);
        main.configManager.getWarps().set("warps." + args[0] + ".y", y);
        main.configManager.getWarps().set("warps." + args[0] + ".z", z);
        main.configManager.getWarps().set("warps." + args[0] + ".yaw", yaw);
        main.configManager.getWarps().set("warps." + args[0] + ".pitch", pitch);
        main.configManager.saveWarps();
        main.configManager.reloadWarps();
        player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.success.warp_set").replace("%warp%", args[0])));

        return true;
    }
}
