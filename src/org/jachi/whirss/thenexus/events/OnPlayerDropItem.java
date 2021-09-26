package org.jachi.whirss.thenexus.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.jachi.whirss.thenexus.Main;

public class OnPlayerDropItem implements Listener {

    private Main main;

    public OnPlayerDropItem(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OnPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        World world = event.getPlayer().getWorld();
        if(world.equals(main.getWorlds().getStringList("worlds")) && main.getWorlds().getBoolean("worlds." + world + ".drop_items")) {
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
