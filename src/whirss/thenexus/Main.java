package whirss.thenexus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
		
	public void OnEnable(ConfigManager cm) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + " _____ _          _   _");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "|_   _| |__   ___| \\ | | _____  ___   _ ___");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "  | | | '_ \\ / _ |  \\| |/ _ \\ \\/ | | | / __|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "  | | | | | |  __| |\\  |  __/>  <| |_| \\__ \\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "  |_| |_| |_|\\___|_| \\_|\\___/_/\\_\\\\__,_|___/");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "");
		
		cm.registerSettings();
		cm.registerKits();
		cm.registerMessages();
		cm.registerWarps();
		
	}
	
	public void OnDisable() {
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + " _____ _          _   _");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|_   _| |__   ___| \\ | | _____  ___   _ ___");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "  | | | '_ \\ / _ |  \\| |/ _ \\ \\/ | | | / __|");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "  | | | | | |  __| |\\  |  __/>  <| |_| \\__ \\");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "  |_| |_| |_|\\___|_| \\_|\\___/_/\\_\\\\__,_|___/");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "");
	}

}
