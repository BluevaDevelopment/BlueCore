package net.blueva.core.modules.kits;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitData {
    private final String name;
    private String permission;
    private int delay;
    private List<ItemStack> items;

    public KitData(String name, String permission, int delay, List<ItemStack> items) {
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