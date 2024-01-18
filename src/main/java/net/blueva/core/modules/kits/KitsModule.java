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

package net.blueva.core.modules.kits;

import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.configuration.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class KitsModule {

    public static void createKit(String name, String permission, int delay, List<ItemStack> items) throws IOException {
        KitData kit = new KitData(name, permission, delay, items);
        saveKit(kit);
    }

    public static boolean kitExists(String name) {
        return !DataManager.Modules.Kits.get(name).node("name").getString().equals("<name>");
    }
    
    private static void saveKit(KitData kit) throws SerializationException {
        String kit_name = kit.getName();

        DataManager.Modules.Kits.get(kit_name).node("name").set(kit_name);
        DataManager.Modules.Kits.get(kit_name).node("permission").set(kit.getPermission());
        DataManager.Modules.Kits.get(kit_name).node("delay").set(kit.getDelay());
        DataManager.Modules.Kits.get(kit_name).node("items").set(kit.getItems());
        DataManager.Modules.Kits.save(kit_name);
        DataManager.Modules.Kits.reload(kit_name);
    }
    
    private static KitData loadKit(String name) throws SerializationException {
        String kitName = DataManager.Modules.Kits.get(name).node("name").getString();
        String perms = DataManager.Modules.Kits.get(name).node("permission").getString();
        int delay = DataManager.Modules.Kits.get(name).node("items").getInt();

        List<ItemStack> items = new ArrayList<>();
        for (Object itemObj : Objects.requireNonNull(DataManager.Modules.Kits.get(name).node("items").getList(Object.class))) {
            ItemStack itemStack = (ItemStack) itemObj;
            items.add(itemStack);
        }
    
        return new KitData(kitName, perms, delay, items);
    }

    public static void modifyKit(String name, String permission, int delay, List<ItemStack> items) throws IOException {
        KitData kit = loadKit(name);
        kit.setPermission(permission);
        kit.setDelay(delay);
        kit.setItems(items);
        saveKit(kit);
    }

    public static void giveKit(Player player, String kitName) throws SerializationException {
        KitData kit = loadKit(kitName);

        for (ItemStack item : kit.getItems()) {
            HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(item);
            if (!remainingItems.isEmpty()) {
                for (ItemStack remainingItem : remainingItems.values()) {
                    player.getWorld().dropItem(player.getLocation(), remainingItem);
                }
            }
        }
    }
    
}
