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

package net.blueva.core.configuration;

import net.blueva.core.Main;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

public class DataManager {

    public static class Users {

        private static File file;
        private static GsonConfigurationLoader loader;
        private static ConfigurationNode user;

        private static void changeUserReference(String uuid) {
            file = new File(Main.getPlugin().getDataFolder() + "/data/users", "/" + uuid + ".json");
            loader = GsonConfigurationLoader.builder().file(file).build();

            try {
                if (!file.exists()) {
                    InputStream in = Main.getPlugin().getClass().getResourceAsStream("/net/blueva/core/configuration/files/data/users/userdatadefault.json");
                    if (in != null) {
                        Files.copy(in, file.toPath());

                    }
                }
                user = loader.load();
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }

        public static ConfigurationNode getUser(UUID uuid) {
            String uuidString = uuid.toString();
            changeUserReference(uuidString);
            return user;
        }

        public static void saveUser(UUID uuid) {
            String uuidString = uuid.toString();
            changeUserReference(uuidString);
            try {
                loader.save(user);
            } catch (ConfigurateException e) {
                throw new RuntimeException(e);
            }
        }

        public static void reloadUser(UUID uuid) {
            String uuidString = uuid.toString();
            changeUserReference(uuidString);
            try {
                user = loader.load();
            } catch (ConfigurateException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public static class Modules {
        public static class Kits {

        }
        public static class Warps {
            private static File file;
            private static GsonConfigurationLoader loader;
            private static ConfigurationNode warp;

            private static void changeReference(String name) {
                file = new File(Main.getPlugin().getDataFolder() + "/data/users", "/" + name + ".json");
                loader = GsonConfigurationLoader.builder().file(file).build();

                try {
                    if (!file.exists()) {
                        InputStream in = Main.getPlugin().getClass().getResourceAsStream("/net/blueva/core/configuration/files/data/modules/warps/warpdatadefault.json");
                        if (in != null) {
                            Files.copy(in, file.toPath());

                        }
                    }
                    warp = loader.load();
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }

            public static ConfigurationNode get(String name) {
                changeReference(name);
                return warp;
            }

            public static void save(String name) {
                changeReference(name);
                try {
                    loader.save(warp);
                } catch (ConfigurateException e) {
                    throw new RuntimeException(e);
                }
            }

            public static void reload(UUID uuid) {
                String uuidString = uuid.toString();
                try {
                    warp = loader.load();
                } catch (ConfigurateException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        public static class Worlds {

        }
    }
}
