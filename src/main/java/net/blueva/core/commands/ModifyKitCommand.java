package net.blueva.core.commands;

import net.blueva.core.Main;
import net.blueva.core.managers.KitsManager;
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ModifyKitCommand implements CommandExecutor {

    private Main main;

    public ModifyKitCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.error.only_player")));
            return true;
        }

        if (args.length < 2 || args.length > 2) {
            sender.sendMessage(MessagesUtil.format(null, main.configManager.getLang().getString("messages.other.use_modifykit_command")));
            return true;
        }

        Player player = (Player) sender;
        String kitname = args[0];
        String delay = args[1];
        int delayInt = 0;

        try {
            delayInt = Integer.parseInt(delay);
        } catch (NumberFormatException e) {
            
        }

        if (!sender.hasPermission("bluecore.modifykit")) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.no_perms")));
            return true;
        }

        if(!KitsManager.kitExists(kitname)) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.kit_not_found").replace("%kit_name%", kitname)));
            return true;
        }

        List<ItemStack> items = new ArrayList<>();
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && !item.getType().equals(Material.AIR)) {
                items.add(item.clone());
            }
        }

        KitsManager.modifyKit(kitname, "bluecore.kit."+kitname, delayInt, items);
        player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.success.kit_modified").replace("%kit_name%", kitname)));

        return true;
    }
}