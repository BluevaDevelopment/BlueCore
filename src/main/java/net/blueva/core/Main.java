package net.blueva.core;

import net.blueva.core.managers.LocationManager;
import net.blueva.core.managers.ScoreboardManager;
import net.blueva.core.managers.TablistManager;
import net.blueva.core.commands.*;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.events.*;
import net.blueva.core.metrics.Metrics;
import net.blueva.core.multiversion.Version;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

	//config files
	public ConfigManager configManager;

	public FileConfiguration settings = null;
	public File settingsFile = null;
	public FileConfiguration commands = null;
	public File commandsFile = null;
	public FileConfiguration kits = null;
	public File kitsFile = null;
	public FileConfiguration warps = null;
	public File warpsFile = null;
	public FileConfiguration language = null;
	public File languageFile = null;
	public FileConfiguration user = null;
	public File userFile = null;
	public FileConfiguration worlds = null;
	public File worldsFile = null;

	//languages
	public FileConfiguration es = null;
	public File esFile = null;

	// other things
	public String pluginversion = getDescription().getVersion();
	public static boolean placeholderapi = false;
	public String actualLang;
	public String langPath;
	private static Main plugin;
	private static final Version version = Version.valueOf(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3]);
	public static Main getPlugin() {
		return plugin;
	}

	public Version getVersion() {
		return version;
	}

	public void onEnable() {
		plugin = this;

		configManager = new ConfigManager(this);

		RegisterFiles();
		RegisterEvents();
		RegisterPluginCommands();


		if(getConfig().getBoolean("metrics")) {
			int pluginId = 17623;
			new Metrics(this, pluginId);
		}

		ScoreboardManager scoreboard = new ScoreboardManager(this);
		scoreboard.createScoreboard();

		TablistManager tablist = new TablistManager(this);
		tablist.createTablist();

		LocationManager lastlocation = new LocationManager(this);
		lastlocation.createLastLocation();

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			placeholderapi = true;
		}

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " ____  _             ____");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "| __ )| |_   _  ___ / ___|___  _ __ ___");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "|  _ \\| | | | |/ _ | |   / _ \\| '__/ _ \\");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "| |_) | | |_| |  __| |__| (_) | | |  __/");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "|____/|_|\\__,_|\\___|\\____\\___/|_|  \\___|");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "V. " + pluginversion + " | Plugin enabled successfully | blueva.net");
			LoadWorlds();
		});
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " ____  _             ____");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "| __ )| |_   _  ___ / ___|___  _ __ ___");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|  _ \\| | | | |/ _ | |   / _ \\| '__/ _ \\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "| |_) | | |_| |  __| |__| (_) | | |  __/");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|____/|_|\\__,_|\\___|\\____\\___/|_|  \\___|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "V. " + pluginversion + " | Plugin disabled successfully | blueva.net");
	}

	private void RegisterFiles() {
		configManager.generateFolders();

		configManager.registerSettings();

		actualLang = configManager.getSettings().getString("language");

		configManager.registerCommands();
		configManager.registerSettings();
		configManager.registerWarps();
		configManager.registerWorlds();

	}

	private void RegisterEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new OnBreakBlock(this), this);
		pm.registerEvents(new OnEntityDamage(this), this);
		pm.registerEvents(new OnEntitySpawn(this), this);
		pm.registerEvents(new OnPlaceBlock(this), this);
		pm.registerEvents(new OnPlayerChatAsync(this), this);
		pm.registerEvents(new OnPlayerCommand(this), this);
		pm.registerEvents(new OnPlayerDeath(this), this);
		pm.registerEvents(new OnPlayerDropItem(this), this);
		pm.registerEvents(new OnPlayerInteract(this), this);
		pm.registerEvents(new OnPlayerJoin(this), this);
		pm.registerEvents(new OnPlayerQuit(this), this);

	}

	private void RegisterPluginCommands() {
		this.getCommand("adventure").setExecutor(new AdventureCommand(this));
		this.getCommand("clearchat").setExecutor(new ClearChatCommand(this));
		this.getCommand("creative").setExecutor(new CreativeCommand(this));
		this.getCommand("day").setExecutor(new DayCommand(this));
		this.getCommand("deletewarp").setExecutor(new DeleteWarpCommand(this));
		this.getCommand("enderchest").setExecutor(new EnderChestCommand(this));
		this.getCommand("feed").setExecutor(new FeedCommand(this));
		this.getCommand("fly").setExecutor(new FlyCommand(this));
		this.getCommand("gm").setExecutor(new GMCommand(this));
		this.getCommand("god").setExecutor(new GodCommand(this));
		this.getCommand("heal").setExecutor(new HealCommand(this));
		this.getCommand("midnight").setExecutor(new MidnightCommand(this));
		this.getCommand("core").setExecutor(new CoreCommand(this));
		this.getCommand("night").setExecutor(new NightCommand(this));
		this.getCommand("noon").setExecutor(new NoonCommand(this));
		this.getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
		this.getCommand("setwarp").setExecutor(new SetWarpCommand(this));
		this.getCommand("spawn").setExecutor(new SpawnCommand(this));
		this.getCommand("spectator").setExecutor(new SpectatorCommand(this));
		this.getCommand("sun").setExecutor(new SunCommand(this));
		this.getCommand("survival").setExecutor(new SurvivalCommand(this));
		this.getCommand("updatewarp").setExecutor(new UpdateWarpCommand(this));
		this.getCommand("warp").setExecutor(new WarpCommand(this));
		this.getCommand("workbench").setExecutor(new WorkbenchCommand(this));
		this.getCommand("worldmanager").setExecutor(new WorldManagerCommand(this));
	}

	public void LoadWorlds() {
		if(configManager.getWorlds().isSet("worlds")) {
			Bukkit.getConsoleSender().sendMessage("[BlueCore/WorldManager] Loading worlds from \\plugins\\BlueCore\\worlds.yml");
			for (String key : configManager.getWorlds().getConfigurationSection("worlds").getKeys(false)) {
				Bukkit.getConsoleSender().sendMessage("[BlueCore/WorldManager] Loading world " + key.toString());
				WorldCreator setupworld = new WorldCreator(key);
				setupworld.createWorld();
			}
		} else {
			Bukkit.getConsoleSender().sendMessage("[BlueCore/WorldManager] Importing worlds from the server to BlueCore World Manager");
			for (World world : Bukkit.getWorlds()) {
				configManager.getWorlds().set("worlds." + world.getName() + ".name", world.getName());
				configManager.getWorlds().set("worlds." + world.getName() + ".alias", "&b" + world.getName().replace("_", " "));
				configManager.getWorlds().set("worlds." + world.getName() + ".build", true);
				configManager.getWorlds().set("worlds." + world.getName() + ".break", true);
				configManager.getWorlds().set("worlds." + world.getName() + ".pvp", true);
				configManager.getWorlds().set("worlds." + world.getName() + ".fall_damage", true);
				configManager.getWorlds().set("worlds." + world.getName() + ".interact", true);
				configManager.getWorlds().set("worlds." + world.getName() + ".drop_items", true);
				configManager.getWorlds().set("worlds." + world.getName() + ".mob_spawning", true);
				configManager.getWorlds().set("worlds." + world.getName() + ".difficulty", "NORMAL");
				configManager.getWorlds().set("worlds." + world.getName() + ".respawnWorld", "");
				configManager.getWorlds().set("worlds." + world.getName() + ".allowWeather", true);
				configManager.getWorlds().set("worlds." + world.getName() + ".seed", world.getSeed());
				configManager.getWorlds().set("worlds." + world.getName() + ".generator", "");
				configManager.getWorlds().set("worlds." + world.getName() + ".environment", world.getWorldType().toString());
				configManager.getWorlds().set("worlds." + world.getName() + ".type", "");
				configManager.getWorlds().set("worlds." + world.getName() + ".spawnlocation.x", 0.0);
				configManager.getWorlds().set("worlds." + world.getName() + ".spawnlocation.y", 65.0);
				configManager.getWorlds().set("worlds." + world.getName() + ".spawnlocation.z", 0.0);
				configManager.getWorlds().set("worlds." + world.getName() + ".spawnlocation.pitch", 0.0);
				configManager.getWorlds().set("worlds." + world.getName() + ".spawnlocation.yaw", 0.0);
				configManager.saveWorlds();
				Bukkit.getConsoleSender().sendMessage("[BlueCore/WorldManager] Imported world: " + world.getName());
			}
		}
	}

}
