package net.blueva.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import net.blueva.core.Main;

public class EntitySpawnListener implements Listener {

    private final Main main;

    public EntitySpawnListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OES(EntitySpawnEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String worldname = player.getWorld().getName();
            if(!main.configManager.getWorlds().getBoolean("worlds." + worldname + ".mob_spawning") && !(event.getEntity() instanceof Player)) {
                event.setCancelled(true);
            }
        }
    }
}
