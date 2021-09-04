package whirss.thenexus;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager extends JavaPlugin implements Listener {
	
	private FileConfiguration settings = null;
	private File settingsFile = null;
	private FileConfiguration kits = null;
	private File kitsFile = null;
	private FileConfiguration warps = null;
	private File warpsFile = null;
	private FileConfiguration messages = null;
	private File messagesFile = null;
	private FileConfiguration userdata = null;
	private File userdataFile = null;
	
	//settings.yml:
  	public FileConfiguration getSettings() {
  		if(settings == null) {
  			reloadSettings();
  		}
  		return settings;
  	}
  	
  	public void reloadSettings(){
  		if(settings == null){
  			settingsFile = new File(getDataFolder(),"settings.yml");
  		}
  		settings = YamlConfiguration.loadConfiguration(settingsFile);
  		Reader defConfigStream;
  		try{
  			defConfigStream = new InputStreamReader(this.getResource("settings.yml"),"UTF8");
  			if(defConfigStream != null){
  				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
  				settings.setDefaults(defConfig);
  			}			
  		}catch(UnsupportedEncodingException e){
  			e.printStackTrace();
  		}
  	}
  	
  	public void saveSettings(){
  		try{
  			settings.save(settingsFile);			
  		}catch(IOException e){
  			e.printStackTrace();
  		}
  	}
   
  	public void registerSettings(){
  		settingsFile = new File(this.getDataFolder(),"settings.yml");
  		if(!settingsFile.exists()){
  			this.getSettings().options().copyDefaults(true);
  			getSettings().options().header(" _____ _          _   _\r\n"
  					+ "|_   _| |__   ___| \\ | | _____  ___   _ ___\r\n"
  					+ "  | | | '_ \\ / _ |  \\| |/ _ \\ \\/ | | | / __|\r\n"
  					+ "  | | | | | |  __| |\\  |  __/>  <| |_| \\__ \\\r\n"
  					+ "  |_| |_| |_|\\___|_| \\_|\\___/_/\\_\\\\__,_|___/\r\n"
  					+ "\r\n"
  					+ "");
  			saveSettings();
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
  			userdataFile = new File(getDataFolder(),uuid+".yml");
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
  		userdataFile = new File(this.getDataFolder(),uuid+".yml");
  		if(!userdataFile.exists()){
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
