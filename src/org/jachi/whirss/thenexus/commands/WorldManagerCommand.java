package org.jachi.whirss.thenexus.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;

import org.jachi.whirss.thenexus.Main;

import java.io.File;
import java.io.IOException;

public class WorldManagerCommand implements CommandExecutor {

    private Main main;

    public WorldManagerCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to run this command.");
            return true;
        }
        final Player p = (Player)sender;

        if(args.length > 0){
            if(args[0].equalsIgnoreCase("help")){
                if(p.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.worldmanager.help") ||
                        sender.hasPermission("thenexus.worldmanager.*")){
                    sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Manager");
                    sender.sendMessage(ChatColor.GRAY + "Wiki: survcraft.org/wiki/thenexus/worldmanager");
                    sender.sendMessage("");
                    sender.sendMessage(ChatColor.AQUA + "/wm create <worldname> [-worldtype]" + ChatColor.GRAY + " - Creates a new world");
                    sender.sendMessage(ChatColor.AQUA + "/wm unload <worldname>" + ChatColor.GRAY + " - Unloads the world without deleting it");
                    sender.sendMessage(ChatColor.AQUA + "/wm delete <worldname>" + ChatColor.GRAY + " - Delete an alredy existing world");
                    sender.sendMessage(ChatColor.AQUA + "/wm import <worldname>" + ChatColor.GRAY + " - Import a world from server file");
                    sender.sendMessage(ChatColor.AQUA + "/wm goto <worldname>" + ChatColor.GRAY + " - Teleport to a world");
                    sender.sendMessage(ChatColor.AQUA + "/wm types <worldname>" + ChatColor.GRAY + " - List of world types");
                    sender.sendMessage(ChatColor.AQUA + "/wm list <worldname>" + ChatColor.GRAY + " - List of loaded worlds");

                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("create")){
                if(p.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.worldmanager.create") ||
                        sender.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        p.sendMessage(ChatColor.RED + "Usage: /wm create <worldname> <-worldtype>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) != null) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_alredy_exist").replace("%worldmanager_worldname%",  worldname)));
                            return true;
                        }
                    } else if (args.length == 3) {
                        String worldtype = args[2];
                        String worldname = args[1];
                        if (worldtype.equals("-flat")) {
                            try {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_creating_world").replace("%worldmanager_worldname%", worldname)));
                                WorldCreator setupworld = new WorldCreator(worldname);
                                setupworld.type(WorldType.FLAT);
                                setupworld.generateStructures(true);
                                setupworld.createWorld();
                                main.getWorlds().set("worlds." + worldname + ".alias", worldname);
                                main.getWorlds().set("worlds." + worldname + ".build", true);
                                main.getWorlds().set("worlds." + worldname + ".break", true);
                                main.getWorlds().set("worlds." + worldname + ".pvp", true);
                                main.getWorlds().set("worlds." + worldname + ".fall_damage", true);
                                main.getWorlds().set("worlds." + worldname + ".interact", true);
                                main.getWorlds().set("worlds." + worldname + ".drop_items", true);
                                main.getWorlds().set("worlds." + worldname + ".mob_spawning", true);
                                main.getWorlds().set("worlds." + worldname + ".difficulty", "NORMAL");
                                main.getWorlds().set("worlds." + worldname + ".respawnWorld", "");
                                main.getWorlds().set("worlds." + worldname + ".allowWeather", true);
                                main.getWorlds().set("worlds." + worldname + ".seed", Bukkit.getWorld(worldname).getSeed());
                                main.getWorlds().set("worlds." + worldname + ".generator", "");
                                main.getWorlds().set("worlds." + worldname + ".generator", "");
                                main.getWorlds().set("worlds." + worldname + ".environment", Bukkit.getWorld(worldname).getEnvironment().toString());
                                main.getWorlds().set("worlds." + worldname + ".type", "FLAT");
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.x", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.y", 65.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.z", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.pitch", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.yaw", 0.0);
                                main.saveWorlds();
                                p.teleport(Bukkit.getWorld(worldname).getSpawnLocation());
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_created_world").replace("%worldmanager_worldname%", worldname)));
                            } catch (Exception e) {
                                e.printStackTrace();
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_creation_error")));
                            }
                            return true;
                        }
                        if (worldtype.equals("-normal")) {
                            try {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_creating_world").replace("%worldmanager_worldname%", worldname)));
                                WorldCreator setupworld = new WorldCreator(worldname);
                                setupworld.type(WorldType.NORMAL);
                                setupworld.generateStructures(true);
                                setupworld.createWorld();
                                main.getWorlds().set("worlds." + worldname + ".alias", worldname);
                                main.getWorlds().set("worlds." + worldname + ".build", true);
                                main.getWorlds().set("worlds." + worldname + ".break", true);
                                main.getWorlds().set("worlds." + worldname + ".pvp", true);
                                main.getWorlds().set("worlds." + worldname + ".fall_damage", true);
                                main.getWorlds().set("worlds." + worldname + ".interact", true);
                                main.getWorlds().set("worlds." + worldname + ".drop_items", true);
                                main.getWorlds().set("worlds." + worldname + ".mob_spawning", true);
                                main.getWorlds().set("worlds." + worldname + ".difficulty", "NORMAL");
                                main.getWorlds().set("worlds." + worldname + ".respawnWorld", "");
                                main.getWorlds().set("worlds." + worldname + ".allowWeather", true);
                                main.getWorlds().set("worlds." + worldname + ".seed", Bukkit.getWorld(worldname).getSeed());
                                main.getWorlds().set("worlds." + worldname + ".generator", "");
                                main.getWorlds().set("worlds." + worldname + ".environment", Bukkit.getWorld(worldname).getEnvironment().toString());
                                main.getWorlds().set("worlds." + worldname + ".type", "NORMAL");
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.x", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.y", 65.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.z", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.pitch", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.yaw", 0.0);
                                main.saveWorlds();
                                p.teleport(Bukkit.getWorld(worldname).getSpawnLocation());
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_created_world").replace("%worldmanager_worldname%", worldname)));
                            } catch (Exception e) {
                                e.printStackTrace();
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_creation_error")));
                            }
                            return true;
                        }
                        if (worldtype.equals("-void")) {
                            try {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_creating_world").replace("%worldmanager_worldname%", worldname)));
                                WorldCreator setupworld = new WorldCreator(worldname);
                                setupworld.type(WorldType.FLAT);
                                setupworld.generatorSettings("2;0;1;");
                                setupworld.generateStructures(false);
                                setupworld.createWorld();
                                main.getWorlds().set("worlds." + worldname + ".alias", worldname);
                                main.getWorlds().set("worlds." + worldname + ".build", true);
                                main.getWorlds().set("worlds." + worldname + ".break", true);
                                main.getWorlds().set("worlds." + worldname + ".pvp", true);
                                main.getWorlds().set("worlds." + worldname + ".fall_damage", true);
                                main.getWorlds().set("worlds." + worldname + ".interact", true);
                                main.getWorlds().set("worlds." + worldname + ".drop_items", true);
                                main.getWorlds().set("worlds." + worldname + ".mob_spawning", true);
                                main.getWorlds().set("worlds." + worldname + ".difficulty", "VOID");
                                main.getWorlds().set("worlds." + worldname + ".respawnWorld", "");
                                main.getWorlds().set("worlds." + worldname + ".allowWeather", true);
                                main.getWorlds().set("worlds." + worldname + ".seed", Bukkit.getWorld(worldname).getSeed());
                                main.getWorlds().set("worlds." + worldname + ".generator", "");
                                main.getWorlds().set("worlds." + worldname + ".environment", Bukkit.getWorld(worldname).getEnvironment().toString());
                                main.getWorlds().set("worlds." + worldname + ".type", "FLAT");
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.x", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.y", 65.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.z", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.pitch", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.yaw", 0.0);
                                main.saveWorlds();
                                p.teleport(Bukkit.getWorld(worldname).getSpawnLocation());
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_created_world").replace("%worldmanager_worldname%", worldname)));
                            } catch (Exception e) {
                                e.printStackTrace();
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_creation_error")));
                            }
                            return true;
                        }
                        if (worldtype.equals("-large_biomes")) {
                            try {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_creating_world").replace("%worldmanager_worldname%", worldname)));
                                WorldCreator setupworld = new WorldCreator(worldname);
                                setupworld.type(WorldType.LARGE_BIOMES);
                                setupworld.generateStructures(true);
                                setupworld.createWorld();
                                main.getWorlds().set("worlds." + worldname + ".alias", worldname);
                                main.getWorlds().set("worlds." + worldname + ".build", true);
                                main.getWorlds().set("worlds." + worldname + ".break", true);
                                main.getWorlds().set("worlds." + worldname + ".pvp", true);
                                main.getWorlds().set("worlds." + worldname + ".fall_damage", true);
                                main.getWorlds().set("worlds." + worldname + ".interact", true);
                                main.getWorlds().set("worlds." + worldname + ".drop_items", true);
                                main.getWorlds().set("worlds." + worldname + ".mob_spawning", true);
                                main.getWorlds().set("worlds." + worldname + ".difficulty", "NORMAL");
                                main.getWorlds().set("worlds." + worldname + ".respawnWorld", "");
                                main.getWorlds().set("worlds." + worldname + ".allowWeather", true);
                                main.getWorlds().set("worlds." + worldname + ".seed", Bukkit.getWorld(worldname).getSeed());
                                main.getWorlds().set("worlds." + worldname + ".generator", "");
                                main.getWorlds().set("worlds." + worldname + ".environment", Bukkit.getWorld(worldname).getEnvironment().toString());
                                main.getWorlds().set("worlds." + worldname + ".type", "LARGE_BIOMES");
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.x", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.y", 65.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.z", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.pitch", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.yaw", 0.0);
                                main.saveWorlds();
                                p.teleport(Bukkit.getWorld(worldname).getSpawnLocation());
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_created_world").replace("%worldmanager_worldname%", worldname)));
                            } catch (Exception e) {
                                e.printStackTrace();
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_creation_error")));
                            }
                            return true;
                        }
                        if (worldtype.equals("-amplified")) {
                            try {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_creating_world").replace("%worldmanager_worldname%", worldname)));
                                WorldCreator setupworld = new WorldCreator(worldname);
                                setupworld.type(WorldType.AMPLIFIED);
                                setupworld.generateStructures(true);
                                setupworld.createWorld();
                                main.getWorlds().set("worlds." + worldname + ".alias", worldname);
                                main.getWorlds().set("worlds." + worldname + ".build", true);
                                main.getWorlds().set("worlds." + worldname + ".break", true);
                                main.getWorlds().set("worlds." + worldname + ".pvp", true);
                                main.getWorlds().set("worlds." + worldname + ".fall_damage", true);
                                main.getWorlds().set("worlds." + worldname + ".interact", true);
                                main.getWorlds().set("worlds." + worldname + ".drop_items", true);
                                main.getWorlds().set("worlds." + worldname + ".mob_spawning", true);
                                main.getWorlds().set("worlds." + worldname + ".difficulty", "NORMAL");
                                main.getWorlds().set("worlds." + worldname + ".respawnWorld", "");
                                main.getWorlds().set("worlds." + worldname + ".allowWeather", true);
                                main.getWorlds().set("worlds." + worldname + ".seed", Bukkit.getWorld(worldname).getSeed());
                                main.getWorlds().set("worlds." + worldname + ".generator", "");
                                main.getWorlds().set("worlds." + worldname + ".environment", Bukkit.getWorld(worldname).getEnvironment().toString());
                                main.getWorlds().set("worlds." + worldname + ".type", "AMPLIFIED");
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.x", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.y", 65.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.z", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.pitch", 0.0);
                                main.getWorlds().set("worlds." + worldname + ".spawnlocation.yaw", 0.0);
                                main.saveWorlds();
                                p.teleport(Bukkit.getWorld(worldname).getSpawnLocation());
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_created_world").replace("%worldmanager_worldname%", worldname)));
                            } catch (Exception e) {
                                e.printStackTrace();
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_creation_error")));
                            }
                            return true;
                        }
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_invalid_worldtype")));
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("delete")){
                if(p.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.worldmanager.delete") ||
                        sender.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        p.sendMessage(ChatColor.RED + "Usage /wm remove <worldname>");
                        p.sendMessage(ChatColor.DARK_AQUA + "Make sure all players are out of the world!");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) == null) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_invalid_world")));
                            return true;
                        }
                        if (!Bukkit.getWorld(worldname).getPlayers().isEmpty()) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_players_in_world")));
                            return true;
                        }
                        Bukkit.unloadWorld(worldname, false);
                        try {
                            FileUtils.deleteDirectory(new File(worldname));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_deleted_world".replace("%worldmanager_worldname%", worldname))));
                            main.getWorlds().set("worlds." + worldname, null);
                            main.saveWorlds();
                        } catch (IOException e) {
                            e.printStackTrace();
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_deletion_error")));
                        }
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("goto")){
                if(p.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.worldmanager.goto") ||
                        sender.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        p.sendMessage(ChatColor.RED + "Usage /wm tp <worldname>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) == null) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_invalid_world")));
                            return true;
                        }
                        p.teleport(Bukkit.getWorld(worldname).getSpawnLocation());
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("unload")){
                if(p.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.worldmanager.unload") ||
                        sender.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        p.sendMessage(ChatColor.RED + "Usage: /wm unload <worldname>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) == null) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_invalid_world")));
                            return true;
                        }
                        if (!Bukkit.getWorld(worldname).getPlayers().isEmpty()) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_players_in_world")));
                            return true;
                        }
                        Bukkit.unloadWorld(worldname, false);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_unloaded_world".replace("%worldmanager_worldname%", worldname))));
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("import")){
                if(p.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.worldmanager.import") ||
                        sender.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        p.sendMessage(ChatColor.RED + "Usage: /wm import <worldname>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        File worldfile = new File(Bukkit.getServer().getWorldContainer(), worldname);
                        if (!worldfile.exists()) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_invalid_world")));
                            return true;
                        }
                        if (Bukkit.getWorld(worldname) != null) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_already_imported")));
                            return true;
                        }
                        Bukkit.unloadWorld(worldname, true);
                        try {
                            WorldCreator setupworld = new WorldCreator(worldname);
                            setupworld.createWorld();
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_imported_world".replace("%worldmanager_worldname%", worldname))));
                            p.teleport(Bukkit.getWorld(worldname).getSpawnLocation());
                        } catch (Exception e) {
                            e.printStackTrace();
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("wm_imported_error")));
                        }
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("list")){
                if(p.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.worldmanager.list") ||
                        sender.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        p.sendMessage(ChatColor.GOLD + "worlds: [" + Bukkit.getWorlds().size() + "]");
                        p.sendMessage("");
                        for (World worlds : Bukkit.getWorlds())
                            p.sendMessage(ChatColor.YELLOW + worlds.getName() + ChatColor.GRAY + " - " + worlds.getWorldType());
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("types")){
                if(p.hasPermission("thenexus.*") ||
                        sender.hasPermission("thenexus.worldmanager.types") ||
                        sender.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Manager");
                        p.sendMessage(ChatColor.GREEN + "Types:");
                        p.sendMessage("");
                        p.sendMessage(ChatColor.AQUA + "-normal = Minecraft Vanilla world");
                        p.sendMessage(ChatColor.AQUA + "-flat = FlatLand world");
                        p.sendMessage(ChatColor.AQUA + "-void = Completely air world");
                        p.sendMessage(ChatColor.AQUA + "-large_biomes = large biomes world");
                        p.sendMessage(ChatColor.AQUA + "-amplified = land-forms, biomes, and the size of all terrain in general is blown out of proportion");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
                }
            }else{
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("unknown_command")));
            }
        }else{
            if(sender.hasPermission("thenexus.admin.help") || sender.hasPermission("thenexus.admin.*")) {
                sender.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Manager");
                sender.sendMessage(ChatColor.GRAY + "Wiki: survcraft.org/wiki/thenexus/worldmanager");
                sender.sendMessage("");
                sender.sendMessage(ChatColor.AQUA + "/wm create <worldname> [-worldtype]" + ChatColor.GRAY + " - Creates a new world");
                sender.sendMessage(ChatColor.AQUA + "/wm unload <worldname>" + ChatColor.GRAY + " - Unloads the world without deleting it");
                sender.sendMessage(ChatColor.AQUA + "/wm delete <worldname>" + ChatColor.GRAY + " - Delete an alredy existing world");
                sender.sendMessage(ChatColor.AQUA + "/wm import <worldname>" + ChatColor.GRAY + " - Import a world from server file");
                sender.sendMessage(ChatColor.AQUA + "/wm goto <worldname>" + ChatColor.GRAY + " - Teleport to a world");
                sender.sendMessage(ChatColor.AQUA + "/wm types <worldname>" + ChatColor.GRAY + " - List of world types");
                sender.sendMessage(ChatColor.AQUA + "/wm list <worldname>" + ChatColor.GRAY + " - List of loaded worlds");
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.getMessages().getString("no_perms")));
            }
        }
        return true;
    }

}