package es.xtreme.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import es.xtreme.core.Main;

public class OnPlayerInteract implements Listener {

    private Main main;

    public OnPlayerInteract(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(!main.getWorlds().getBoolean("worlds." + worldname + ".interact")) {
            if(player.hasPermission("xtremecore.*") ||
                    player.hasPermission("xtremecore.worldmanager.bypass.*") ||
                    player.hasPermission("xtremecore.worldmanager.bypass.interact") ||
                    player.hasPermission("xtremecore.worldmanager.*")){
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }

    }
}
