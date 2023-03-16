package net.blueva.core.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.blueva.core.Main;

public class KitsManager {

    public static void createKit(String name, String permission, int delay, List<ItemStack> items) {
        Kit kit = new Kit(name, permission, delay, items);
        saveKit(kit);
    }
    
    public static Kit getKit(String name) {
        return loadKit(name);
    }

    public static boolean kitExists(String name) {
        if (Main.getPlugin().configManager.getKit(name).getString("name").equals("<name>")) {
            return false; 
        } else {
            return true; 
        }
    }
    
    private static void saveKit(Kit kit) {
        String kitName = kit.getName();
        Main.getPlugin().configManager.registerKit(kitName);
        
        ConfigurationSection kitSection = Main.getPlugin().configManager.getKit(kitName);
        kitSection.set("name", kitName);
        kitSection.set("permission", kit.getPermission());
        kitSection.set("delay", kit.getDelay());
        kitSection.set("items", kit.getItems());
        Main.getPlugin().configManager.saveKit(kitName);
        Main.getPlugin().configManager.reloadKit(kitName);
    }
    
    private static Kit loadKit(String name) {
        String kitName = Main.getPlugin().configManager.getKit(name).getString("name");
        String perms = Main.getPlugin().configManager.getKit(name).getString("perms");
        int delay = Main.getPlugin().configManager.getKit(name).getInt("delay");

        List<ItemStack> items = new ArrayList<>();
        for (Object itemObj : Main.getPlugin().configManager.getKit(name).getList("items")) {
            ItemStack itemStack = (ItemStack) itemObj;
            items.add(itemStack);
        }
    
        return new Kit(kitName, perms, delay, items);
    }

    public static void modifyKit(String name, String permission, int delay, List<ItemStack> items) {
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
        private String name;
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
