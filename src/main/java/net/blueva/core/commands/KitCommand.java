package net.blueva.core.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.managers.KitsManager;
import net.blueva.core.utils.DateUtil;
import net.blueva.core.utils.MessagesUtil;

public class KitCommand implements CommandExecutor {

    private final Main main;

    public KitCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) && args.length != 2) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_kit_command")));
            return true;
        }

        if (args.length < 1 || args.length > 2) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_kit_command")));
            return true;
        }

        if (!sender.hasPermission("bluecore.kit")) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.no_perms")));
            return true;
        }

        String kit = args[0];

        if(!KitsManager.kitExists(kit)) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.kit_not_found").replace("%kit_name%", kit)));
            return true;
        }

        Player target;
        if (args.length == 2) {
            target = Bukkit.getPlayer(args[1]);

            if (!sender.hasPermission("bluecore.kit.others") && !sender.hasPermission("bluecore.kit."+kit+".others")) {
                sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }
            
            KitsManager.giveKit(target, kit);

            sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.kit_given_others").replace("%kit_name%", kit).replace("%player%", target.getName())));
            target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.kit_given").replace("%kit_name%", kit)));
        } else if (args.length == 1) {
            target = (Player) sender;

            if (!sender.hasPermission("bluecore.kit."+kit)) {
                sender.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.error.no_perms")));
                return true;
            }

            if(main.configManager.getUser(target.getUniqueId()).isSet("date.kits."+kit)) {
                if(!isFutureKitDatePassed(kit, target)) {
                    sender.sendMessage(MessagesUtil.format(target.getPlayer(), getTimeUntilFutureDateAsString(kit, target)));
                    return true;
                } else {
                    removeKitDate(kit, target);
                    setFutureKitDate(kit, target);
                }
            } else {
                setFutureKitDate(kit, target);
            }

            KitsManager.giveKit(target, kit);
            target.sendMessage(MessagesUtil.format(target, main.configManager.getLang().getString("messages.success.kit_given").replace("%kit_name%", kit)));
        } 

        return true;
    }

    private void setFutureKitDate(String kit, Player target) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime futureDate = currentDate.plusSeconds(main.configManager.getKit(kit).getInt("delay"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String futureDateStr = futureDate.format(formatter);

        main.configManager.getUser(target.getUniqueId()).set("date.kits."+kit, futureDateStr);
        main.configManager.saveUser(target.getUniqueId());
        main.configManager.reloadUser(target.getUniqueId());
    }

    private void removeKitDate(String kit, Player target) {
        main.configManager.getUser(target.getUniqueId()).set("date.kits."+kit, null);
        main.configManager.saveUser(target.getUniqueId());
        main.configManager.reloadUser(target.getUniqueId());
    }

    private boolean isFutureKitDatePassed(String kit, Player target) {
        String dateString = main.configManager.getUser(target.getUniqueId()).getString("date.kits."+kit);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime futureDate = LocalDateTime.parse(dateString, formatter);

        if(!DateUtil.isConfigDatePassed(futureDate)) {
            return false;
        } 

        return true;
    }

    private String getTimeUntilFutureDateAsString(String kit, Player target) {
        String dateString = main.configManager.getUser(target.getUniqueId()).getString("date.kits."+kit);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime futureDate = LocalDateTime.parse(dateString, formatter);
        return DateUtil.getTimeUntilFutureDateAsString(futureDate, kit);
    }
    
}
