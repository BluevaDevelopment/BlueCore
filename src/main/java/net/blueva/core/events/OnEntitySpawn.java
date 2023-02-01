package net.blueva.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import net.blueva.core.Main;

public class OnEntitySpawn implements Listener {

    private Main main;

    public OnEntitySpawn(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OnEntitySpawn(EntitySpawnEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String worldname = player.getWorld().getName();
            if(!main.getWorlds().getBoolean("worlds." + worldname + ".mob_spawning") && !(event.getEntity() instanceof Player)) {
                event.setCancelled(true);
            }
        }
    }
}
