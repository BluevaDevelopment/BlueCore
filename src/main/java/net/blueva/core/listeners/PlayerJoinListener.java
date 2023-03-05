package net.blueva.core.listeners;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;

import net.blueva.core.Main;
import net.blueva.core.utils.MessagesUtil;

public class PlayerJoinListener implements Listener {

	private final Main main;

	public PlayerJoinListener(Main main) {
		this.main = main;
	}

	@EventHandler
	public void OPJ(PlayerJoinEvent event) {
		main.configManager.registerUser(event.getPlayer().getUniqueId());

		if(main.configManager.getSettings().getBoolean("welcome.broadcast.join.enabled")) {
			event.setJoinMessage(MessagesUtil.format(event.getPlayer(), main.configManager.getSettings().getString("welcome.broadcast.join.message")).replace("%player_name%", event.getPlayer().getDisplayName()));
		} else {
			event.setJoinMessage("");
		}

		if(main.configManager.getSettings().getBoolean("welcome.message.enabled")) {
			List<String> description = main.configManager.getSettings().getStringList("welcome.message.list");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> {
				for (String message : description) {
					event.getPlayer().sendMessage(MessagesUtil.format(event.getPlayer(), message));
				}
			}, main.configManager.getSettings().getInt("welcome.message.wait"));
		}

		if(main.configManager.getSettings().getBoolean("welcome.title.enabled")) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> event.getPlayer().sendTitle(MessagesUtil.format(event.getPlayer(), main.configManager.getSettings().getString("welcome.title.title")), MessagesUtil.format(event.getPlayer(), main.configManager.getSettings().getString("welcome.title.subtitle")), main.configManager.getSettings().getInt("welcome.title.fade_in"), main.configManager.getSettings().getInt("welcome.title.stay"), main.configManager.getSettings().getInt("welcome.title.fade_out")), main.configManager.getSettings().getInt("welcome.title.wait"));

		}

		if(main.configManager.getSettings().getBoolean("welcome.actionbar.enabled")) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, () -> {
				TextComponent text_component = new TextComponent(MessagesUtil.format(event.getPlayer(), main.configManager.getSettings().getString("welcome.actionbar.message")));
				event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, text_component);
			}, main.configManager.getSettings().getInt("welcome.actionbar.wait"));
		}

		if(main.configManager.getSettings().getBoolean("welcome.broadcast.first_join.enabled")) {
			if(!main.configManager.getUser(event.getPlayer().getUniqueId()).isSet("logoutlocation")) {
				Bukkit.broadcastMessage(MessagesUtil.format(event.getPlayer(), main.configManager.getSettings().getString("welcome.broadcast.first_join.message").replace("%player_name%", event.getPlayer().getName())));

			}
		}

		if(event.getPlayer().hasPermission("bluecore.fly.safelogin")) {
			event.getPlayer().setAllowFlight(true);
			event.getPlayer().setFlying(true);
		}
	}

}