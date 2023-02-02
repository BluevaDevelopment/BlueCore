package net.blueva.core.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;

import java.io.File;

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

        final Player player = (Player)sender;
        if(args.length > 0){
            if(args[0].equalsIgnoreCase("help")){
                if(player.hasPermission("bluecore.*") ||
                        player.hasPermission("bluecore.worldmanager.help") ||
                        player.hasPermission("bluecore.worldmanager.*")){
                    player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Manager");
                    player.sendMessage(ChatColor.GRAY + "Wiki: survcraft.org/wiki/xtremecore/worldmanager");
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
                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), player));
                }
            }else if(args[0].equalsIgnoreCase("create")){
                if(player.hasPermission("bluecore.*") ||
                        player.hasPermission("bluecore.worldmanager.create") ||
                        player.hasPermission("bluecore.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage: /wm create <worldname> <environment> <type>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) != null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_alredy_exist").replace("%worldmanager_worldname%",  worldname), player));
                            return true;
                        } else {
                            player.sendMessage(ChatColor.RED + "Usage: /wm create <worldname> <environment>");
                            return true;
                        }
                    }
                    if (args.length == 3) {
                        if(args[2].equalsIgnoreCase("normal") || args[2].equalsIgnoreCase("nether") || args[2].equalsIgnoreCase("the_end")) {
                            try {
                                player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.info.wm_creating_world"), player).replace("%worldmanager_worldname%", args[1]));
                                WorldCreator setupworld = new WorldCreator(args[1]);
                                setupworld.environment(World.Environment.valueOf(args[2].toUpperCase()));
                                setupworld.generateStructures(true);
                                setupworld.createWorld();
                                main.configManager.getWorlds().set("worlds." + args[1] + ".name", args[1]);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".alias", "&b" + args[1].replace("_", " "));
                                main.configManager.getWorlds().set("worlds." + args[1] + ".build", true);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".break", true);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".pvp", true);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".fall_damage", true);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".interact", true);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".drop_items", true);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".mob_spawning", true);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".difficulty", "NORMAL");
                                main.configManager.getWorlds().set("worlds." + args[1] + ".respawnWorld", "");
                                main.configManager.getWorlds().set("worlds." + args[1] + ".allowWeather", true);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".seed", Bukkit.getWorld(args[1]).getSeed());
                                main.configManager.getWorlds().set("worlds." + args[1] + ".generator", "");
                                main.configManager.getWorlds().set("worlds." + args[1] + ".generator", "");
                                main.configManager.getWorlds().set("worlds." + args[1] + ".environment", args[2].toUpperCase());
                                main.configManager.getWorlds().set("worlds." + args[1] + ".type", "NORMAL");
                                main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.x", 0.0);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.y", 65.0);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.z", 0.0);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.pitch", 0.0);
                                main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.yaw", 0.0);
                                main.configManager.saveWorlds();
                                player.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                                player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.wm_created_world"), player).replace("%worldmanager_worldname%", args[1]));
                            } catch (Exception e) {
                                e.printStackTrace();
                                player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_creation_error"), player));
                            }
                            return true;
                        } else {
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_invalid_args"), player));
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
                                        player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.info.wm_creating_world"), player).replace("%worldmanager_worldname%", args[1]));
                                        WorldCreator setupworld = new WorldCreator(args[1]);
                                        setupworld.environment(World.Environment.valueOf(args[2].toUpperCase()));
                                        setupworld.type(WorldType.valueOf(args[4].toUpperCase()));
                                        setupworld.generateStructures(true);
                                        setupworld.createWorld();
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".name", args[1]);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".alias", "&b" + args[1].replace("_", " "));
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".build", true);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".break", true);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".pvp", true);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".fall_damage", true);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".interact", true);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".drop_items", true);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".mob_spawning", true);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".difficulty", "NORMAL");
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".respawnWorld", "");
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".allowWeather", true);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".seed", Bukkit.getWorld(args[1]).getSeed());
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".generator", "");
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".generator", "");
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".environment", args[2].toUpperCase());
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".type", args[4].toLowerCase());
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.x", 0.0);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.y", 65.0);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.z", 0.0);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.pitch", 0.0);
                                        main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.yaw", 0.0);
                                        main.configManager.saveWorlds();
                                        player.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                                        player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.wm_created_world"), player).replace("%worldmanager_worldname%", args[1]));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_creation_error"), player));
                                    }
                                }
                            } else if(args[3].equals("-g")) {
                                if(args[4].isEmpty()) {
                                    //generator code
                                }
                                try {
                                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.info.wm_creating_world"), player).replace("%worldmanager_worldname%", args[1]));
                                    WorldCreator setupworld = new WorldCreator(args[1]);
                                    setupworld.environment(World.Environment.valueOf(args[2].toUpperCase()));
                                    setupworld.type(WorldType.NORMAL);
                                    setupworld.generateStructures(true);
                                    setupworld.generator(args[4]);
                                    setupworld.createWorld();
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".name", args[1]);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".alias", "&b" + args[1].replace("_", " "));
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".build", true);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".break", true);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".pvp", true);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".fall_damage", true);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".interact", true);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".drop_items", true);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".mob_spawning", true);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".difficulty", "NORMAL");
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".respawnWorld", "");
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".allowWeather", true);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".seed", Bukkit.getWorld(args[1]).getSeed());
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".generator", "");
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".generator", args[4]);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".environment", args[2].toUpperCase());
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".type", "NORMAL");
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.x", 0.0);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.y", 65.0);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.z", 0.0);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.pitch", 0.0);
                                    main.configManager.getWorlds().set("worlds." + args[1] + ".spawnlocation.yaw", 0.0);
                                    main.configManager.saveWorlds();
                                    player.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.wm_created_world"), player).replace("%worldmanager_worldname%", args[1]));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_creation_error"), player));
                                }
                            } else {
                                player.sendMessage(ChatColor.RED + "Usage: /wm create <worldname> <environment> -t <type> || -g <generator>");
                            }
                        } else {
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_invalid_args"), player));
                        }
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), player));
                }
            }else if(args[0].equalsIgnoreCase("delete")){
                if(player.hasPermission("bluecore.*") ||
                        player.hasPermission("bluecore.worldmanager.delete") ||
                        player.hasPermission("bluecore.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage /wm remove <worldname>");
                        player.sendMessage(ChatColor.DARK_AQUA + "Make sure all players are out of the world!");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) == null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_invalid_world"), player));
                            return true;
                        }
                        if (!Bukkit.getWorld(worldname).getPlayers().isEmpty()) {
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_players_in_world"), player));
                            return true;
                        }
                        Bukkit.unloadWorld(worldname, false);
                        /*try {
                            FileUtils.deleteDirectory(new File(worldname));
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.wm_deleted_world"), player).replace("%worldmanager_worldname%", worldname));
                            main.configManager.getWorlds().set("worlds." + worldname, null);
                            main.configManager.saveWorlds();
                        } catch (IOException e) {
                            e.printStackTrace();
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_deletion_error"), player));
                        }*/
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), player));
                }
            }else if(args[0].equalsIgnoreCase("setspawn")){
                if(player.hasPermission("bluecore.*") ||
                        player.hasPermission("bluecore.worldmanager.setspawn") ||
                        player.hasPermission("bluecore.worldmanager.*")){
                    if (args.length == 1) {
                        String worldname = player.getWorld().getName();
                        if (Bukkit.getWorld(worldname) == null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_invalid_world"), player));
                            return true;
                        }
                        main.configManager.getWorlds().set("worlds." + worldname + ".spawnlocation.x", player.getLocation().getX());
                        main.configManager.getWorlds().set("worlds." + worldname + ".spawnlocation.y", player.getLocation().getY());
                        main.configManager.getWorlds().set("worlds." + worldname + ".spawnlocation.z", player.getLocation().getZ());
                        main.configManager.getWorlds().set("worlds." + worldname + ".spawnlocation.pitch", player.getLocation().getPitch());
                        main.configManager.getWorlds().set("worlds." + worldname + ".spawnlocation.yaw", player.getLocation().getYaw());
                        main.configManager.saveWorlds();
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), player));
                }
            }else if(args[0].equalsIgnoreCase("spawn")){
                if(player.hasPermission("bluecore.*") ||
                        player.hasPermission("bluecore.worldmanager.spawn") ||
                        player.hasPermission("bluecore.worldmanager.*")){
                    if (args.length == 1) {
                        String worldname = player.getWorld().getName();
                        World world = Bukkit.getWorld(main.configManager.getWorlds().getString("worlds." + worldname + ".name"));
                        double x = Double.valueOf(main.configManager.getWorlds().getDouble("worlds." + worldname + ".spawnlocation.x"));
                        double y = Double.valueOf(main.configManager.getWorlds().getDouble("worlds." + worldname + ".spawnlocation.y"));
                        double z = Double.valueOf(main.configManager.getWorlds().getDouble("worlds." + worldname + ".spawnlocation.z"));
                        float pitch = Float.valueOf(main.configManager.getWorlds().getString("worlds." + worldname + ".spawnlocation.pitch"));
                        float yaw = Float.valueOf(main.configManager.getWorlds().getString("worlds." + worldname + ".spawnlocation.yaw"));
                        Location loc = new Location(world, x, y, z, yaw, pitch);
                        player.teleport(loc);
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), player));
                }
            }else if(args[0].equalsIgnoreCase("goto")){
                if(player.hasPermission("bluecore.*") ||
                        player.hasPermission("bluecore.worldmanager.goto") ||
                        player.hasPermission("bluecore.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage /wm goto <worldname>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) == null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_invalid_world"), player));
                            return true;
                        }
                        World world = Bukkit.getWorld(main.configManager.getWorlds().getString("worlds." + worldname + ".name"));
                        double x = Double.valueOf(main.configManager.getWorlds().getString("worlds." + worldname + ".spawnlocation.x"));
                        double y = Double.valueOf(main.configManager.getWorlds().getString("worlds." + worldname + ".spawnlocation.y"));
                        double z = Double.valueOf(main.configManager.getWorlds().getString("worlds." + worldname + ".spawnlocation.z"));
                        float pitch = Float.valueOf(main.configManager.getWorlds().getString("worlds." + worldname + ".spawnlocation.pitch"));
                        float yaw = Float.valueOf(main.configManager.getWorlds().getString("worlds." + worldname + ".spawnlocation.yaw"));
                        Location loc = new Location(world, x, y, z, yaw, pitch);
                        player.teleport(loc);
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), player));
                }
            }else if(args[0].equalsIgnoreCase("unload")){
                if(player.hasPermission("bluecore.*") ||
                        player.hasPermission("bluecore.worldmanager.unload") ||
                        player.hasPermission("bluecore.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage: /wm unload <worldname>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        if (Bukkit.getWorld(worldname) == null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_invalid_world"), player));
                            return true;
                        }
                        if (!Bukkit.getWorld(worldname).getPlayers().isEmpty()) {
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_players_in_world"), player));
                            return true;
                        }
                        Bukkit.unloadWorld(worldname, false);
                        player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.wm_unloaded_world"), player).replace("%worldmanager_worldname%", worldname));
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), player));
                }
            }else if(args[0].equalsIgnoreCase("import")){
                if(player.hasPermission("bluecore.*") ||
                        player.hasPermission("bluecore.worldmanager.import") ||
                        player.hasPermission("bluecore.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.RED + "Usage: /wm import <worldname>");
                        return true;
                    }
                    if (args.length == 2) {
                        String worldname = args[1];
                        File worldfile = new File(Bukkit.getServer().getWorldContainer(), worldname);
                        if (!worldfile.exists()) {
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_invalid_world"), player));
                            return true;
                        }
                        if (Bukkit.getWorld(worldname) != null) {
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_already_imported"), player));
                            return true;
                        }
                        Bukkit.unloadWorld(worldname, true);
                        try {
                            WorldCreator setupworld = new WorldCreator(worldname);
                            setupworld.createWorld();
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.success.wm_imported_world"), player).replace("%worldmanager_worldname%", worldname));
                            player.teleport(Bukkit.getWorld(worldname).getSpawnLocation());
                        } catch (Exception e) {
                            e.printStackTrace();
                            player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.wm_imported_error"), player));
                        }
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), player));
                }
            }else if(args[0].equalsIgnoreCase("list")){
                if(player.hasPermission("bluecore.*") ||
                        player.hasPermission("bluecore.worldmanager.list") ||
                        player.hasPermission("bluecore.worldmanager.*")){
                    if (args.length == 1) {
                        player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Manager");
                        player.sendMessage(ChatColor.GREEN + "List of worlds [" + Bukkit.getWorlds().size() + "]");
                        player.sendMessage("");
                        for (World worlds : Bukkit.getWorlds())
                            player.sendMessage(ChatColor.AQUA + worlds.getName() + ChatColor.GRAY + " - " + worlds.getWorldType());
                        return true;
                    }
                } else {
                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), player));
                }
            }else if(args[0].equalsIgnoreCase("types")){
                if(player.hasPermission("bluecore.*") ||
                        player.hasPermission("bluecore.worldmanager.types") ||
                        player.hasPermission("bluecore.worldmanager.*")){
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
                    player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), player));
                }
            }else{
                player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.unknown_command"), player));
            }
        }else{
            if(player.hasPermission("bluecore.admin.help") || player.hasPermission("bluecore.admin.*")) {
                player.sendMessage(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD.toString() + "World " + ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD.toString() + "Manager");
                player.sendMessage(ChatColor.GRAY + "Wiki: survcraft.org/wiki/xtremecore/worldmanager");
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
                player.sendMessage(MessageUtil.getColorMessage(main.configManager.getLang().getString("messages.error.no_perms"), player));
            }
        }
        return true;
    }

}