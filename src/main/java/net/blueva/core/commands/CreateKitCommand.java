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
 * Copyright (c) 2024 Blueva Development. All rights reserved.
 */

package net.blueva.core.commands;

import net.blueva.core.configuration.ConfigManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.blueva.core.Main;
import net.blueva.core.modules.kits.KitsModule;
import net.blueva.core.utils.MessagesUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class CreateKitCommand implements CommandExecutor {

    private final Main main;

    public CreateKitCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            MessagesUtils.sendToConsole(ConfigManager.language.getString("messages.error.only_player"));
            return true;
        }

        if (args.length != 2) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_createkit_command"));
            return true;
        }

        Player player = (Player) sender;
        String kitname = args[0];
        String delay = args[1];
        int delayInt = 0;

        try {
            delayInt = Integer.parseInt(delay);
        } catch (NumberFormatException ignored) {
            
        }

        if (!sender.hasPermission("bluecore.createkit")) {
            MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.error.no_perms"));
            return true;
        }

        if(KitsModule.kitExists(kitname)) {
            MessagesUtils.sendToSender(player, Objects.requireNonNull(ConfigManager.language.getString("messages.error.existing_kit")).replace("%kit_name%", kitname));
            return true;
        }

        List<ItemStack> items = new ArrayList<>();
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && !item.getType().equals(Material.AIR)) {
                items.add(item.clone());
            }
        }

        try {
            KitsModule.createKit(kitname, "bluecore.kit."+kitname, delayInt, items);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        MessagesUtils.sendToSender(player, Objects.requireNonNull(ConfigManager.language.getString("messages.success.kit_created")).replace("%kit_name%", kitname));

        return true;
    }
}