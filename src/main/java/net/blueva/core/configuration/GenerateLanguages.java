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
        glangFile = new File(main.getDataFolder()+"/language/",file+".yml");
        glang = YamlConfiguration.loadConfiguration(glangFile);
        Reader defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource("net/blueva/core/configuration/files/language/" + file + ".yml")), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        glang.setDefaults(defConfig);
        glang.options().copyDefaults(true);
        glang.save(glangFile);
    }
}