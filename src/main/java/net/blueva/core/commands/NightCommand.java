package net.blueva.core.commands;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NightCommand implements CommandExecutor {

    private final Main main;

    public NightCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        World world = null;
        if (args.length > 0) {
            world = Bukkit.getWorld(args[0]);
            Bukkit.broadcastMessage(String.valueOf(args.length));
        }
        if (sender instanceof Player) {
            if (world == null) {
                world = ((Player) sender).getWorld();
            }
            if (!sender.hasPermission("bluecore.time.night" + (world == ((Player) sender).getWorld() ? "" : ".others"))) {
                sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }
            world.setTime(13000);
            sender.sendMessage(MessagesUtil.format(((Player) sender), main.configManager.getLang().getString("messages.success.changed_time"))
                    .replace("%world%", world.getName())
                    .replace("%time%", "Night")
                    .replace("%ticks%", "13000"));
        } else {
            if (world == null) {
                sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_noon_command")));
                return true;
            }
            world.setTime(13000);
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.success.changed_time"))
                    .replace("%world%", world.getName())
                    .replace("%time%", "Night")
                    .replace("%ticks%", "13000"));
        }
        return true;
    }
}