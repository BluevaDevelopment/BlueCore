package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class FlyCommand implements CommandExecutor {

    private Main main;

    public FlyCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) && args.length != 1) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_fly_command")));
            return true;
        }

        Player target;
        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(MessagesUtil.format((Player) sender, main.configManager.getLang().getString("messages.error.player_offline")));
                return true;
            }
            if (!sender.hasPermission("bluecore.fly.others") || !sender.hasPermission("bluecore.fly.*") || !sender.hasPermission("bluecore.*")) {
                sender.sendMessage(MessagesUtil.format((Player) sender, main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }
        } else {
            target = (Player) sender;
            if (!sender.hasPermission("bluecore.fly") || !sender.hasPermission("bluecore.fly.*") || !sender.hasPermission("bluecore.*")) {
                sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }
        }

        if(target.isFlying()) {
            target.setAllowFlight(false);
            target.setFlying(false);
            target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.fly_mode_disabled")));
        } else {
            target.setAllowFlight(true);
            target.setFlying(true);
            target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.fly_mode_enabled")));
        }
        if (args.length == 1) {
            if(target.isFlying()) {
                sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.fly_mode_disabled_others")).replace("%player%", target.getName()));
            } else {
                sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.fly_mode_enabled_others")).replace("%player%", target.getName()));
            }
        }

        return true;
    }
}