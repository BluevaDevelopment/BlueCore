package org.jachi.whirss.thenexus.events;

import org.bukkit.World;
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
        World world = event.getPlayer().getWorld();
        if(world.equals(main.getWorlds().getStringList("worlds")) && main.getWorlds().getBoolean("worlds." + world + ".break")) {
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
