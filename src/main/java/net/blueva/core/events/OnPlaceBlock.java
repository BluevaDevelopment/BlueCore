package net.blueva.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import net.blueva.core.Main;

public class OnPlaceBlock implements Listener {

    private final Main main;

    public OnPlaceBlock(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OPB(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(!main.configManager.getWorlds().getBoolean("worlds." + worldname + ".build")) {
            event.setCancelled(!player.hasPermission("bluecore.*") &&
                    !player.hasPermission("bluecore.worldmanager.bypass.*") &&
                    !player.hasPermission("bluecore.worldmanager.bypass.build") &&
                    !player.hasPermission("bluecore.worldmanager.*"));
        }

    }
}
