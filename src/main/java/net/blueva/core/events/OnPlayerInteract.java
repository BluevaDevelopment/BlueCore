package net.blueva.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import net.blueva.core.Main;

public class OnPlayerInteract implements Listener {

    private final Main main;

    public OnPlayerInteract(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OPI(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(!main.configManager.getWorlds().getBoolean("worlds." + worldname + ".interact")) {
            event.setCancelled(!player.hasPermission("bluecore.*") &&
                    !player.hasPermission("bluecore.worldmanager.bypass.*") &&
                    !player.hasPermission("bluecore.worldmanager.bypass.interact") &&
                    !player.hasPermission("bluecore.worldmanager.*"));
        }

    }
}
