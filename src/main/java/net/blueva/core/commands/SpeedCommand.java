package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class SpeedCommand implements CommandExecutor {

    private Main main;

    public SpeedCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_speed_command")));
            return true;
        }

        float speed;
        try {
            speed = Float.parseFloat(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.invalid_speed")));
            return true;
        }

        Player target;
        if (args.length == 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.player_offline")));
                return true;
            }
            if (!sender.hasPermission("bluecore.speed.others")) {
                sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }
        } else {
            target = (Player) sender;
            if (!sender.hasPermission("bluecore.speed")) {
                sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }
        }

        target.setWalkSpeed(speed);
        target.setFlySpeed(speed);
        target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.speed_changed").replace("%speed%", String.valueOf(speed))));
        if (args.length == 2) {
            sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.speed_changed_others").replace("%speed%", String.valueOf(speed)).replace("%player%", target.getName())));
        }

        return true;
    }
}