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
 * Copyright (c) 2025 Blueva Development. All rights reserved.
 */

package net.blueva.core.configuration;

import net.blueva.core.Main;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

public class DataManager {

    public static class Users {

        private static File file;
        private static GsonConfigurationLoader loader;
        private static ConfigurationNode user;

        public static void changeUserReference(String uuid) {
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
            return user;
        }

        public static void saveUser(UUID uuid) {
            try {
                loader.save(user);
            } catch (ConfigurateException e) {
                throw new RuntimeException(e);
            }
        }

        public static void reloadUser(UUID uuid) {
            try {
                user = loader.load();
            } catch (ConfigurateException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public static class Modules {
        public static class Kits {
            private static File file;
            private static GsonConfigurationLoader loader;
            private static ConfigurationNode kit;

            public static void changeReference(String name) {
                file = new File(Main.getPlugin().getDataFolder() + "/data/modules/kits", "/" + name + ".json");
                loader = GsonConfigurationLoader.builder().file(file).build();

                try {
                    if (!file.exists()) {
                        InputStream in = Main.getPlugin().getClass().getResourceAsStream("/net/blueva/core/configuration/files/data/modules/kits/kitdatadefault.json");
                        if (in != null) {
                            Files.copy(in, file.toPath());

                        }
                    }
                    kit = loader.load();
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }

            public static ConfigurationNode get(String name) {
                return kit;
            }

            public static void save(String name) {
                try {
                    loader.save(kit);
                } catch (ConfigurateException e) {
                    throw new RuntimeException(e);
                }
            }

            public static void reload(String name) {
                try {
                    kit = loader.load();
                } catch (ConfigurateException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public static class Warps {
            private static File file;
            private static GsonConfigurationLoader loader;
            private static ConfigurationNode warp;

            public static void changeReference(String name) {
                file = new File(Main.getPlugin().getDataFolder() + "/data/modules/warps", "/" + name + ".json");
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
                return warp;
            }

            public static void save(String name) {
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
            private static File file;
            private static GsonConfigurationLoader loader;
            private static ConfigurationNode world;

            public static void changeReference(String name) {
                file = new File(Main.getPlugin().getDataFolder() + "/data/modules/worlds", "/" + name + ".json");
                loader = GsonConfigurationLoader.builder().file(file).build();

                try {
                    if (!file.exists()) {
                        InputStream in = Main.getPlugin().getClass().getResourceAsStream("/net/blueva/core/configuration/files/data/modules/worlds/worlddatadefault.json");
                        if (in != null) {
                            Files.copy(in, file.toPath());

                        }
                    }
                    world = loader.load();
                } catch (Exception e) {
                    e.fillInStackTrace();
                }
            }

            public static ConfigurationNode get(String name) {
                return world;
            }

            public static void save(String name) {
                try {
                    loader.save(world);
                } catch (ConfigurateException e) {
                    throw new RuntimeException(e);
                }
            }

            public static void reload(String name) {
                try {
                    world = loader.load();
                } catch (ConfigurateException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
