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
 * Copyright (c) 2023 Blueva Development. All rights reserved.
 */

package net.blueva.core.managers;

import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.entity.Player;

import java.io.IOException;

public class EconomyManager {

    public static void depositMoney(Player player, double quantity, Main main) throws IOException {
        if(!Main.vaultapi) {
            double playerMoney = ConfigManager.Data.getUserDocument(player.getUniqueId()).getDouble("money");
            double newQuantity = playerMoney+quantity;
            if(allowModifyBalance(player, main, quantity)) {
                ConfigManager.Data.getUserDocument(player.getUniqueId()).set("config", newQuantity);
                ConfigManager.Data.getUserDocument(player.getUniqueId()).save();
            }
        } else {
            main.economyImplementer.depositPlayer(player, quantity);
        }
    }

    public static void withdrawMoney(Player player, double quantity, Main main) throws IOException {
        if(!Main.vaultapi) {
            double playerMoney = ConfigManager.Data.getUserDocument(player.getUniqueId()).getDouble("money");
            double newQuantity = playerMoney-quantity;
            if(allowModifyBalance(player, main, quantity)) {
                ConfigManager.Data.getUserDocument(player.getUniqueId()).set("config", newQuantity);
                ConfigManager.Data.getUserDocument(player.getUniqueId()).save();
            }
        } else {
            main.economyImplementer.withdrawPlayer(player, quantity);
        }
    }

    public static void setMoney(Player player, double quantity, Main main) throws IOException {
        if(!Main.vaultapi) {
            if(allowModifyBalance(player, main, quantity)) {
                ConfigManager.Data.getUserDocument(player.getUniqueId()).set("config", quantity);
                ConfigManager.Data.getUserDocument(player.getUniqueId()).save();
            }
        } else {
            double actualBalance = main.economyImplementer.getBalance(player);
            main.economyImplementer.withdrawPlayer(player, actualBalance);
            main.economyImplementer.depositPlayer(player, quantity);
        }
    }

    public static double balancePlayer(Player player, Main main) {
        double balance = 0.0;
        if(!Main.vaultapi) {
            balance = ConfigManager.Data.getUserDocument(player.getUniqueId()).getDouble("money");
        } else {
            balance = main.economyImplementer.getBalance(player);
        }

        return balance;
    }

    public static boolean allowModifyBalance(Player player, Main main, double quantity) {
        boolean bool = false;
        double min = ConfigManager.Modules.economy.getDouble("economy.min_money");
        double max = ConfigManager.Modules.economy.getDouble("economy.max_money");
        double currentBalance = balancePlayer(player, main);

        if (max == -1) {
            if (currentBalance + quantity >= min) {
                bool = true;
            }
        } else if (currentBalance + quantity >= min && currentBalance + quantity <= max) {
            bool = true;
        }

        if(!bool) {
            player.sendMessage(MessagesUtil.format(player, ConfigManager.language.getString("messages.error.money_out_of_range")));
        }

        return bool;
    }

}
