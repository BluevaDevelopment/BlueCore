package net.blueva.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import net.blueva.core.Main;

public class OnPlayerDropItem implements Listener {

    private final Main main;

    public OnPlayerDropItem(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OPDI(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(!main.configManager.getWorlds().getBoolean("worlds." + worldname + ".drop_items")) {
            event.setCancelled(!player.hasPermission("bluecore.*") &&
                    !player.hasPermission("bluecore.worldmanager.bypass.*") &&
                    !player.hasPermission("bluecore.worldmanager.bypass.drop_items") &&
                    !player.hasPermission("bluecore.worldmanager.*"));
        }

    }
}
