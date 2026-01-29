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

package net.blueva.core.configuration;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import net.blueva.core.Main;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigManager {

    public static YamlDocument settings;
    public static YamlDocument language;

    public static class Modules {
        public static YamlDocument auth;
        public static YamlDocument bossbar;
        public static YamlDocument chat;
        public static YamlDocument commands;
        public static YamlDocument economy;
        public static YamlDocument holograms;
        public static YamlDocument kits;
        public static YamlDocument permissions;
        public static YamlDocument scoreboards;
        public static YamlDocument tablist;
        public static YamlDocument warps;
        public static YamlDocument welcome;
        public static YamlDocument worlds;
    }

    public static void generateFolders() {
        if(!Main.getPlugin().getDataFolder().exists()) {
            Main.getPlugin().getDataFolder().mkdirs();
        }

        // Backups folder
        File backupf = new File(Main.getPlugin().getDataFolder()+"/backups");
        if(!backupf.exists()) {
            backupf.mkdirs();
        }

        // Data folder
        File dataf = new File(Main.getPlugin().getDataFolder()+"/data");
        if(!dataf.exists()) {
            dataf.mkdirs();
        }

        // Language folder
        File languagesf = new File(Main.getPlugin().getDataFolder()+"/languages");
        if(!languagesf.exists()) {
            languagesf.mkdirs();

            GenerateLanguages gl = new GenerateLanguages(Main.getPlugin());
            gl.generate();
        }

        // Modules folder
        File modulesf = new File(Main.getPlugin().getDataFolder()+"/modules");
        if(!modulesf.exists()) {
            modulesf.mkdirs();
        }


        // Data -> Modules folder
        File data_modulesf = new File(Main.getPlugin().getDataFolder()+"/data/modules");
        if(!data_modulesf.exists()) {
            data_modulesf.mkdirs();
        }

        // Data -> Modules --> Worlds folder
        File data_modules_worldsf = new File(Main.getPlugin().getDataFolder()+"/data/modules/worlds");
        if(!data_modules_worldsf.exists()) {
            data_modules_worldsf.mkdirs();
        }

        // Data -> Modules --> Warps folder
        File data_modules_warpsf = new File(Main.getPlugin().getDataFolder()+"/data/modules/warps");
        if(!data_modules_warpsf.exists()) {
            data_modules_warpsf.mkdirs();
        }

        // Data -> Modules --> Kits folder
        File data_modules_kitsf = new File(Main.getPlugin().getDataFolder()+"/data/modules/kits");
        if(!data_modules_kitsf.exists()) {
            data_modules_kitsf.mkdirs();
        }

        // Data -> Users folder
        File data_usersf = new File(Main.getPlugin().getDataFolder()+"/data/users");
        if(!data_usersf.exists()) {
            data_usersf.mkdirs();
        }
    }

    public static void registerDocuments() {
        // BlueCore/settings.yml
        try {
            settings = YamlDocument.create(new File(Main.getPlugin().getDataFolder(), "settings.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/settings.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/language/xx.XX.yml
        Main.getPlugin().actualLang = settings.getString("language.main");
        try {
            language = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/languages/", Main.getPlugin().actualLang+".yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/languages/"+Main.getPlugin().actualLang+".yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/auth.yml
        try {
            Modules.auth = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "auth.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/auth.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/bossbar.yml
        try {
            Modules.bossbar = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "bossbar.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/bossbar.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/chat.yml
        try {
            Modules.chat = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "chat.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/chat.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/commands.yml
        try {
            Modules.commands = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "commands.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/commands.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/economy.yml
        try {
            Modules.economy = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "economy.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/economy.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/holograms.yml
        try {
            Modules.holograms = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "holograms.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/holograms.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/kits.yml
        try {
            Modules.kits = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "kits.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/kits.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/permissions.yml
        try {
            Modules.permissions = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "permissions.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/permissions.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/scoreboard.yml
        try {
            Modules.scoreboards = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "scoreboard.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/scoreboard.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/tablist.yml
        try {
            Modules.tablist = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "tablist.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/tablist.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/warps.yml
        try {
            Modules.warps = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "warps.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/warps.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/welcome.yml
        try {
            Modules.welcome = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "welcome.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/welcome.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }

        // BlueCore/modules/worlds.yml
        try {
            Modules.worlds = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "worlds.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/worlds.yml")),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file_version")).build());
        } catch (IOException ex) {
            ex.fillInStackTrace();
        }
    }
}