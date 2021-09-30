package org.jachi.whirss.thenexus.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jachi.whirss.thenexus.Main;

public class OnPlaceBlock implements Listener {

    private Main main;

    public OnPlaceBlock(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OnPlayerPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(!main.getWorlds().getBoolean("worlds." + worldname + ".build")) {
            if(player.hasPermission("thenexus.*") ||
                    player.hasPermission("thenexus.worldmanager.bypass") ||
                    player.hasPermission("thenexus.worldmanager.*")){
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }

    }
}
