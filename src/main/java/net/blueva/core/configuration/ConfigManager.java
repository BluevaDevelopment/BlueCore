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
import java.util.UUID;

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

    public static class Data {
        public static YamlDocument kit;
        public static YamlDocument warp;
        public static YamlDocument world;
        public static YamlDocument user;


        // BlueCore/data/modules/kits/kit.yml
        public static void registerKitDocument(String name) {
            try {
                kit = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/data/modules/kits/", name+".yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/data/modules/kits/kitdatadefault.yml")),
                        GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().build());
            } catch (IOException ex) {
                ex.fillInStackTrace();
            }
        }

        public static YamlDocument getKitDocument(String kit) {
            registerKitDocument(kit);
            return user;
        }

        // BlueCore/data/modules/warps/warp.yml
        public static void registerWarpDocument(String name) {
            try {
                warp = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/data/modules/worlds/", name+".yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/data/modules/worlds/worlddatadefault.yml")),
                        GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().build());
            } catch (IOException ex) {
                ex.fillInStackTrace();
            }
        }

        public static YamlDocument getWarpDocument(String warp) {
            registerWarpDocument(warp);
            return user;
        }

        // BlueCore/data/modules/worlds/world.yml
        public static void changeWorldReference(String name) {
            try {
                world = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/data/modules/worlds/", name+".yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/data/modules/worlds/worlddatadefault.yml")),
                        GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(false).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().build());
            } catch (IOException ex) {
                ex.fillInStackTrace();
            }
        }

        // BlueCore/data/users/uuid.yml
        public static void registerUserDocument(UUID uuid) {
            try {
                user = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/data/users/", uuid.toString()+".yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/data/users/userdatadefault.yml")),
                        GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().build());
            } catch (IOException ex) {
                ex.fillInStackTrace();
            }
        }

        public static YamlDocument getUserDocument(UUID uuid) {
            registerUserDocument(uuid);
            return user;
        }
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

        // Data -> Modules folder
        File data_modules_worldsf = new File(Main.getPlugin().getDataFolder()+"/data/modules/worlds");
        if(!data_modules_worldsf.exists()) {
            data_modules_worldsf.mkdirs();
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

        // BlueCore/modules/scoreboards.yml
        try {
            Modules.scoreboards = YamlDocument.create(new File(Main.getPlugin().getDataFolder()+"/modules/", "scoreboards.yml"), Objects.requireNonNull(Main.getPlugin().getResource("net/blueva/core/configuration/files/modules/scoreboards.yml")),
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



    //commands.yml file
    /*
    //individual user file
    public FileConfiguration getUser(UUID uuid) {
        reloadUser(uuid);
        return main.user;
    }

    public void reloadUser(UUID uuid){
        main.userFile = new File(main.getDataFolder()+"/data/users/"+uuid+".yml");
        main.user = YamlConfiguration.loadConfiguration(main.userFile);
        Reader defConfigStream;
        defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource("net/blueva/core/configuration/files/data/userdatadefault.yml")), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        main.user.setDefaults(defConfig);
    }

    public void saveUser(UUID uuid){
        try{
            main.user.save(main.userFile);
            //ConfigUpdater.update(main, "net/blueva/arcade/configuration/files/data/userdatadefault.yml", new File(main.getDataFolder()+"/data/"+userid+".yml"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerUser(UUID uuid){
        main.userFile = new File(main.getDataFolder()+"/data/users/"+uuid+".yml");
        if(!main.userFile.exists()){
            this.getUser(uuid).options().copyDefaults(true);
            saveUser(uuid);
        }
    }

    //individual kit file
    public FileConfiguration getKit(String name) {
        reloadKit(name);
        return main.kit;
    }

    public void reloadKit(String name){
        main.kitFile = new File(main.getDataFolder()+"/data/kits/"+name+".yml");
        main.kit = YamlConfiguration.loadConfiguration(main.kitFile);
        Reader defConfigStream;
        defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource("net/blueva/core/configuration/files/data/kitdatadefault.yml")), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        main.kit.setDefaults(defConfig);
    }

    public void saveKit(String name){
        try{
            main.kit.save(main.kitFile);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerKit(String name){
        main.kitFile = new File(main.getDataFolder()+"/data/kits/"+name+".yml");
        if(!main.kitFile.exists()){
            this.getKit(name).options().copyDefaults(true);
            saveKit(name);
        }
    }

    //actual lang manager
    //lang.yml file
    public FileConfiguration getLang() {
        if(main.language == null) {
            reloadLang();
        }
        return main.language;
    }

    public void reloadLang(){
        if(main.language == null){
            main.languageFile = new File(main.getDataFolder()+"/language/",main.actualLang+".yml");
        }
        main.language = YamlConfiguration.loadConfiguration(main.languageFile);
        Reader defConfigStream;
        defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource("net/blueva/core/configuration/files/language/" + main.actualLang + ".yml")), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        main.language.setDefaults(defConfig);
    }

    public void saveLang(){
        try{
            main.language.save(main.languageFile);
            ConfigUpdater.update(main, "net/blueva/core/configuration/files/language/"+main.actualLang+".yml", new File(main.getDataFolder()+"/language/"+main.actualLang+".yml"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerLang(){
        main.languageFile = new File(main.getDataFolder()+"/language/",main.actualLang+".yml");
        if(!main.languageFile.exists()){
            this.getLang().options().copyDefaults(true);
            main.langPath = getLang().getCurrentPath();
            saveLang();
        }
    }*/
}
