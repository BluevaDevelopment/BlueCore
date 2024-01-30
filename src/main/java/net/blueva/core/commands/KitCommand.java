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
 * Copyright (c) 2024 Blueva Development. All rights reserved.
 */

package net.blueva.core.commands;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.configuration.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.blueva.core.Main;
import net.blueva.core.modules.kits.KitsModule;
import net.blueva.core.utils.DateUtils;
import net.blueva.core.utils.MessagesUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.serialize.SerializationException;

public class KitCommand implements CommandExecutor {

    private final Main main;

    public KitCommand(Main main) {
        this.main = main;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player) && args.length != 2) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_kit_command"));
            return true;
        }

        if (args.length < 1 || args.length > 2) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_kit_command"));
            return true;
        }

        if (!sender.hasPermission("bluecore.kit")) {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
            return true;
        }

        String kit = args[0];

        if(!KitsModule.kitExists(kit)) {
            MessagesUtils.sendToSender(sender, Objects.requireNonNull(ConfigManager.language.getString("messages.error.kit_not_found")).replace("%kit_name%", kit));
            return true;
        }

        Player target;
        if (args.length == 2) {
            target = Bukkit.getPlayer(args[1]);

            if (!sender.hasPermission("bluecore.kit.others") && !sender.hasPermission("bluecore.kit."+kit+".others")) {
                MessagesUtils.sendToSender(target, ConfigManager.language.getString("messages.error.no_perms"));
                return true;
            }

            try {
                KitsModule.giveKit(target, kit);
            } catch (SerializationException e) {
                throw new RuntimeException(e);
            }

            MessagesUtils.sendToSender(sender, Objects.requireNonNull(ConfigManager.language.getString("messages.success.kit_given_others")).replace("%kit_name%", kit).replace("%player%", target.getName()));
            MessagesUtils.sendToSender(target, Objects.requireNonNull(ConfigManager.language.getString("messages.success.kit_given")).replace("%kit_name%", kit));
        } else {
            target = (Player) sender;

            if (!sender.hasPermission("bluecore.kit."+kit)) {
                MessagesUtils.sendToSender(target, ConfigManager.language.getString("messages.error.no_perms"));
                return true;
            }

            DataManager.Users.changeUserReference(target.getUniqueId().toString());
            if(!DataManager.Users.getUser(target.getUniqueId()).node("date", "kits", kit).isNull()) {
                if(!isFutureKitDatePassed(kit, target)) {
                    MessagesUtils.sendToSender(target.getPlayer(), getTimeUntilFutureDateAsString(kit, target));
                    return true;
                } else {
                    try {
                        removeKitDate(kit, target);
                        setFutureKitDate(kit, target);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                try {
                    setFutureKitDate(kit, target);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                KitsModule.giveKit(target, kit);
            } catch (SerializationException e) {
                throw new RuntimeException(e);
            }
            MessagesUtils.sendToPlayer(target, Objects.requireNonNull(ConfigManager.language.getString("messages.success.kit_given")).replace("%kit_name%", kit));
        } 

        return true;
    }

    private void setFutureKitDate(String kit, Player target) throws IOException {
        DataManager.Modules.Kits.changeReference(kit);

        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime futureDate = currentDate.plusSeconds(DataManager.Modules.Kits.get(kit).node("items").getInt());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String futureDateStr = futureDate.format(formatter);

        DataManager.Users.getUser(target.getUniqueId()).node("date", "kits", kit).set(futureDateStr);
        DataManager.Users.saveUser(target.getUniqueId());
        DataManager.Users.reloadUser(target.getUniqueId());
    }

    private void removeKitDate(String kit, Player target) throws IOException {
        DataManager.Users.getUser(target.getUniqueId()).node("date", "kits", kit).set(null);
        DataManager.Users.saveUser(target.getUniqueId());
        DataManager.Users.reloadUser(target.getUniqueId());
    }

    private boolean isFutureKitDatePassed(String kit, Player target) {
        String dateString = DataManager.Users.getUser(target.getUniqueId()).node("date", "kits", kit).getString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assert dateString != null;
        LocalDateTime futureDate = LocalDateTime.parse(dateString, formatter);

        return DateUtils.isConfigDatePassed(futureDate);
    }

    private String getTimeUntilFutureDateAsString(String kit, Player target) {
        String dateString = DataManager.Users.getUser(target.getUniqueId()).node("date", "kits", kit).getString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        assert dateString != null;
        LocalDateTime futureDate = LocalDateTime.parse(dateString, formatter);
        return DateUtils.getTimeUntilFutureDateAsString(futureDate, kit);
    }
    
}
