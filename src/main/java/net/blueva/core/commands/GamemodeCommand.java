package net.blueva.core.commands;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {

    private Main main;

    public GamemodeCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = null;
        if(sender instanceof Player) {
            player = (Player)sender;
        }

        if(args.length == 0) {
            sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_gm_command")));
            return true;
        }
        String gamemode = args[0].toLowerCase();
        GameMode mode = null;
        switch(gamemode) {
            case "survival":
            case "surv":
            case "su":
            case "s":
                mode = GameMode.SURVIVAL;
                break;
            case "creative":
            case "crea":
            case "cr":
            case "c":
                mode = GameMode.CREATIVE;
                break;
            case "adventure":
            case "adven":
            case "adv":
            case "a":
                mode = GameMode.ADVENTURE;
                break;
            case "spectator":
            case "spect":
            case "spec":
            case "sp":
                mode = GameMode.SPECTATOR;
                break;
            default:
                sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.gamemode_invalid")));
                return true;
        }
        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.other.use_gm_command")));
            }
            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    target.setGameMode(mode);
                    target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.gamemode_changed").replace("%gamemode%", mode.toString())));
                    sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.success.gamemode_changed_others")).replace("%gamemode%", mode.toString()).replace("%player%", target.getName()));
                } else {
                    sender.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.player_not_found")).replace("%player%", args[1]));
                }
            }
        } else {
            if(args.length == 1) {
                player.setGameMode(mode);
                player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.success.gamemode_changed").replace("%gamemode%", mode.toString())));
            }
            if(args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if(target != null) {
                    target.setGameMode(mode);
                    target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.gamemode_changed").replace("%gamemode%", mode.toString())));
                    player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.success.gamemode_changed_others")).replace("%gamemode%", mode.toString()).replace("%player%", target.getName()));
                } else {
                    player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.player_not_found")).replace("%player%", args[1]));
                }
            }
        }
        return true;
    }
}
