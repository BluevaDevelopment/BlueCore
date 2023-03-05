package net.blueva.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import net.blueva.core.Main;

public class BlockBreakListener implements Listener {

    private final Main main;

    public BlockBreakListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OBB(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(main.configManager.getWorlds().getBoolean("worlds." + worldname + ".break")) {
            event.setCancelled(player.hasPermission("bluecore.worldmanager.bypass.break"));
        }

    }
}
