package org.jachi.whirss.thenexus.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.jachi.whirss.thenexus.Main;

public class OnEntitySpawn implements Listener {

    private Main main;

    public OnEntitySpawn(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OnEntitySpawn(EntitySpawnEvent event) {
        Player player = (Player) event.getEntity();
        World world = player.getWorld();
        if(world.equals(main.getWorlds().getStringList("worlds")) && main.getWorlds().getBoolean("worlds." + world + ".mob_spawning") && !(event.getEntity() instanceof Player)) {
            event.setCancelled(true);
        }

    }
}
