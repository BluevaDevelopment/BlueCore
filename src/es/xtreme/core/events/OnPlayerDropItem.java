package es.xtreme.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import es.xtreme.core.Main;

public class OnPlayerDropItem implements Listener {

    private Main main;

    public OnPlayerDropItem(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OnPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(!main.getWorlds().getBoolean("worlds." + worldname + ".drop_items")) {
            if(player.hasPermission("thenexus.*") ||
                    player.hasPermission("thenexus.worldmanager.bypass.*") ||
                    player.hasPermission("thenexus.worldmanager.bypass.drop_items") ||
                    player.hasPermission("thenexus.worldmanager.*")){
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }

    }
}
