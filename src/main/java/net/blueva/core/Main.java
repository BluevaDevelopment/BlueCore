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
 * GitHub repository: https://github.com/BluevaDevelopment/BlueMenu
 *
 * Copyright (c) 2023 Blueva Development. All rights reserved.
 */

package net.blueva.core;

import net.blueva.core.commands.*;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.listeners.*;
import net.blueva.core.managers.LocationManager;
import net.blueva.core.managers.ScoreboardManager;
import net.blueva.core.managers.TablistManager;
import net.blueva.core.managers.WorldManager;
import net.blueva.core.libraries.metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class Main extends JavaPlugin {

	//managers
	public ConfigManager configManager;
	public WorldManager worldManager;

	public FileConfiguration settings = null;
	public File settingsFile = null;
	public FileConfiguration commands = null;
	public File commandsFile = null;
	public FileConfiguration warps = null;
	public File warpsFile = null;
	public FileConfiguration language = null;
	public File languageFile = null;
	public FileConfiguration user = null;
	public File userFile = null;
	public FileConfiguration kit = null;
	public File kitFile = null;
	public FileConfiguration worlds = null;
	public File worldsFile = null;
	public FileConfiguration scoreboards = null;
	public File scoreboardsFile = null;

	// other things
	public String pluginversion = getDescription().getVersion();
	public static boolean placeholderapi = false;
	public String actualLang;
	public String langPath;
	public static String prefix;
	private static Main plugin;
	public static Main getPlugin() {
		return plugin;
	}

	public void onEnable() {
		plugin = this;

		configManager = new ConfigManager(this);

		registerFiles();
		registerEvents();
		registerCommands();

		prefix = configManager.getLang().getString("prefix");


		if(configManager.getSettings().getBoolean("metrics")) {
			int pluginId = 17623;
			new Metrics(this, pluginId);
		}

		ScoreboardManager scoreboard = new ScoreboardManager(this);
		scoreboard.createScoreboard();

		TablistManager tablist = new TablistManager(this);
		tablist.createTablist();

		LocationManager lastlocation = new LocationManager(this);
		lastlocation.createLastLocation();

		worldManager = new WorldManager(this);

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			placeholderapi = true;
		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
			worldManager.loadWorlds();
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
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " ____  _             ____");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "| __ )| |_   _  ___ / ___|___  _ __ ___");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|  _ \\| | | | |/ _ | |   / _ \\| '__/ _ \\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "| |_) | | |_| |  __| |__| (_) | | |  __/");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|____/|_|\\__,_|\\___|\\____\\___/|_|  \\___|");
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "V. " + pluginversion + " | Plugin disabled successfully | blueva.net");
	}

	private void registerFiles() {
		configManager.generateFolders();

		configManager.registerSettings();

		actualLang = configManager.getSettings().getString("language");

		configManager.registerCommands();
		configManager.registerScoreboards();
		configManager.registerSettings();
		configManager.registerWarps();
		configManager.registerWorlds();

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

	private void registerCommands() {
		Objects.requireNonNull(this.getCommand("adventure")).setExecutor(new AdventureCommand(this));
		Objects.requireNonNull(this.getCommand("clearchat")).setExecutor(new ClearChatCommand(this));
		Objects.requireNonNull(this.getCommand("createkit")).setExecutor(new CreateKitCommand(this));
		Objects.requireNonNull(this.getCommand("creative")).setExecutor(new CreativeCommand(this));
		Objects.requireNonNull(this.getCommand("day")).setExecutor(new DayCommand(this));
		Objects.requireNonNull(this.getCommand("deletewarp")).setExecutor(new DeleteWarpCommand(this));
		Objects.requireNonNull(this.getCommand("enderchest")).setExecutor(new EnderChestCommand(this));
		Objects.requireNonNull(this.getCommand("feed")).setExecutor(new FeedCommand(this));
		Objects.requireNonNull(this.getCommand("fly")).setExecutor(new FlyCommand(this));
		Objects.requireNonNull(this.getCommand("gamemode")).setExecutor(new GamemodeCommand(this));
		Objects.requireNonNull(this.getCommand("god")).setExecutor(new GodCommand(this));
		Objects.requireNonNull(this.getCommand("heal")).setExecutor(new HealCommand(this));
		Objects.requireNonNull(this.getCommand("kill")).setExecutor(new KillCommand(this));
		Objects.requireNonNull(this.getCommand("kit")).setExecutor(new KitCommand(this));
		Objects.requireNonNull(this.getCommand("message")).setExecutor(new MessageCommand(this));
		Objects.requireNonNull(this.getCommand("midnight")).setExecutor(new MidnightCommand(this));
		Objects.requireNonNull(this.getCommand("modifykit")).setExecutor(new ModifyKitCommand(this));
		Objects.requireNonNull(this.getCommand("bluecore")).setExecutor(new BlueCoreCommand(this));
		Objects.requireNonNull(this.getCommand("night")).setExecutor(new NightCommand(this));
		Objects.requireNonNull(this.getCommand("noon")).setExecutor(new NoonCommand(this));
		Objects.requireNonNull(this.getCommand("setspawn")).setExecutor(new SetSpawnCommand(this));
		Objects.requireNonNull(this.getCommand("setwarp")).setExecutor(new SetWarpCommand(this));
		Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new SpawnCommand(this));
		Objects.requireNonNull(this.getCommand("spectator")).setExecutor(new SpectatorCommand(this));
		Objects.requireNonNull(this.getCommand("speed")).setExecutor(new SpeedCommand(this));
		Objects.requireNonNull(this.getCommand("storm")).setExecutor(new StormCommand(this));
		Objects.requireNonNull(this.getCommand("sudo")).setExecutor(new SudoCommand(this));
		Objects.requireNonNull(this.getCommand("suicide")).setExecutor(new SuicideCommand(this));
		Objects.requireNonNull(this.getCommand("sun")).setExecutor(new SunCommand(this));
		Objects.requireNonNull(this.getCommand("survival")).setExecutor(new SurvivalCommand(this));
		Objects.requireNonNull(this.getCommand("teleport")).setExecutor(new TeleportCommand(this));
		Objects.requireNonNull(this.getCommand("updatewarp")).setExecutor(new UpdateWarpCommand(this));
		Objects.requireNonNull(this.getCommand("warp")).setExecutor(new WarpCommand(this));
		Objects.requireNonNull(this.getCommand("workbench")).setExecutor(new WorkbenchCommand(this));
		Objects.requireNonNull(this.getCommand("world")).setExecutor(new WorldCommand(this));
	}

}
