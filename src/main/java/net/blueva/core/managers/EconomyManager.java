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
import net.blueva.core.utils.MessagesUtil;
import org.bukkit.entity.Player;

public class EconomyManager {

    public static void depositMoney(Player player, double quantity, Main main) {
        if(!Main.vaultapi) {
            double playerMoney = main.configManager.getUser(player.getUniqueId()).getDouble("money");
            double newQuantity = playerMoney+quantity;
            if(allowModifyBalance(player, main, quantity)) {
                main.configManager.getUser(player.getUniqueId()).set("config", newQuantity);
                main.configManager.saveUser(player.getUniqueId());
            }
        } else {
            main.economyImplementer.depositPlayer(player, quantity);
        }
    }

    public static void withdrawMoney(Player player, double quantity, Main main) {
        if(!Main.vaultapi) {
            double playerMoney = main.configManager.getUser(player.getUniqueId()).getDouble("money");
            double newQuantity = playerMoney-quantity;
            if(allowModifyBalance(player, main, quantity)) {
                main.configManager.getUser(player.getUniqueId()).set("config", newQuantity);
                main.configManager.saveUser(player.getUniqueId());
            }
        } else {
            main.economyImplementer.withdrawPlayer(player, quantity);
        }
    }

    public static void setMoney(Player player, double quantity, Main main) {
        if(!Main.vaultapi) {
            if(allowModifyBalance(player, main, quantity)) {
                main.configManager.getUser(player.getUniqueId()).set("config", quantity);
                main.configManager.saveUser(player.getUniqueId());
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
            balance = main.configManager.getUser(player.getUniqueId()).getDouble("money");
        } else {
            balance = main.economyImplementer.getBalance(player);
        }

        return balance;
    }

    public static boolean allowModifyBalance(Player player, Main main, double quantity) {
        boolean bool = false;
        double min = main.configManager.getSettings().getDouble("economy.min_money");
        double max = main.configManager.getSettings().getDouble("economy.max_money");
        double currentBalance = balancePlayer(player, main);

        if (max == -1) {
            if (currentBalance + quantity >= min) {
                bool = true;
            }
        } else if (currentBalance + quantity >= min && currentBalance + quantity <= max) {
            bool = true;
        }

        if(!bool) {
            player.sendMessage(MessagesUtil.format(player, main.configManager.getLang().getString("messages.error.money_out_of_range")));
        }

        return bool;
    }

}
