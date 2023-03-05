package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class GodCommand implements CommandExecutor {

    private Main main;

    public GodCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) && args.length != 1) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_god_command")));
            return true;
        }

        Player target;
        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(MessagesUtil.format((Player) sender, main.configManager.getLang().getString("messages.error.player_offline")));
                return true;
            }
            if (!sender.hasPermission("bluecore.god.others")) {
                sender.sendMessage(MessagesUtil.format((Player) sender, main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }
        } else {
            target = (Player) sender;
            if (!sender.hasPermission("bluecore.god")) {
                sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }
        }

        if(main.configManager.getUser(target.getUniqueId()).getBoolean("godMode")) {
            main.configManager.getUser(target.getUniqueId()).set("godMode", false);
            main.configManager.saveUser(target.getUniqueId());
            main.configManager.reloadUser(target.getUniqueId());
            target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.god_mode_disabled")));
        } else {
            main.configManager.getUser(target.getUniqueId()).set("godMode", true);
            main.configManager.saveUser(target.getUniqueId());
            main.configManager.reloadUser(target.getUniqueId());
            target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.god_mode_enabled")));
        }
        if (args.length == 1) {
            if(main.configManager.getUser(target.getUniqueId()).getBoolean("godMode")) {
                sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.god_mode_disabled_others")).replace("%player%", target.getName()));
            } else {
                sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.god_mode_enabled_others")).replace("%player%", target.getName()));
            }
        }

        return true;
    }
}