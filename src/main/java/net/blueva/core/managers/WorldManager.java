package net.blueva.core.managers;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

public class WorldManager {

    private Main main;

    public WorldManager(Main main) {
        this.main = main;
    }

    public void loadWorlds() {
        if(main.configManager.getWorlds().isSet("worlds")) {
            Bukkit.getConsoleSender().sendMessage("[BlueCore] [WorldManager] Loading worlds from \\plugins\\BlueCore\\worlds.yml");
            for (String key : main.configManager.getWorlds().getConfigurationSection("worlds").getKeys(false)) {
                Bukkit.getConsoleSender().sendMessage("[BlueCore] [WorldManager] Loading world " + key.toString());
                WorldCreator setupworld = new WorldCreator(key);
                setupworld.createWorld();
            }
        } else {
            Bukkit.getConsoleSender().sendMessage("[BlueCore] [WorldManager] Importing worlds from the server to BlueCore World Manager");
            for (World world : Bukkit.getWorlds()) {
                main.configManager.getWorlds().set("worlds." + world.getName() + ".name", world.getName());
                main.configManager.getWorlds().set("worlds." + world.getName() + ".alias", "&b" + world.getName().replace("_", " "));
                main.configManager.getWorlds().set("worlds." + world.getName() + ".build", true);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".break", true);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".pvp", true);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".fall_damage", true);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".interact", true);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".drop_items", true);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".mob_spawning", true);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".difficulty", world.getDifficulty().toString());
                main.configManager.getWorlds().set("worlds." + world.getName() + ".respawnWorld", "");
                main.configManager.getWorlds().set("worlds." + world.getName() + ".allowWeather", true);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".seed", world.getSeed());
                if(world.getGenerator() != null) main.configManager.getWorlds().set("worlds." + world.getName() + ".generator", world.getGenerator().toString().split("\\(")[0].trim());
                main.configManager.getWorlds().set("worlds." + world.getName() + ".environment", world.getEnvironment().toString());
                main.configManager.getWorlds().set("worlds." + world.getName() + ".type", world.getWorldType().toString());
                main.configManager.getWorlds().set("worlds." + world.getName() + ".spawnlocation.x", 0.0);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".spawnlocation.y", 65.0);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".spawnlocation.z", 0.0);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".spawnlocation.pitch", 0.0);
                main.configManager.getWorlds().set("worlds." + world.getName() + ".spawnlocation.yaw", 0.0);
                main.configManager.saveWorlds();
                Bukkit.getConsoleSender().sendMessage("[BlueCore] [WorldManager] Imported world: " + world.getName());
            }
        }
    }

    public void createWorld(Player player, String name, String environment, String type, String generator) {
        if(environment == null) {
            environment = "normal";
        }
        if(type == null) {
            type = "normal";
        }

        player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.info.wm_creating_world")).replace("%worldmanager_worldname%", name));
        WorldCreator setupworld = new WorldCreator(name);
        setupworld.environment(World.Environment.valueOf(environment.toUpperCase()));
        setupworld.type(WorldType.valueOf(type.toUpperCase()));
        setupworld.generateStructures(true);

        if(generator != null) {
            setupworld.generator(generator);
        }

        try {
            setupworld.createWorld();
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.wm_creation_error")));
        }

        main.configManager.getWorlds().set("worlds." + name + ".name", name);
        main.configManager.getWorlds().set("worlds." + name + ".alias", "&b" + name.replace("_", " "));
        main.configManager.getWorlds().set("worlds." + name + ".build", true);
        main.configManager.getWorlds().set("worlds." + name + ".break", true);
        main.configManager.getWorlds().set("worlds." + name + ".pvp", true);
        main.configManager.getWorlds().set("worlds." + name + ".fall_damage", true);
        main.configManager.getWorlds().set("worlds." + name + ".interact", true);
        main.configManager.getWorlds().set("worlds." + name + ".drop_items", true);
        main.configManager.getWorlds().set("worlds." + name + ".mob_spawning", true);
        main.configManager.getWorlds().set("worlds." + name + ".difficulty", "NORMAL");
        main.configManager.getWorlds().set("worlds." + name + ".respawnWorld", "");
        main.configManager.getWorlds().set("worlds." + name + ".allowWeather", true);
        main.configManager.getWorlds().set("worlds." + name + ".seed", Bukkit.getWorld(name).getSeed());
        main.configManager.getWorlds().set("worlds." + name + ".generator", "");
        main.configManager.getWorlds().set("worlds." + name + ".generator", "");
        main.configManager.getWorlds().set("worlds." + name + ".environment", environment.toUpperCase());
        main.configManager.getWorlds().set("worlds." + name + ".type", type.toUpperCase());
        main.configManager.getWorlds().set("worlds." + name + ".spawnlocation.x", 0.0);
        main.configManager.getWorlds().set("worlds." + name + ".spawnlocation.y", 65.0);
        main.configManager.getWorlds().set("worlds." + name + ".spawnlocation.z", 0.0);
        main.configManager.getWorlds().set("worlds." + name + ".spawnlocation.pitch", 0.0);
        main.configManager.getWorlds().set("worlds." + name + ".spawnlocation.yaw", 0.0);
        main.configManager.saveWorlds();
        player.teleport(Bukkit.getWorld(name).getSpawnLocation());
        player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.success.wm_created_world")).replace("%worldmanager_worldname%", name));

    }

    public void deleteWorld(Player player, String name) {
        if (Bukkit.getWorld(name) == null) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.wm_invalid_world")));
            return;
        }
        if (!Bukkit.getWorld(name).getPlayers().isEmpty()) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.wm_players_in_world")));
            return;
        }
        Bukkit.unloadWorld(name, false);
    }

    public void importWorld(Player player, String name) {
        File worldfile = new File(Bukkit.getServer().getWorldContainer(), name);
        if (!worldfile.exists()) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.wm_invalid_world")));
            return;
        }
        if (Bukkit.getWorld(name) != null) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.wm_already_imported")));
            return;
        }
        Bukkit.unloadWorld(name, true);
        try {
            WorldCreator setupworld = new WorldCreator(name);
            setupworld.createWorld();
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.success.wm_imported_world")).replace("%worldmanager_worldname%", name));
            player.teleport(Bukkit.getWorld(name).getSpawnLocation());
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.wm_imported_error")));
        }
    }

    public void setWorldSpawn(Player player) {
        String name = player.getWorld().getName();
        if (Bukkit.getWorld(name) == null) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.wm_invalid_world")));
            return;
        }
        main.configManager.getWorlds().set("worlds." + name + ".spawnlocation.x", player.getLocation().getX());
        main.configManager.saveWorlds();
        main.configManager.getWorlds().set("worlds." + name + ".spawnlocation.y", player.getLocation().getY());
        main.configManager.saveWorlds();
        main.configManager.getWorlds().set("worlds." + name + ".spawnlocation.z", player.getLocation().getZ());
        main.configManager.saveWorlds();
        main.configManager.getWorlds().set("worlds." + name + ".spawnlocation.pitch", player.getLocation().getPitch());
        main.configManager.saveWorlds();
        main.configManager.getWorlds().set("worlds." + name + ".spawnlocation.yaw", player.getLocation().getYaw());
        main.configManager.saveWorlds();
        main.configManager.reloadWorlds();
    }

    public void gotoWorldSpawn(Player player) {
        String name = player.getWorld().getName();
        World world = Bukkit.getWorld(Objects.requireNonNull(main.configManager.getWorlds().getString("worlds." + name + ".name")));
        double x = main.configManager.getWorlds().getDouble("worlds." + name + ".spawnlocation.x");
        double y = main.configManager.getWorlds().getDouble("worlds." + name + ".spawnlocation.y");
        double z = main.configManager.getWorlds().getDouble("worlds." + name + ".spawnlocation.z");
        float pitch = Float.parseFloat(Objects.requireNonNull(main.configManager.getWorlds().getString("worlds." + name + ".spawnlocation.pitch")));
        float yaw = Float.parseFloat(Objects.requireNonNull(main.configManager.getWorlds().getString("worlds." + name + ".spawnlocation.yaw")));
        Location loc = new Location(world, x, y, z, yaw, pitch);
        player.teleport(loc);
    }

    public void gotoWorld(Player player, String name) {
        if (Bukkit.getWorld(name) == null) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.wm_invalid_world")));
            return;
        }
        World world = Bukkit.getWorld(Objects.requireNonNull(main.configManager.getWorlds().getString("worlds." + name + ".name")));
        double x = main.configManager.getWorlds().getDouble("worlds." + name + ".spawnlocation.x");
        double y = main.configManager.getWorlds().getDouble("worlds." + name + ".spawnlocation.y");
        double z = main.configManager.getWorlds().getDouble("worlds." + name + ".spawnlocation.z");
        float pitch = Float.parseFloat(Objects.requireNonNull(main.configManager.getWorlds().getString("worlds." + name + ".spawnlocation.pitch")));
        float yaw = Float.parseFloat(Objects.requireNonNull(main.configManager.getWorlds().getString("worlds." + name + ".spawnlocation.yaw")));
        Location loc = new Location(world, x, y, z, yaw, pitch);
        player.teleport(loc);
    }
}
