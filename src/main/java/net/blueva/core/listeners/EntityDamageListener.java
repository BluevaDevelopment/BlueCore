package net.blueva.core.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import net.blueva.core.Main;

public class EntityDamageListener implements Listener {

    private Main main;

    public EntityDamageListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OED(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String worldname = player.getWorld().getName();
            if (!main.configManager.getWorlds().getBoolean("worlds." + worldname + ".fall_damage")) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    event.setCancelled(!player.hasPermission("bluecore.*") &&
                            !player.hasPermission("bluecore.worldmanager.bypass.*") &&
                            !player.hasPermission("bluecore.worldmanager.bypass.fall_damage") &&
                            !player.hasPermission("bluecore.worldmanager.*"));
                }
            }

            if (!main.configManager.getWorlds().getBoolean("worlds." + worldname + ".pvp")) {
                if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    if (event.getEntityType() == EntityType.PLAYER) {
                        event.setCancelled(!player.hasPermission("bluecore.*") &&
                                !player.hasPermission("bluecore.worldmanager.bypass.*") &&
                                !player.hasPermission("bluecore.worldmanager.bypass.pvp") &&
                                !player.hasPermission("bluecore.worldmanager.*"));
                    }
                }
            }

            if(main.configManager.getUser(player.getUniqueId()).getBoolean("godMode")) {
                event.setCancelled(true);
            }
        }
    }
}
