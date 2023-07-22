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
 * GitHub repository: https://github.com/BluevaDevelopment/BlueMenu
 *
 * Copyright (c) 2023 Blueva Development. All rights reserved.
 */
package net.blueva.core.libraries.vault;

import net.blueva.core.Main;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class EconomyImplementer implements Economy {
    private final Main main = Main.getPlugin();

    @Override
    public boolean isEnabled() {
        return Main.vaultapi;
    }

    @Override
    public String getName() {
        return "BlueCore";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double v) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String s) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean hasAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }

    @Override
    public double getBalance(String s) {
        Player player = Bukkit.getPlayer(s);
        assert player != null;
        UUID uuid = player.getUniqueId();
        return main.playerBank.get(uuid);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        UUID uuid = offlinePlayer.getUniqueId();
        return main.playerBank.get(uuid);
    }

    @Override
    public double getBalance(String s, String s1) {
        Player player = Bukkit.getPlayer(s);
        assert player != null;
        UUID uuid = player.getUniqueId();
        return main.playerBank.get(uuid);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        UUID uuid = offlinePlayer.getUniqueId();
        return main.playerBank.get(uuid);
    }

    @Override
    public boolean has(String s, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return false;
    }

    @Override
    public boolean has(String s, String s1, double v) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, double v) {
        Player player = Bukkit.getPlayer(s);
        assert player != null;
        UUID uuid = player.getUniqueId();
        double oldBalance = main.playerBank.get(uuid);
        double newBalance = oldBalance - v;
        main.playerBank.put(uuid, newBalance);
        main.configManager.getUser(player.getUniqueId()).set("money", newBalance);
        main.configManager.saveUser(player.getUniqueId());
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldBalance = main.playerBank.get(uuid);
        double newBalance = oldBalance - v;
        main.playerBank.put(uuid, newBalance);
        main.configManager.getUser(offlinePlayer.getUniqueId()).set("money", newBalance);
        main.configManager.saveUser(offlinePlayer.getUniqueId());
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(String s, String s1, double v) {
        Player player = Bukkit.getPlayer(s);
        assert player != null;
        UUID uuid = player.getUniqueId();
        double oldBalance = main.playerBank.get(uuid);
        double newBalance = oldBalance - v;
        main.playerBank.put(uuid, newBalance);
        main.configManager.getUser(player.getUniqueId()).set("money", newBalance);
        main.configManager.saveUser(player.getUniqueId());
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldBalance = main.playerBank.get(uuid);
        double newBalance = oldBalance - v;
        main.playerBank.put(uuid, newBalance);
        main.configManager.getUser(offlinePlayer.getUniqueId()).set("money", newBalance);
        main.configManager.saveUser(offlinePlayer.getUniqueId());
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String s, double v) {
        Player player = Bukkit.getPlayer(s);
        assert player != null;
        UUID uuid = player.getUniqueId();
        double oldBalance = main.playerBank.get(uuid);
        double newBalance = oldBalance + v;
        main.playerBank.put(uuid, newBalance);
        main.configManager.getUser(player.getUniqueId()).set("money", newBalance);
        main.configManager.saveUser(player.getUniqueId());
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldBalance = main.playerBank.get(uuid);
        double newBalance = oldBalance + v;
        main.playerBank.put(uuid, newBalance);
        main.configManager.getUser(offlinePlayer.getUniqueId()).set("money", newBalance);
        main.configManager.saveUser(offlinePlayer.getUniqueId());
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String s, String s1, double v) {
        Player player = Bukkit.getPlayer(s);
        assert player != null;
        UUID uuid = player.getUniqueId();
        double oldBalance = main.playerBank.get(uuid);
        double newBalance = oldBalance + v;
        main.playerBank.put(uuid, newBalance);
        main.configManager.getUser(player.getUniqueId()).set("money", newBalance);
        main.configManager.saveUser(player.getUniqueId());
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        UUID uuid = offlinePlayer.getUniqueId();
        double oldBalance = main.playerBank.get(uuid);
        double newBalance = oldBalance + v;
        main.playerBank.put(uuid, newBalance);
        main.configManager.getUser(offlinePlayer.getUniqueId()).set("money", newBalance);
        main.configManager.saveUser(offlinePlayer.getUniqueId());
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String s) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}
