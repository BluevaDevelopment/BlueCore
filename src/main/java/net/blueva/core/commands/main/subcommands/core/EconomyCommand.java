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

package net.blueva.core.commands.main.subcommands.core;

import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.modules.economy.EconomyModule;
import net.blueva.core.utils.MessagesUtils;
import net.blueva.core.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import net.blueva.core.commands.CommandInterface;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class EconomyCommand implements CommandInterface {

    private final Main main;

    public EconomyCommand(Main main) {
        this.main = main;
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) throws IOException {
        if(args.length == 3) {
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.player_offline"));
                return;
            }

            double quantity = 0;
            if(StringUtils.isNumber(args[2])) {
                quantity = Double.parseDouble(args[2]);
            } else {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_economy_command"));
                return;
            }

            if(args[0].equalsIgnoreCase("deposit")) {
                if(sender.hasPermission("bluecore.economy.deposit")) {
                    try {
                        EconomyModule.depositMoney(player, quantity, main);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MessagesUtils.sendToSender(player, Objects.requireNonNull(ConfigManager.language.getString("messages.success.money_deposited"))
                            .replace("{amount}", String.valueOf(quantity)));
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                }
            } else if(args[0].equalsIgnoreCase("withdraw")) {
                if(sender.hasPermission("bluecore.economy.withdraw")) {
                    try {
                        EconomyModule.withdrawMoney(player, quantity, main);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MessagesUtils.sendToSender(player, Objects.requireNonNull(ConfigManager.language.getString("messages.success.money_withdrawn"))
                            .replace("{amount}", String.valueOf(quantity)));
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                }
            } else if(args[0].equalsIgnoreCase("set")) {
                if(sender.hasPermission("bluecore.economy.set")) {
                    try {
                        EconomyModule.setMoney(player, quantity, main);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MessagesUtils.sendToSender(player, Objects.requireNonNull(ConfigManager.language.getString("messages.success.money_set"))
                            .replace("{amount}", String.valueOf(quantity)));
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                }
            } else {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_economy_command"));
            }
        } else if(args.length == 2) {
            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.player_offline"));
                return;
            }

            if(args[0].equalsIgnoreCase("balance")) {
                if(sender.hasPermission("bluecore.economy.balance")) {
                    MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.info.current_balance_other"));
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                }
            } else if(args[0].equalsIgnoreCase("reset")) {
                if(sender.hasPermission("bluecore.economy.reset")) {
                    try {
                        EconomyModule.setMoney(player, 0, main);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    MessagesUtils.sendToSender(player, ConfigManager.language.getString("messages.success.money_reset"));
                } else {
                    MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.error.no_perms"));
                }
            } else {
                MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_economy_command"));
            }
        } else {
            MessagesUtils.sendToSender(sender, ConfigManager.language.getString("messages.other.use_economy_command"));
        }

        return;
    }
}