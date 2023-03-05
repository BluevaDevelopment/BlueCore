package net.blueva.core;

import net.blueva.core.managers.LocationManager;
import net.blueva.core.managers.ScoreboardManager;
import net.blueva.core.managers.TablistManager;
import net.blueva.core.commands.*;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.listeners.*;
import net.blueva.core.managers.WorldManager;
import net.blueva.core.metrics.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

	//managers
	public ConfigManager configManager;
	public WorldManager worldManager;

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

	// other things
	public String pluginversion = getDescription().getVersion();
	public static boolean placeholderapi = false;
	public String actualLang;
	public String langPath;
	private static Main plugin;
	public static Main getPlugin() {
		return plugin;
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

		worldManager = new WorldManager(this);

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
			worldManager.loadWorlds();
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

	private void RegisterPluginCommands() {
		this.getCommand("adventure").setExecutor(new AdventureCommand(this));
		this.getCommand("clearchat").setExecutor(new ClearChatCommand(this));
		this.getCommand("creative").setExecutor(new CreativeCommand(this));
		this.getCommand("day").setExecutor(new DayCommand(this));
		this.getCommand("deletewarp").setExecutor(new DeleteWarpCommand(this));
		this.getCommand("enderchest").setExecutor(new EnderChestCommand(this));
		this.getCommand("feed").setExecutor(new FeedCommand(this));
		this.getCommand("fly").setExecutor(new FlyCommand(this));
		this.getCommand("gamemode").setExecutor(new GamemodeCommand(this));
		this.getCommand("god").setExecutor(new GodCommand(this));
		this.getCommand("heal").setExecutor(new HealCommand(this));
		this.getCommand("midnight").setExecutor(new MidnightCommand(this));
		this.getCommand("bluecore").setExecutor(new BlueCoreCommand(this));
		this.getCommand("night").setExecutor(new NightCommand(this));
		this.getCommand("noon").setExecutor(new NoonCommand(this));
		this.getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
		this.getCommand("setwarp").setExecutor(new SetWarpCommand(this));
		this.getCommand("spawn").setExecutor(new SpawnCommand(this));
		this.getCommand("spectator").setExecutor(new SpectatorCommand(this));
		this.getCommand("speed").setExecutor(new SpeedCommand(this));
		this.getCommand("storm").setExecutor(new StormCommand(this));
		this.getCommand("suicide").setExecutor(new SuicideCommand(this));
		this.getCommand("sun").setExecutor(new SunCommand(this));
		this.getCommand("survival").setExecutor(new SurvivalCommand(this));
		this.getCommand("teleport").setExecutor(new TeleportCommand(this));
		this.getCommand("updatewarp").setExecutor(new UpdateWarpCommand(this));
		this.getCommand("warp").setExecutor(new WarpCommand(this));
		this.getCommand("workbench").setExecutor(new WorkbenchCommand(this));
		this.getCommand("world").setExecutor(new WorldCommand(this));
	}

}
