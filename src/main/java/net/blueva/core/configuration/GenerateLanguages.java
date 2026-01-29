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

import net.blueva.core.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class GenerateLanguages {
    private FileConfiguration glang = null;
    private File glangFile = null;

    private final Main main;

    public GenerateLanguages (Main main) {
        this.main = main;
    }

    public void generate() {
        try {
            generateFile("en_UK");
            generateFile("es_ES");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateFile(String file) throws IOException {
        glangFile = new File(main.getDataFolder()+"/languages/",file+".yml");
        glang = YamlConfiguration.loadConfiguration(glangFile);
        Reader defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource("net/blueva/core/configuration/files/languages/" + file + ".yml")), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        glang.setDefaults(defConfig);
        glang.options().copyDefaults(true);
        glang.save(glangFile);
    }

}