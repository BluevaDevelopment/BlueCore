package net.blueva.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class SuicideCommand implements CommandExecutor {

    private Main main;

    public SuicideCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.only_player")));
            return true;
        }

        if (!sender.hasPermission("bluecore.suicide")) {
            sender.sendMessage(MessagesUtil.format((Player) sender, main.configManager.getLang().getString("messages.error.no_perms")));
            return true;
        }

        Player target = (Player) sender;
        target.setHealth(0);
        target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.completed_suicide")));

        return true;
    }
}