package net.blueva.core.events;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;

import net.blueva.core.Main;
import net.blueva.core.utils.MessageUtil;

public class OnPlayerJoin implements Listener {

	private final Main main;

	public OnPlayerJoin(Main main) {
		this.main = main;
	}

	@EventHandler
	public void OPJ(PlayerJoinEvent event) {
		main.configManager.registerUser(event.getPlayer().getUniqueId());

		if(main.getConfig().getBoolean("welcome.message.enabled")) {
			List<String> description = main.getConfig().getStringList("welcome.message.list");
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
				public void run() {
					for (String message : description) {
						event.getPlayer().sendMessage(MessageUtil.getColorMessage(message, event.getPlayer()));
					}
				}
			}, main.getConfig().getInt("welcome.message.wait"));
		}

		if(main.getConfig().getBoolean("welcome.title.enabled")) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
				public void run() {
					event.getPlayer().sendTitle(MessageUtil.getColorMessage(main.getConfig().getString("welcome.title.title"), event.getPlayer()), MessageUtil.getColorMessage(main.getConfig().getString("welcome.title.subtitle"), event.getPlayer()), main.getConfig().getInt("welcome.title.fade-in"), main.getConfig().getInt("welcome.title.stay"), main.getConfig().getInt("welcome.title.fade-out"));
				}
			}, main.getConfig().getInt("welcome.title.wait"));

		}

		if(main.getConfig().getBoolean("welcome.actionbar.enabled")) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
				public void run() {
					TextComponent text_component = new TextComponent(MessageUtil.getColorMessage(main.getConfig().getString("welcome.actionbar.message"), event.getPlayer()));
					event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, text_component);
				}
			}, main.getConfig().getInt("welcome.actionbar.wait"));
		}

		if(main.getConfig().getBoolean("welcome.broadcast.join.enabled")) {
			event.setJoinMessage(MessageUtil.getColorMessage(main.getConfig().getString("welcome.broadcast.join.message"), event.getPlayer()).replace("%player_name%", event.getPlayer().getDisplayName()));
		} else {
			event.setJoinMessage("");
		}

		if(main.getConfig().getBoolean("welcome.broadcast.first_join.enabled")) {
			if(!main.configManager.getUser(event.getPlayer().getUniqueId()).isSet("logoutlocation")) {
				Bukkit.broadcastMessage(MessageUtil.getColorMessage(main.getConfig().getString("welcome.broadcast.first_join.message"), event.getPlayer()));

			}
		}

		if(event.getPlayer().hasPermission("bluecore.fly.safelogin")) {
			event.getPlayer().setAllowFlight(true);
			event.getPlayer().setFlying(true);
		}
	}

}