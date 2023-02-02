package net.blueva.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;

public class DeleteWarpCommand implements CommandExecutor {

    private Main main;

    public DeleteWarpCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to run this command.");
            return true;
        }

        final Player player = (Player)sender;
        if(args.length > 0){
            if(sender.hasPermission("bluecore.*") ||
                    sender.hasPermission("bluecore.updatewarp")) {
                if(args.length == 1){
                    if(main.configManager.getWarps().isSet("warps."+ args[0])) {
                        main.configManager.getWarps().set("warps."+ args[0], null);
                        main.configManager.saveWarps();
                        main.configManager.reloadWarps();
                        player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.warp_deleted").replace("%warp%", args[0]), player));
                    } else {
                        player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.unknown_warp"), player));
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.other.use_deletewarp_command"), player));
                }
            }
        }
        return true;
    }
}
