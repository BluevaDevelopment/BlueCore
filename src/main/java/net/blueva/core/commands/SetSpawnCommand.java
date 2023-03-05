package net.blueva.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class SetSpawnCommand implements CommandExecutor {

    private Main main;

    public SetSpawnCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = null;
        if(sender instanceof Player) {
            player = (Player)sender;
        }

        if(!sender.hasPermission("bluecore.setspawn")) {
            sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_permission")));
            return true;
        }

        if(args.length != 1) {
            sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_setspawn_command")));
            return true;
        }

        if(!main.configManager.getWarps().isSet("warps." + args[0])) {
            sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.unknown_warp")));
            return true;
        }

        main.configManager.getWarps().set("spawn", args[0]);
        main.configManager.saveWarps();
        main.configManager.reloadWarps();
        sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.success.spawn_set").replace("%warp%", args[0])));
        return true;
    }
}
