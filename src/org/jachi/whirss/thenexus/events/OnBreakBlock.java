package org.jachi.whirss.thenexus.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jachi.whirss.thenexus.Main;

public class OnBreakBlock implements Listener {

    private Main main;

    public OnBreakBlock(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OnBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(main.getWorlds().getBoolean("worlds." + worldname + ".break")) {
            if(player.hasPermission("thenexus.*") ||
                    player.hasPermission("thenexus.worldmanager.bypass.*") ||
                    player.hasPermission("thenexus.worldmanager.bypass.break") ||
                    player.hasPermission("thenexus.worldmanager.*")){
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }

    }
}
