package net.blueva.core.configuration;

import net.blueva.core.Main;
import net.blueva.core.configuration.updater.ConfigUpdater;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

public class ConfigManager {

    private final Main main;

    public ConfigManager(Main main) {
        this.main = main;
    }

    public void generateFolders() {
        if(!main.getDataFolder().exists()) {
            main.getDataFolder().mkdirs();
        }

        // Language folder
        File languagef = new File(main.getDataFolder()+"/language");
        if(!languagef.exists()) {
            languagef.mkdirs();

            GenerateLanguages gl = new GenerateLanguages(main);
            gl.generate();
        }

        // Backups folder
        File backupf = new File(main.getDataFolder()+"/backup");
        if(!backupf.exists()) {
            backupf.mkdirs();
        }

        // Data folder
        File dataf = new File(main.getDataFolder()+"/data");
        if(!dataf.exists()) {
            dataf.mkdirs();
        }

        // users folder
        File usersf = new File(main.getDataFolder()+"/data/users");
        if(!usersf.exists()) {
            usersf.mkdirs();
        }
    }

    //commands.yml file
    public FileConfiguration getCommands() {
        if(main.commands == null) {
            reloadCommands();
        }
        return main.commands;
    }

    public void reloadCommands(){
        if(main.commands == null){
            main.commandsFile = new File(main.getDataFolder(),"commands.yml");
        }
        main.commands = YamlConfiguration.loadConfiguration(main.commandsFile);
        Reader defConfigStream;
        defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource("net/blueva/core/configuration/files/commands.yml")), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        main.commands.setDefaults(defConfig);
    }

    public void saveCommands(){
        try{
            main.commands.save(main.commandsFile);
            ConfigUpdater.update(main, "net/blueva/core/configuration/files/commands.yml", new File(main.getDataFolder()+"/commands.yml"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerCommands(){
        main.commandsFile = new File(main.getDataFolder(),"commands.yml");
        if(!main.commandsFile.exists()){
            this.getCommands().options().copyDefaults(true);
            saveCommands();
        }
    }

    //settings.yml file
    public FileConfiguration getSettings() {
        if(main.settings == null) {
            reloadSettings();
        }
        return main.settings;
    }

    public void reloadSettings(){
        if(main.settings == null){
            main.settingsFile = new File(main.getDataFolder(),"settings.yml");
        }
        main.settings = YamlConfiguration.loadConfiguration(main.settingsFile);
        Reader defConfigStream;
        defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource("net/blueva/core/configuration/files/settings.yml")), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        main.settings.setDefaults(defConfig);
    }

    public void saveSettings(){
        try{
            main.settings.save(main.settingsFile);
            ConfigUpdater.update(main, "net/blueva/core/configuration/files/settings.yml", new File(main.getDataFolder()+"/settings.yml"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerSettings(){
        main.settingsFile = new File(main.getDataFolder(),"settings.yml");
        if(!main.settingsFile.exists()){
            this.getSettings().options().copyDefaults(true);
            saveSettings();
        }
    }

    //warps.yml file
    public FileConfiguration getWarps() {
        if(main.warps == null) {
            reloadWarps();
        }
        return main.warps;
    }

    public void reloadWarps(){
        if(main.warps == null){
            main.warpsFile = new File(main.getDataFolder(),"warps.yml");
        }
        main.warps = YamlConfiguration.loadConfiguration(main.warpsFile);
        Reader defConfigStream;
        defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource("net/blueva/core/configuration/files/warps.yml")), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        main.warps.setDefaults(defConfig);
    }

    public void saveWarps(){
        try{
            main.warps.save(main.warpsFile);
            ConfigUpdater.update(main, "net/blueva/core/configuration/files/warps.yml", new File(main.getDataFolder()+"/warps.yml"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerWarps(){
        main.warpsFile = new File(main.getDataFolder(),"warps.yml");
        if(!main.warpsFile.exists()){
            this.getWarps().options().copyDefaults(true);
            saveWarps();
        }
    }

    //worlds.yml file
    public FileConfiguration getWorlds() {
        if(main.worlds == null) {
            reloadWorlds();
        }
        return main.worlds;
    }

    public void reloadWorlds(){
        if(main.worlds == null){
            main.worldsFile = new File(main.getDataFolder(),"worlds.yml");
        }
        main.worlds = YamlConfiguration.loadConfiguration(main.worldsFile);
        Reader defConfigStream;
        defConfigStream = new InputStreamReader(Objects.requireNonNull(main.getResource("net/blueva/core/configuration/files/worlds.yml")), StandardCharsets.UTF_8);
        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        main.worlds.setDefaults(defConfig);
    }

    public void saveWorlds(){
        try{
            main.worlds.save(main.worldsFile);
            ConfigUpdater.update(main, "net/blueva/core/configuration/files/worlds.yml", new File(main.getDataFolder()+"/worlds.yml"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void registerWorlds(){
        main.worldsFile = new File(main.getDataFolder(),"worlds.yml");
        if(!main.worldsFile.exists()){
            this.getWorlds().options().copyDefaults(true);
            saveWorlds();
        }
    }

    //individual user file
    public FileConfiguration getUser(UUID uuid) {
        reloadUser(uuid);
        return main.user;
    }

    public void reloadUser(UUID uuid){
        main.userFile = new File(main.getDataFolder()+"/data/users/",uuid+".yml");
        main.user = YamlConfiguration.loadConfiguration(main.userFile);
        Reader defConfigStream;
        defConfigStream = new InputStreamReader(main.getResource("net/blueva/core/configuration/files/data/userdatadefault.yml"), StandardCharsets.UTF_8);
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
        main.userFile = new File(main.getDataFolder()+"/data/users/",uuid+".yml");
        if(!main.userFile.exists()){
            this.getUser(uuid).options().copyDefaults(true);
            saveUser(uuid);
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
    }
}
