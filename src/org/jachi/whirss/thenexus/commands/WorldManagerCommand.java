package org.jachi.whirss.thenexus.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;

import org.jachi.whirss.thenexus.Main;
import org.jachi.whirss.thenexus.MessageUtil;

import java.io.File;
import java.io.IOException;

public class WorldManagerCommand implements CommandExecutor {

    private Main main;

    public WorldManagerCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        final Player player = (Player)sender;

        if (!(sender instanceof Player)) {
            player.sendMessage("You must be a player to run this command.");
            return true;
        }

        if(args.length > 0){
            if(args[0].equalsIgnoreCase("help")){
                if(player.hasPermission("thenexus.*") ||
                        player.hasPermission("thenexus.worldmanager.help") ||
                        player.hasPermission("thenexus.worldmanager.*")){
                    player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Manager");
                    player.sendMessage(ChatColor.GRAY + "Wiki: survcraft.org/wiki/thenexus/worldmanager");
                    player.sendMessage("");
                    player.sendMessage(ChatColor.AQUA + "/wm create <worldname> [-worldtype]" + ChatColor.GRAY + " - Creates a new world");
                    player.sendMessage(ChatColor.AQUA + "/wm unload <worldname>" + ChatColor.GRAY + " - Unloads the world without deleting it");
                    player.sendMessage(ChatColor.AQUA + "/wm delete <worldname>" + ChatColor.GRAY + " - Delete an alredy existing world");
                    player.sendMessage(ChatColor.AQUA + "/wm import <worldname>" + ChatColor.GRAY + " - Import a world from server file");
                    player.sendMessage(ChatColor.AQUA + "/wm goto <worldname>" + ChatColor.GRAY + " - Teleport to a world");
                    player.sendMessage(ChatColor.AQUA + "/wm setspawn" + ChatColor.GRAY + " - Sets the spawn of a world");
                    player.sendMessage(ChatColor.AQUA + "/wm types" + ChatColor.GRAY + " - List of world types");
                    player.sendMessage(ChatColor.AQUA + "/wm list" + ChatColor.GRAY + " - List of loaded worlds");

                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("create")){
                if(player.hasPermission("thenexus.*") ||
                        player.hasPermission("thenexus.worldmanager.create") ||
                        player.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage: /wm create <worldname> <environment> <type>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) != null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_alredy_exist").replace("%worldmanager_worldname%",  worldname)));
                            return true;
                        } else {
                            player.sendMessage(ChatColor.RED + "Usage: /wm create <worldname> <environment>");
                            return true;
                        }
                    }
                    if (args.length == 3) {
                        if(args[2].equalsIgnoreCase("normal") || args[2].equalsIgnoreCase("nether") || args[2].equalsIgnoreCase("the_end")) {
                            try {
                                player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.info.wm_creating_world").replace("%worldmanager_worldname%", args[1])));
                                WorldCreator setupworld = new WorldCreator(args[1]);
                                setupworld.environment(World.Environment.valueOf(args[2].toUpperCase()));
                                setupworld.generateStructures(true);
                                setupworld.createWorld();
                                main.getWorlds().set("worlds." + args[1] + ".name", args[1]);
                                main.getWorlds().set("worlds." + args[1] + ".alias", "&a" + args[1].replace("_", " "));
                                main.getWorlds().set("worlds." + args[1] + ".build", true);
                                main.getWorlds().set("worlds." + args[1] + ".break", true);
                                main.getWorlds().set("worlds." + args[1] + ".pvp", true);
                                main.getWorlds().set("worlds." + args[1] + ".fall_damage", true);
                                main.getWorlds().set("worlds." + args[1] + ".interact", true);
                                main.getWorlds().set("worlds." + args[1] + ".drop_items", true);
                                main.getWorlds().set("worlds." + args[1] + ".mob_spawning", true);
                                main.getWorlds().set("worlds." + args[1] + ".difficulty", "NORMAL");
                                main.getWorlds().set("worlds." + args[1] + ".respawnWorld", "");
                                main.getWorlds().set("worlds." + args[1] + ".allowWeather", true);
                                main.getWorlds().set("worlds." + args[1] + ".seed", Bukkit.getWorld(args[1]).getSeed());
                                main.getWorlds().set("worlds." + args[1] + ".generator", "");
                                main.getWorlds().set("worlds." + args[1] + ".generator", "");
                                main.getWorlds().set("worlds." + args[1] + ".environment", args[2].toUpperCase());
                                main.getWorlds().set("worlds." + args[1] + ".type", "NORMAL");
                                main.getWorlds().set("worlds." + args[1] + ".spawnlocation.x", 0.0);
                                main.getWorlds().set("worlds." + args[1] + ".spawnlocation.y", 65.0);
                                main.getWorlds().set("worlds." + args[1] + ".spawnlocation.z", 0.0);
                                main.getWorlds().set("worlds." + args[1] + ".spawnlocation.pitch", 0.0);
                                main.getWorlds().set("worlds." + args[1] + ".spawnlocation.yaw", 0.0);
                                main.saveWorlds();
                                player.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                                player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.wm_created_world")).replace("%worldmanager_worldname%", args[1]));
                            } catch (Exception e) {
                                e.printStackTrace();
                                player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_creation_error")));
                            }
                            return true;
                        } else {
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_invalid_args")));
                        }
                    }
                    if (args.length == 4) {
                        if(args[2].equalsIgnoreCase("normal") || args[2].equalsIgnoreCase("nether") || args[2].equalsIgnoreCase("the_end")) {
                            if(args[3].equals("-t")) {
                                player.sendMessage(ChatColor.RED + "Usage: /wm create <worldname> <environment> -t <type> || -g <generator>");
                            } else if(args[3].equals("-g")) {
                                player.sendMessage(ChatColor.RED + "Usage: /wm create <worldname> <environment> -t <type> || -g <generator>");
                            } else {
                                player.sendMessage(ChatColor.RED + "Usage: /wm create <worldname> <environment> -t <type> || -g <generator>");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Usage: /wm create <worldname> <environment> -t <type> || -g <generator>");
                        }

                    }
                    if (args.length == 5) {
                        if(args[2].equalsIgnoreCase("normal") || args[2].equalsIgnoreCase("nether") || args[2].equalsIgnoreCase("the_end")) {
                            if(args[3].equals("-t")) {
                                if(args[4].equals("flat") || args[4].equals("large_biomes") || args[4].equals("normal") || args[4].equals("amplified")) {
                                    try {
                                        player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.info.wm_creating_world").replace("%worldmanager_worldname%", args[1])));
                                        WorldCreator setupworld = new WorldCreator(args[1]);
                                        setupworld.environment(World.Environment.valueOf(args[2].toUpperCase()));
                                        setupworld.type(WorldType.valueOf(args[4].toUpperCase()));
                                        setupworld.generateStructures(true);
                                        setupworld.createWorld();
                                        main.getWorlds().set("worlds." + args[1] + ".name", args[1]);
                                        main.getWorlds().set("worlds." + args[1] + ".alias", "&a" + args[1].replace("_", " "));
                                        main.getWorlds().set("worlds." + args[1] + ".build", true);
                                        main.getWorlds().set("worlds." + args[1] + ".break", true);
                                        main.getWorlds().set("worlds." + args[1] + ".pvp", true);
                                        main.getWorlds().set("worlds." + args[1] + ".fall_damage", true);
                                        main.getWorlds().set("worlds." + args[1] + ".interact", true);
                                        main.getWorlds().set("worlds." + args[1] + ".drop_items", true);
                                        main.getWorlds().set("worlds." + args[1] + ".mob_spawning", true);
                                        main.getWorlds().set("worlds." + args[1] + ".difficulty", "NORMAL");
                                        main.getWorlds().set("worlds." + args[1] + ".respawnWorld", "");
                                        main.getWorlds().set("worlds." + args[1] + ".allowWeather", true);
                                        main.getWorlds().set("worlds." + args[1] + ".seed", Bukkit.getWorld(args[1]).getSeed());
                                        main.getWorlds().set("worlds." + args[1] + ".generator", "");
                                        main.getWorlds().set("worlds." + args[1] + ".generator", "");
                                        main.getWorlds().set("worlds." + args[1] + ".environment", args[2].toUpperCase());
                                        main.getWorlds().set("worlds." + args[1] + ".type", args[4].toLowerCase());
                                        main.getWorlds().set("worlds." + args[1] + ".spawnlocation.x", 0.0);
                                        main.getWorlds().set("worlds." + args[1] + ".spawnlocation.y", 65.0);
                                        main.getWorlds().set("worlds." + args[1] + ".spawnlocation.z", 0.0);
                                        main.getWorlds().set("worlds." + args[1] + ".spawnlocation.pitch", 0.0);
                                        main.getWorlds().set("worlds." + args[1] + ".spawnlocation.yaw", 0.0);
                                        main.saveWorlds();
                                        player.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                                        player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.wm_created_world")).replace("%worldmanager_worldname%", args[1]));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_creation_error")));
                                    }
                                }
                            } else if(args[3].equals("-g")) {
                                if(args[4].isBlank()) {
                                    player.sendMessage("pene");
                                }
                                try {
                                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.info.wm_creating_world").replace("%worldmanager_worldname%", args[1])));
                                    WorldCreator setupworld = new WorldCreator(args[1]);
                                    setupworld.environment(World.Environment.valueOf(args[2].toUpperCase()));
                                    setupworld.type(WorldType.NORMAL);
                                    setupworld.generateStructures(true);
                                    setupworld.generator(args[4]);
                                    setupworld.createWorld();
                                    main.getWorlds().set("worlds." + args[1] + ".name", args[1]);
                                    main.getWorlds().set("worlds." + args[1] + ".alias", "&a" + args[1].replace("_", " "));
                                    main.getWorlds().set("worlds." + args[1] + ".build", true);
                                    main.getWorlds().set("worlds." + args[1] + ".break", true);
                                    main.getWorlds().set("worlds." + args[1] + ".pvp", true);
                                    main.getWorlds().set("worlds." + args[1] + ".fall_damage", true);
                                    main.getWorlds().set("worlds." + args[1] + ".interact", true);
                                    main.getWorlds().set("worlds." + args[1] + ".drop_items", true);
                                    main.getWorlds().set("worlds." + args[1] + ".mob_spawning", true);
                                    main.getWorlds().set("worlds." + args[1] + ".difficulty", "NORMAL");
                                    main.getWorlds().set("worlds." + args[1] + ".respawnWorld", "");
                                    main.getWorlds().set("worlds." + args[1] + ".allowWeather", true);
                                    main.getWorlds().set("worlds." + args[1] + ".seed", Bukkit.getWorld(args[1]).getSeed());
                                    main.getWorlds().set("worlds." + args[1] + ".generator", "");
                                    main.getWorlds().set("worlds." + args[1] + ".generator", args[4]);
                                    main.getWorlds().set("worlds." + args[1] + ".environment", args[2].toUpperCase());
                                    main.getWorlds().set("worlds." + args[1] + ".type", "NORMAL");
                                    main.getWorlds().set("worlds." + args[1] + ".spawnlocation.x", 0.0);
                                    main.getWorlds().set("worlds." + args[1] + ".spawnlocation.y", 65.0);
                                    main.getWorlds().set("worlds." + args[1] + ".spawnlocation.z", 0.0);
                                    main.getWorlds().set("worlds." + args[1] + ".spawnlocation.pitch", 0.0);
                                    main.getWorlds().set("worlds." + args[1] + ".spawnlocation.yaw", 0.0);
                                    main.saveWorlds();
                                    player.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.wm_created_world")).replace("%worldmanager_worldname%", args[1]));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_creation_error")));
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Usage: /wm create <worldname> <environment> -t <type> || -g <generator>");
                            }
                        } else {
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_invalid_args")));
                        }
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("delete")){
                if(player.hasPermission("thenexus.*") ||
                        player.hasPermission("thenexus.worldmanager.delete") ||
                        player.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage /wm remove <worldname>");
                        player.sendMessage(ChatColor.DARK_AQUA + "Make sure all players are out of the world!");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) == null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_invalid_world")));
                            return true;
                        }
                        if (!Bukkit.getWorld(worldname).getPlayers().isEmpty()) {
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_players_in_world")));
                            return true;
                        }
                        Bukkit.unloadWorld(worldname, false);
                        try {
                            FileUtils.deleteDirectory(new File(worldname));
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.wm_deleted_world".replace("%worldmanager_worldname%", worldname))));
                            main.getWorlds().set("worlds." + worldname, null);
                            main.saveWorlds();
                        } catch (IOException e) {
                            e.printStackTrace();
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_deletion_error")));
                        }
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("setspawn")){
                if(player.hasPermission("thenexus.*") ||
                        player.hasPermission("thenexus.worldmanager.setspawn") ||
                        player.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        String worldname = player.getWorld().getName();
                        if (Bukkit.getWorld(worldname) == null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_invalid_world")));
                            return true;
                        }
                        main.getWorlds().set("worlds." + worldname + ".spawnlocation.x", player.getLocation().getX());
                        main.getWorlds().set("worlds." + worldname + ".spawnlocation.y", player.getLocation().getY());
                        main.getWorlds().set("worlds." + worldname + ".spawnlocation.z", player.getLocation().getZ());
                        main.getWorlds().set("worlds." + worldname + ".spawnlocation.pitch", player.getLocation().getPitch());
                        main.getWorlds().set("worlds." + worldname + ".spawnlocation.yaw", player.getLocation().getYaw());
                        main.saveWorlds();
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("spawn")){
                if(player.hasPermission("thenexus.*") ||
                        player.hasPermission("thenexus.worldmanager.spawn") ||
                        player.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        String worldname = player.getWorld().getName();
                        World world = Bukkit.getWorld(main.getWorlds().getString("worlds." + worldname + ".name"));
                        double x = Double.valueOf(main.getWorlds().getDouble("worlds." + worldname + ".spawnlocation.x"));
                        double y = Double.valueOf(main.getWorlds().getDouble("worlds." + worldname + ".spawnlocation.y"));
                        double z = Double.valueOf(main.getWorlds().getDouble("worlds." + worldname + ".spawnlocation.z"));
                        float pitch = Float.valueOf(main.getWorlds().getString("worlds." + worldname + ".spawnlocation.pitch"));
                        float yaw = Float.valueOf(main.getWorlds().getString("worlds." + worldname + ".spawnlocation.yaw"));
                        Location loc = new Location(world, x, y, z, yaw, pitch);
                        player.teleport(loc);
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("goto")){
                if(player.hasPermission("thenexus.*") ||
                        player.hasPermission("thenexus.worldmanager.goto") ||
                        player.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage /wm goto <worldname>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) == null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_invalid_world")));
                            return true;
                        }
                        World world = Bukkit.getWorld(main.getWorlds().getString("worlds." + worldname + ".name"));
                        double x = Double.valueOf(main.getWorlds().getString("worlds." + worldname + ".spawnlocation.x"));
                        double y = Double.valueOf(main.getWorlds().getString("worlds." + worldname + ".spawnlocation.y"));
                        double z = Double.valueOf(main.getWorlds().getString("worlds." + worldname + ".spawnlocation.z"));
                        float pitch = Float.valueOf(main.getWorlds().getString("worlds." + worldname + ".spawnlocation.pitch"));
                        float yaw = Float.valueOf(main.getWorlds().getString("worlds." + worldname + ".spawnlocation.yaw"));
                        Location loc = new Location(world, x, y, z, yaw, pitch);
                        player.teleport(loc);
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("unload")){
                if(player.hasPermission("thenexus.*") ||
                        player.hasPermission("thenexus.worldmanager.unload") ||
                        player.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage: /wm unload <worldname>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) == null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_invalid_world")));
                            return true;
                        }
                        if (!Bukkit.getWorld(worldname).getPlayers().isEmpty()) {
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_players_in_world")));
                            return true;
                        }
                        Bukkit.unloadWorld(worldname, false);
                        player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.wm_unloaded_world".replace("%worldmanager_worldname%", worldname))));
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("import")){
                if(player.hasPermission("thenexus.*") ||
                        player.hasPermission("thenexus.worldmanager.import") ||
                        player.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage: /wm import <worldname>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        File worldfile = new File(Bukkit.getServer().getWorldContainer(), worldname);
                        if (!worldfile.exists()) {
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_invalid_world")));
                            return true;
                        }
                        if (Bukkit.getWorld(worldname) != null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_already_imported")));
                            return true;
                        }
                        Bukkit.unloadWorld(worldname, true);
                        try {
                            WorldCreator setupworld = new WorldCreator(worldname);
                            setupworld.createWorld();
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.success.wm_imported_world".replace("%worldmanager_worldname%", worldname))));
                            player.teleport(Bukkit.getWorld(worldname).getSpawnLocation());
                        } catch (Exception e) {
                            e.printStackTrace();
                            player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.wm_imported_error")));
                        }
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("list")){
                if(player.hasPermission("thenexus.*") ||
                        player.hasPermission("thenexus.worldmanager.list") ||
                        player.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Manager");
                        player.sendMessage(ChatColor.GREEN + "List of worlds [" + Bukkit.getWorlds().size() + "]");
                        player.sendMessage("");
                        for (World worlds : Bukkit.getWorlds())
                            player.sendMessage(ChatColor.AQUA + worlds.getName() + ChatColor.GRAY + " - " + worlds.getWorldType());
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
                }
            }else if(args[0].equalsIgnoreCase("types")){
                if(player.hasPermission("thenexus.*") ||
                        player.hasPermission("thenexus.worldmanager.types") ||
                        player.hasPermission("thenexus.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Manager");
                        player.sendMessage(ChatColor.GREEN + "Types:");
                        player.sendMessage("");
                        player.sendMessage(ChatColor.AQUA + "-normal = Minecraft Vanilla world");
                        player.sendMessage(ChatColor.AQUA + "-flat = FlatLand world");
                        player.sendMessage(ChatColor.AQUA + "-void = Completely air world");
                        player.sendMessage(ChatColor.AQUA + "-large_biomes = large biomes world");
                        player.sendMessage(ChatColor.AQUA + "-amplified = land-forms, biomes, and the size of all terrain in general is blown out of proportion");
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
                }
            }else{
                player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.unknown_command")));
            }
        }else{
            if(player.hasPermission("thenexus.admin.help") || player.hasPermission("thenexus.admin.*")) {
                player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Manager");
                player.sendMessage(ChatColor.GRAY + "Wiki: survcraft.org/wiki/thenexus/worldmanager");
                player.sendMessage("");
                player.sendMessage(ChatColor.AQUA + "/wm create <worldname> [-worldtype]" + ChatColor.GRAY + " - Creates a new world");
                player.sendMessage(ChatColor.AQUA + "/wm unload <worldname>" + ChatColor.GRAY + " - Unloads the world without deleting it");
                player.sendMessage(ChatColor.AQUA + "/wm delete <worldname>" + ChatColor.GRAY + " - Delete an alredy existing world");
                player.sendMessage(ChatColor.AQUA + "/wm import <worldname>" + ChatColor.GRAY + " - Import a world from server file");
                player.sendMessage(ChatColor.AQUA + "/wm goto <worldname>" + ChatColor.GRAY + " - Teleport to a world");
                player.sendMessage(ChatColor.AQUA + "/wm setspawn" + ChatColor.GRAY + " - Sets the spawn of a world");
                player.sendMessage(ChatColor.AQUA + "/wm types" + ChatColor.GRAY + " - List of world types");
                player.sendMessage(ChatColor.AQUA + "/wm list" + ChatColor.GRAY + " - List of loaded worlds");
            } else {
                player.sendMessage(MessageUtil.getColorMessage(main.getLanguages().getString("messages.error.no_perms")));
            }
        }
        return true;
    }

}