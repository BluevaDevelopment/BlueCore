package whirss.thenexus;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import whirss.thenexus.events.OnPlayerJoin;

public final class Main extends JavaPlugin {
	
	private FileConfiguration kits = null;
	private File kitsFile = null;
	private FileConfiguration warps = null;
	private File warpsFile = null;
	private FileConfiguration messages = null;
	private File messagesFile = null;
	private FileConfiguration userdata = null;
	private File userdataFile = null;
	
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " _____ _          _   _");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "|_   _| |__   ___| \\ | | _____  ___   _ ___");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "  | | | '_ \\ / _ |  \\| |/ _ \\ \\/ | | | / __|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "  | | | | | |  __| |\\  |  __/>  <| |_| \\__ \\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "  |_| |_| |_|\\___|_| \\_|\\___/_/\\_\\\\__,_|___/");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "");
		
		RegisterEvents();
		
		registerConfig();
		registerKits();
		registerMessages();
		registerWarps();
		
		if(getConfig().getBoolean("metrics")) {
			int pluginId = 12700; 
	        new Metrics(this, pluginId);
		}
		
	}
	
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " _____ _          _   _");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|_   _| |__   ___| \\ | | _____  ___   _ ___");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "  | | | '_ \\ / _ |  \\| |/ _ \\ \\/ | | | / __|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "  | | | | | |  __| |\\  |  __/>  <| |_| \\__ \\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "  |_| |_| |_|\\___|_| \\_|\\___/_/\\_\\\\__,_|___/");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "");
	}
	
	public void RegisterEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new OnPlayerJoin(this), this);
		
	}
	
	//config.yml
	public void registerConfig(){
		File config = new File(this.getDataFolder(),"config.yml");
		if(!config.exists()){
			Bukkit.getConsoleSender().sendMessage("[TheNexus] Creating new file: " + getDataFolder()+"\\config.yml");
			this.getConfig().options().copyDefaults(true);
			getConfig().options().header(" _____ _          _   _\r\n"
  					+ "|_   _| |__   ___| \\ | | _____  ___   _ ___\r\n"
  					+ "  | | | '_ \\ / _ |  \\| |/ _ \\ \\/ | | | / __|\r\n"
  					+ "  | | | | | |  __| |\\  |  __/>  <| |_| \\__ \\\r\n"
  					+ "  |_| |_| |_|\\___|_| \\_|\\___/_/\\_\\\\__,_|___/\r\n"
  					+ "\r\n"
  					+ "");
			saveConfig();
		}
	}
  	
  //messages.yml:
  	public FileConfiguration getMessages() {
  		if(messages == null) {
  			reloadMessages();
  		}
  		return messages;
  	}
  	
  	public void reloadMessages(){
  		if(messages == null){
  			messagesFile = new File(getDataFolder(),"messages.yml");
  		}
  		messages = YamlConfiguration.loadConfiguration(messagesFile);
  		Reader defConfigStream;
  		try{
  			defConfigStream = new InputStreamReader(this.getResource("messages.yml"),"UTF8");
  			if(defConfigStream != null){
  				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
  				messages.setDefaults(defConfig);
  			}			
  		}catch(UnsupportedEncodingException e){
  			e.printStackTrace();
  		}
  	}
  	
  	public void saveMessages(){
  		try{
  			messages.save(messagesFile);			
  		}catch(IOException e){
  			e.printStackTrace();
  		}
  	}
   
  	public void registerMessages(){
  		messagesFile = new File(this.getDataFolder(),"messages.yml");
  		if(!messagesFile.exists()){
  			Bukkit.getConsoleSender().sendMessage("[TheNexus] Creating new file: " + getDataFolder()+"\\messages.yml");
  			this.getMessages().options().copyDefaults(true);
  			getMessages().options().header(" _____ _          _   _\r\n"
  					+ "|_   _| |__   ___| \\ | | _____  ___   _ ___\r\n"
  					+ "  | | | '_ \\ / _ |  \\| |/ _ \\ \\/ | | | / __|\r\n"
  					+ "  | | | | | |  __| |\\  |  __/>  <| |_| \\__ \\\r\n"
  					+ "  |_| |_| |_|\\___|_| \\_|\\___/_/\\_\\\\__,_|___/\r\n"
  					+ "\r\n"
  					+ "");
  			saveMessages();
  		}
  	}
  	
  //kits.yml:
  	public FileConfiguration getKits() {
  		if(kits == null) {
  			reloadKits();
  		}
  		return kits;
  	}
  	
  	public void reloadKits(){
  		if(kits == null){
  			kitsFile = new File(getDataFolder(),"kits.yml");
  		}
  		kits = YamlConfiguration.loadConfiguration(kitsFile);
  		Reader defConfigStream;
  		try{
  			defConfigStream = new InputStreamReader(this.getResource("kits.yml"),"UTF8");
  			if(defConfigStream != null){
  				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
  				kits.setDefaults(defConfig);
  			}			
  		}catch(UnsupportedEncodingException e){
  			e.printStackTrace();
  		}
  	}
  	
  	public void saveKits(){
  		try{
  			kits.save(kitsFile);			
  		}catch(IOException e){
  			e.printStackTrace();
  		}
  	}
   
  	public void registerKits(){
  		kitsFile = new File(this.getDataFolder(),"kits.yml");
  		if(!kitsFile.exists()){
  			Bukkit.getConsoleSender().sendMessage("[TheNexus] Creating new file: " + getDataFolder()+"\\kits.yml");
  			this.getKits().options().copyDefaults(true);
  			getKits().options().header(" _____ _          _   _\r\n"
  					+ "|_   _| |__   ___| \\ | | _____  ___   _ ___\r\n"
  					+ "  | | | '_ \\ / _ |  \\| |/ _ \\ \\/ | | | / __|\r\n"
  					+ "  | | | | | |  __| |\\  |  __/>  <| |_| \\__ \\\r\n"
  					+ "  |_| |_| |_|\\___|_| \\_|\\___/_/\\_\\\\__,_|___/\r\n"
  					+ "\r\n"
  					+ "");
  			saveKits();
  		}
  	}
  	
  	
  //warps.yml:
  	public FileConfiguration getWarps() {
  		if(warps == null) {
  			reloadWarps();
  		}
  		return warps;
  	}
  	
  	public void reloadWarps(){
  		if(warps == null){
  			warpsFile = new File(getDataFolder(),"warps.yml");
  		}
  		warps = YamlConfiguration.loadConfiguration(warpsFile);
  		Reader defConfigStream;
  		try{
  			defConfigStream = new InputStreamReader(this.getResource("warps.yml"),"UTF8");
  			if(defConfigStream != null){
  				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
  				warps.setDefaults(defConfig);
  			}			
  		}catch(UnsupportedEncodingException e){
  			e.printStackTrace();
  		}
  	}
  	
  	public void saveWarps(){
  		try{
  			warps.save(warpsFile);			
  		}catch(IOException e){
  			e.printStackTrace();
  		}
  	}
   
  	public void registerWarps(){
  		warpsFile = new File(this.getDataFolder(),"warps.yml");
  		if(!warpsFile.exists()){
  			Bukkit.getConsoleSender().sendMessage("[TheNexus] Creating new file: " + getDataFolder()+"\\warps.yml");
  			this.getWarps().options().copyDefaults(true);
  			getWarps().options().header(" _____ _          _   _\r\n"
  					+ "|_   _| |__   ___| \\ | | _____  ___   _ ___\r\n"
  					+ "  | | | '_ \\ / _ |  \\| |/ _ \\ \\/ | | | / __|\r\n"
  					+ "  | | | | | |  __| |\\  |  __/>  <| |_| \\__ \\\r\n"
  					+ "  |_| |_| |_|\\___|_| \\_|\\___/_/\\_\\\\__,_|___/\r\n"
  					+ "\r\n"
  					+ "");
  			saveWarps();
  		}
  	}
  	
  //userdata.yml:
  	public FileConfiguration getUserdata(UUID uuid) {
  		if(userdata == null) {
  			reloadUserdata(uuid);
  		}
  		return userdata;
  	}
  	
  	public void reloadUserdata(UUID uuid){
  		if(userdata == null){
  			userdataFile = new File(getDataFolder()+"/userdata",uuid+".yml");
  		}
  		userdata = YamlConfiguration.loadConfiguration(userdataFile);
  		Reader defConfigStream;
  		try{
  			defConfigStream = new InputStreamReader(this.getResource("userdata.yml"),"UTF8");
  			if(defConfigStream != null){
  				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
  				userdata.setDefaults(defConfig);
  			}			
  		}catch(UnsupportedEncodingException e){
  			e.printStackTrace();
  		}
  	}
  	
  	public void saveUserdata(){
  		try{
  			userdata.save(userdataFile);			
  		}catch(IOException e){
  			e.printStackTrace();
  		}
  	}
   
  	public void registerUserdata(UUID uuid){
  		userdataFile = new File(this.getDataFolder()+"/userdata",uuid+".yml");
  		if(!userdataFile.exists()){
  			Bukkit.getConsoleSender().sendMessage("[TheNexus] Creating new file: " + getDataFolder()+"\\userdata\\"+uuid+".yml");
  			Bukkit.getConsoleSender().sendMessage("");
  			this.getUserdata(uuid).options().copyDefaults(true);
  			getUserdata(uuid).options().header(" _____ _          _   _\r\n"
  					+ "|_   _| |__   ___| \\ | | _____  ___   _ ___\r\n"
  					+ "  | | | '_ \\ / _ |  \\| |/ _ \\ \\/ | | | / __|\r\n"
  					+ "  | | | | | |  __| |\\  |  __/>  <| |_| \\__ \\\r\n"
  					+ "  |_| |_| |_|\\___|_| \\_|\\___/_/\\_\\\\__,_|___/\r\n"
  					+ "\r\n"
  					+ "");
  			saveUserdata();
  		}
  	}

}
