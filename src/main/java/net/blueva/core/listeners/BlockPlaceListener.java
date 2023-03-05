package net.blueva.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import net.blueva.core.Main;

public class BlockPlaceListener implements Listener {

    private final Main main;

    public BlockPlaceListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OPB(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(!main.configManager.getWorlds().getBoolean("worlds." + worldname + ".build")) {
            event.setCancelled(!player.hasPermission("bluecore.worldmanager.bypass.build"));
        }

    }
}
