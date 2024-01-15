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

package net.blueva.core.modules;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import net.blueva.core.configuration.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class KitsModule {

    public static void createKit(String name, String permission, int delay, List<ItemStack> items) throws IOException {
        Kit kit = new Kit(name, permission, delay, items);
        saveKit(kit);
    }

    public static boolean kitExists(String name) {
        return !ConfigManager.Data.getKitDocument(name).getString("name").equals("<name>");
    }
    
    private static void saveKit(Kit kit) throws IOException {
        String kitName = kit.getName();
        ConfigManager.Data.registerKitDocument(kitName);

        Section kitSection = ConfigManager.Data.getKitDocument(kitName).getSection(kitName);
        kitSection.set("name", kitName);
        kitSection.set("permission", kit.getPermission());
        kitSection.set("delay", kit.getDelay());
        kitSection.set("items", kit.getItems());
        ConfigManager.Data.getKitDocument(kitName).save();
        ConfigManager.Data.getKitDocument(kitName).reload();
    }
    
    private static Kit loadKit(String name) {
        String kitName = ConfigManager.Data.getKitDocument(name).getString("name");
        String perms = ConfigManager.Data.getKitDocument(name).getString("perms");
        int delay = ConfigManager.Data.getKitDocument(name).getInt("delay");

        List<ItemStack> items = new ArrayList<>();
        for (Object itemObj : ConfigManager.Data.getKitDocument(name).getList("items")) {
            ItemStack itemStack = (ItemStack) itemObj;
            items.add(itemStack);
        }
    
        return new Kit(kitName, perms, delay, items);
    }

    public static void modifyKit(String name, String permission, int delay, List<ItemStack> items) throws IOException {
        Kit kit = loadKit(name);
        if (kit != null) {
            kit.setPermission(permission);
            kit.setDelay(delay);
            kit.setItems(items);
            saveKit(kit);
        }
    }

    public static void giveKit(Player player, String kitName) {
        Kit kit = loadKit(kitName);
        if (kit == null) {
            return;
        }

        for (ItemStack item : kit.getItems()) {
            HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(item);
            if (!remainingItems.isEmpty()) {
                for (ItemStack remainingItem : remainingItems.values()) {
                    player.getWorld().dropItem(player.getLocation(), remainingItem);
                }
            }
        }
    }


    public static class Kit {
        private final String name;
        private String permission;
        private int delay;
        private List<ItemStack> items;

        public Kit(String name, String permission, int delay, List<ItemStack> items) {
            this.name = name;
            this.permission = permission;
            this.delay = delay;
            this.items = items;
        }

        public String getName() {
            return name;
        }

        public String getPermission() {
            return permission;
        }

        public int getDelay() {
            return delay;
        }

        public List<ItemStack> getItems() {
            return items;
        }
        
        public void setPermission(String permission) {
            this.permission = permission;
        }
        
        public void setDelay(int delay) {
            this.delay = delay;
        }
        
        public void setItems(List<ItemStack> items) {
            this.items = items;
        }
    }
    
}
