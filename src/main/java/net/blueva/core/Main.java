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
 * Copyright (c) 2026 Blueva Development. All rights reserved.
 */

package net.blueva.core;

import net.blueva.core.commands.main.CommandHandler;
import net.blueva.core.commands.main.command.BlueCoreAliasRegister;
import net.blueva.core.commands.main.command.BlueCoreCommand;
import net.blueva.core.commands.main.subcommands.core.*;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.libraries.bstats.Metrics;
import net.blueva.core.modules.economy.vault.EconomyImplementer;
import net.blueva.core.modules.economy.vault.VaultHook;
import net.blueva.core.listeners.*;
import net.blueva.core.modules.scoreboard.ScoreboardModule;
import net.blueva.core.modules.TablistModule;
import net.blueva.core.modules.WorldModule;
import net.blueva.core.utils.LocationUtils;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public final class Main extends JavaPlugin {

	//managers
	public WorldModule worldModule;

	// other things
	public String pluginversion = getDescription().getVersion();
	public String actualLang;
	public static String prefix;
	public static boolean placeholderapi = false;
	public static boolean vaultapi = false;
	public EconomyImplementer economyImplementer = null;
	public static String currency_symbol;
    public HashMap<UUID, Double> playerBank = new HashMap<>();
	private VaultHook vaultHook;
	private static Main plugin;
	private BukkitAudiences adventure;
	public static Main getPlugin() {
		return plugin;
	}

    public void onEnable() {
		plugin = this;

		this.adventure = BukkitAudiences.create(this);

		ConfigManager.generateFolders();
		ConfigManager.registerDocuments();
		registerEvents();
        try {
            registerCommands();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        prefix = ConfigManager.language.getString("prefix");
		currency_symbol = ConfigManager.Modules.economy.getString("economy.currency_symbol");

		if(ConfigManager.settings.getBoolean("metrics")) {
			int pluginId = 17623;
			new Metrics(this, pluginId);
		}

		ScoreboardModule scoreboard = new ScoreboardModule(this);
		scoreboard.loadScoreboards();
		int delay_ticks = ConfigManager.Modules.scoreboards.getInt("scoreboards.join_delay");
		int refresh_ticks = ConfigManager.Modules.scoreboards.getInt("scoreboards.refresh");
		Bukkit.getScheduler().runTaskTimer(this, scoreboard::updatePlayerScoreboards, delay_ticks, refresh_ticks);

		TablistModule tablist = new TablistModule(this);
		tablist.createTab();

		LocationUtils lastlocation = new LocationUtils(this);
		lastlocation.createLastLocation();

		worldModule = new WorldModule(this);

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			placeholderapi = true;
		}
		if(ConfigManager.Modules.economy.getBoolean("economy.enabled")) {
			if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
				Bukkit.getConsoleSender().sendMessage("[BlueCore] " + ConfigManager.language.getString("messages.info.detected_vault"));
				economyImplementer = new EconomyImplementer();
				vaultHook = new VaultHook();
				vaultHook.hook();
				vaultapi = true;
			}
		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
			if(ConfigManager.Modules.worlds.getBoolean("worlds.enabled")) {
				try {
					worldModule.loadWorlds();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " ____  _             ____");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "| __ )| |_   _  ___ / ___|___  _ __ ___");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "|  _ \\| | | | |/ _ | |   / _ \\| '__/ _ \\");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "| |_) | | |_| |  __| |__| (_) | | |  __/");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "|____/|_|\\__,_|\\___|\\____\\___/|_|  \\___|");
			Bukkit.getConsoleSender().sendMessage("");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "V. " + pluginversion + " | Plugin enabled successfully | blueva.net");
		});
	}

	public void onDisable() {
		if (vaultapi) {
			vaultHook.unhook();
		}

		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " ____  _             ____");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "| __ )| |_   _  ___ / ___|___  _ __ ___");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|  _ \\| | | | |/ _ | |   / _ \\| '__/ _ \\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "| |_) | | |_| |  __| |__| (_) | | |  __/");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|____/|_|\\__,_|\\___|\\____\\___/|_|  \\___|");
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "V. " + pluginversion + " | Plugin disabled successfully | blueva.net");
	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockBreakListener(this), this);
		pm.registerEvents(new EntityDamageListener(this), this);
		pm.registerEvents(new EntitySpawnListener(this), this);
		pm.registerEvents(new BlockPlaceListener(this), this);
		pm.registerEvents(new AsyncPlayerChatListener(this), this);
		pm.registerEvents(new PlayerCommandPreprocessListener(this), this);
		pm.registerEvents(new PlayerDeathListener(this), this);
		pm.registerEvents(new PlayerDropItemListener(this), this);
		pm.registerEvents(new PlayerInteractListener(this), this);
		pm.registerEvents(new PlayerJoinListener(this), this);
		pm.registerEvents(new PlayerQuitListener(this), this);
	}

	private void registerCommands() throws IOException {
        CommandHandler bc = new CommandHandler();

        bc.register("bluecore", new BlueCoreCommand());
        bc.register("help", new HelpSubCommand());
        bc.register("info", new InfoSubCommand());
		bc.register("actionbar", new ActionBarCommand(this));
		bc.register("adventure", new AdventureCommand(this));
		bc.register("clearchat", new ClearChatCommand(this));
		bc.register("createkit", new CreateKitCommand(this));
		bc.register("creative", new CreativeCommand(this));
		bc.register("day", new DayCommand(this));
		bc.register("deletewarp", new DeleteWarpCommand(this));
		bc.register("economy", new EconomyCommand(this));
		bc.register("enderchest", new EnderChestCommand(this));
		bc.register("feed", new FeedCommand(this));
		bc.register("fly", new FlyCommand(this));
		bc.register("gamemode", new GamemodeCommand(this));
		bc.register("god", new GodCommand(this));
		bc.register("heal", new HealCommand(this));
		bc.register("kill", new KillCommand(this));
		bc.register("kit", new KitCommand(this));
		bc.register("message", new MessageCommand(this));
		bc.register("midnight", new MidnightCommand(this));
		bc.register("modifykit", new ModifyKitCommand(this));
		bc.register("money", new MoneyCommand(this));
		bc.register("night", new NightCommand(this));
		bc.register("noon", new NoonCommand(this));
		bc.register("pay", new PayCommand(this));
		bc.register("setspawn", new SetSpawnCommand(this));
		bc.register("setwarp", new SetWarpCommand(this));
		bc.register("spawn", new SpawnCommand(this));
		bc.register("spectator", new SpectatorCommand(this));
		bc.register("speed", new SpeedCommand(this));
		bc.register("storm", new StormCommand(this));
		bc.register("sudo", new SudoCommand(this));
		bc.register("suicide", new SuicideCommand(this));
		bc.register("sun", new SunCommand(this));
		bc.register("survival", new SurvivalCommand(this));
		bc.register("teleport", new TeleportCommand(this));
		bc.register("title", new TitleCommand(this));
		bc.register("updatewarp", new UpdateWarpCommand(this));
		bc.register("warp", new WarpCommand(this));
		bc.register("workbench", new WorkbenchCommand(this));
		bc.register("world", new WorldCommand(this));
        Objects.requireNonNull(getCommand("bluecore")).setExecutor(bc);
		new BlueCoreAliasRegister(this, bc).registerAliases();
        //Objects.requireNonNull(getCommand("bluecore")).setTabCompleter(new ...);
	}

	public @NonNull BukkitAudiences adventure() {
		if(this.adventure == null) {
			throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
		}
		return this.adventure;
	}
}
