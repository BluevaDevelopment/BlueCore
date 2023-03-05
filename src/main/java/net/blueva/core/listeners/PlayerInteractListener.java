package net.blueva.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import net.blueva.core.Main;

public class PlayerInteractListener implements Listener {

    private final Main main;

    public PlayerInteractListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OPI(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(!main.configManager.getWorlds().getBoolean("worlds." + worldname + ".interact")) {
            event.setCancelled(!player.hasPermission("bluecore.worldmanager.bypass.interact"));
        }

    }
}
