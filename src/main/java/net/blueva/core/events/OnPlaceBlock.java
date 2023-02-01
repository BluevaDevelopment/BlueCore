package net.blueva.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import net.blueva.core.Main;

public class OnPlaceBlock implements Listener {

    private Main main;

    public OnPlaceBlock(Main main) {
        this.main = main;
    }

    @EventHandler
    public void OnPlayerPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        String worldname = event.getPlayer().getWorld().getName();
        if(!main.getWorlds().getBoolean("worlds." + worldname + ".build")) {
            if(player.hasPermission("xtremecore.*") ||
                    player.hasPermission("xtremecore.worldmanager.bypass.*") ||
                    player.hasPermission("xtremecore.worldmanager.bypass.build") ||
                    player.hasPermission("xtremecore.worldmanager.*")){
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }

    }
}
