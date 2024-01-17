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

        private static File userFile;
        private static GsonConfigurationLoader userLoader;
        private static ConfigurationNode user;

        private static void changeUserReference(String uuid) {
            userFile = new File(Main.getPlugin().getDataFolder() + "/data/users", "/" + uuid + ".json");
            userLoader = GsonConfigurationLoader.builder().file(userFile).build();

            try {
                if (!userFile.exists()) {
                    InputStream in = Main.getPlugin().getClass().getResourceAsStream("/net/blueva/core/configuration/files/data/users/userdatadefault.json");
                    if (in != null) {
                        Files.copy(in, userFile.toPath());

                    }
                }
                user = userLoader.load();
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
                userLoader.save(user);
            } catch (ConfigurateException e) {
                throw new RuntimeException(e);
            }
        }

        public static void reloadUser(UUID uuid) {
            String uuidString = uuid.toString();
            changeUserReference(uuidString);
            try {
                user = userLoader.load();
            } catch (ConfigurateException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public static class Modules {
        public static class Kits {

        }
        public static class Warps {

        }
        public static class Worlds {

        }
    }
}
