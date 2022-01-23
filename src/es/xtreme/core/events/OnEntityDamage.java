package es.xtreme.core.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import es.xtreme.core.Main;

public class OnEntityDamage implements Listener {

    private Main main;

    public OnEntityDamage(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OnEntityDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            String worldname = player.getWorld().getName();
            if (!main.getWorlds().getBoolean("worlds." + worldname + ".fall_damage")) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if (player.hasPermission("xtremecore.*") ||
                            player.hasPermission("xtremecore.worldmanager.bypass.*") ||
                            player.hasPermission("xtremecore.worldmanager.bypass.fall_damage") ||
                            player.hasPermission("xtremecore.worldmanager.*")) {
                        event.setCancelled(false);
                    } else {
                        event.setCancelled(true);
                    }
                }
            }

            if (!main.getWorlds().getBoolean("worlds." + worldname + ".pvp")) {
                if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    if (event.getEntityType() == EntityType.PLAYER) {
                        if (player.hasPermission("xtremecore.*") ||
                                player.hasPermission("xtremecore.worldmanager.bypass.*") ||
                                player.hasPermission("xtremecore.worldmanager.bypass.pvp") ||
                                player.hasPermission("xtremecore.worldmanager.*")) {
                            event.setCancelled(false);
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            }

            if(main.getUserdata(player.getUniqueId()).getBoolean("godMode")) {
                event.setCancelled(true);
            }
        }
    }
}
