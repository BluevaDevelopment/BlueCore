package net.blueva.core.libraries.vault;

import net.blueva.core.Main;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {
    private final Main main = Main.getPlugin();

    private Economy provider;

    public void hook() {
        provider = main.economyImplementer;
        Bukkit.getServicesManager().register(Economy.class, this.provider, this.main, ServicePriority.Normal);
    }

    public void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class, this.provider);
    }
}
