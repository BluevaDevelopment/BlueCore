package es.xtreme.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import es.xtreme.core.Main;
import es.xtreme.core.utils.MessageUtil;

public class SetSpawnCommand implements CommandExecutor {

    private Main main;

    public SetSpawnCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to run this command.");
            return true;
        }

        final Player player = (Player)sender;
        if(args.length > 0){
            if(sender.hasPermission("thenexus.*") ||
                    sender.hasPermission("thenexus.setspawn")) {
                if(args.length == 1){
                    if(main.getWarps().isSet("warps."+ args[0])) {
                        main.getWarps().set("spawn", args[0]);
                        main.saveWarps();
                        main.reloadWarps();
                        player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.spawn_set").replace("%warp%", args[0]), player));
                    } else {
                        player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.unknown_warp"), player));
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.other.use_setspawn_command"), player));
                }
            }
        }
        return true;
    }
}
