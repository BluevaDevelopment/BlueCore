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

package net.blueva.core.commands.main.command;

import net.blueva.core.Main;
import net.blueva.core.commands.main.CommandHandler;
import net.blueva.core.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class BlueCoreAliasRegister {

    private final Main plugin;
    private final CommandHandler commandHandler;

    public BlueCoreAliasRegister(Main plugin, CommandHandler commandHandler) {
        this.plugin = plugin;
        this.commandHandler = commandHandler;
    }

    public void registerAliases() {
        if (!ConfigManager.Modules.commands.contains("commands")) {
            return;
        }

        Set<Object> commandKeys = Objects.requireNonNull(ConfigManager.Modules.commands.getSection("commands")).getKeys();
        for (Object key : commandKeys) {
            String commandName = key.toString();
            commandHandler.registerAlias(commandName, commandName);

            List<String> aliases = ConfigManager.Modules.commands.getStringList("commands." + commandName + ".aliases");
            registerSubcommandAliases(commandName, aliases);

            boolean hasStandaloneSetting = ConfigManager.Modules.commands.contains("commands." + commandName + ".register_standalone");
            boolean registerStandalone = ConfigManager.Modules.commands.getBoolean("commands." + commandName + ".register_standalone", true);
            if (!hasStandaloneSetting) {
                registerStandalone = true;
            }

            if (registerStandalone) {
                registerStandaloneAlias(commandName, commandName);
                for (String alias : aliases) {
                    if (alias == null || alias.isBlank()) {
                        continue;
                    }
                    registerStandaloneAlias(alias, commandName);
                }
            }
        }
    }

    private void registerSubcommandAliases(String commandName, List<String> aliases) {
        for (String alias : aliases) {
            if (alias == null || alias.isBlank()) {
                continue;
            }
            commandHandler.registerAlias(alias.toLowerCase(Locale.ROOT), commandName);
        }
    }

    private void registerStandaloneAlias(String alias, String commandName) {
        if (alias == null || alias.isBlank() || commandName == null || commandName.isBlank()) {
            return;
        }
        String normalizedAlias = alias.toLowerCase(Locale.ROOT);
        CommandMap commandMap = getCommandMap();
        if (commandMap == null || commandMap.getCommand(normalizedAlias) != null) {
            return;
        }
        BukkitCommand standaloneCommand = new BlueCoreStandaloneAliasCommand(normalizedAlias, commandName.toLowerCase(Locale.ROOT), commandHandler);
        commandMap.register(plugin.getDescription().getName(), standaloneCommand);
    }

    private CommandMap getCommandMap() {
        try {
            Field commandMapField = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return (CommandMap) commandMapField.get(Bukkit.getPluginManager());
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class BlueCoreStandaloneAliasCommand extends BukkitCommand {

        private final CommandHandler commandHandler;
        private final String targetSubcommand;

        public BlueCoreStandaloneAliasCommand(@NotNull String name, @NotNull String targetSubcommand, CommandHandler commandHandler) {
            super(name);
            this.commandHandler = commandHandler;
            this.targetSubcommand = targetSubcommand;
        }

        @Override
        public boolean execute(@NotNull org.bukkit.command.CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
            return commandHandler.executeSubcommand(sender, this, commandLabel, targetSubcommand, args);
        }
    }
}
