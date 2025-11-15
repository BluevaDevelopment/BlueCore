/*
 *  ____  _             ____
 * | __ )| |_   _  ___ / ___|___  _ __ ___
 * |  _ \| | | | |/ _ | |   / _ \| '__/ _ \
 * | |_) | | |_| |  __| |__| (_) | | |  __/
 * |____/|_|\__,_|\___|\____\___/|_|  \___|
 *
 * This file is part of Blue Core.
 *
 * Blue Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation.
 *
 * Blue Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License version 3 for more details.
 *
 * Blue Core plugin developed by Blueva Development.
 * Website: https://blueva.net/
 * GitHub repository: https://github.com/BluevaDevelopment/BlueCore
 *
 * Copyright (c) 2025 Blueva Development. All rights reserved.
 */

package net.blueva.core.listeners;

import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.configuration.DataManager;
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
            String world_name = player.getWorld().getName();
            DataManager.Modules.Worlds.changeReference(world_name);
            if(!DataManager.Modules.Worlds.get(world_name).node("world." + world_name + ".mob_spawning").getBoolean() && !(event.getEntity() instanceof Player)) {
                event.setCancelled(true);
            }
        }
    }
}
