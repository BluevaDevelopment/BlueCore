package net.blueva.core.modules.scoreboard;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import fr.mrmicky.fastboard.FastBoard;
import net.blueva.core.Main;
import net.blueva.core.configuration.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreboardModule {

	private final Main main;

	public ScoreboardModule(Main main) {
		this.main = main;
	}

	private final Map<UUID, FastBoard> playerBoards = new HashMap<>();
	private final Map<UUID, ScoreboardData> playerScoreboardData = new ConcurrentHashMap<>();
	private final Map<Object, ScoreboardData> scoreboards = new HashMap<>();

	public void loadScoreboards() {
		if (ConfigManager.Modules.scoreboards.getBoolean("scoreboards.enabled")) {
			Section scoreboardSection = ConfigManager.Modules.scoreboards.getSection("scoreboards");

			for (Object scoreboardKey : scoreboardSection.getKeys()) {
				Section scoreboardData = scoreboardSection.getSection(scoreboardKey.toString());
				ScoreboardData data = new ScoreboardData(scoreboardData);
				scoreboards.put(scoreboardKey, data);
			}
		}
	}

	public void updatePlayerScoreboards() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			updatePlayerScoreboard(player);
		}
	}

	private void updatePlayerScoreboard(Player player) {
		UUID playerId = player.getUniqueId();

		// Verificar si ya tiene un scoreboard
		if (playerBoards.containsKey(playerId)) {
			FastBoard board = playerBoards.get(playerId);

			// Verificar si el scoreboard actual sigue siendo válido
			ScoreboardData currentData = getHighestPriorityValidScoreboard(player);
			ScoreboardData boardData = playerScoreboardData.get(playerId);

			if (currentData != null && !currentData.equals(boardData)) {
				// La configuración del scoreboard ha cambiado, actualizar el scoreboard
				updateScoreboard(player, currentData);
			} else if (currentData == null) {
				// No hay ningún scoreboard válido, quitar el scoreboard
				removePlayerScoreboard(player);
			}
		} else {
			// El jugador no tiene un scoreboard, agregar uno si es necesario
			ScoreboardData newData = getHighestPriorityValidScoreboard(player);
			if (newData != null) {
				addPlayerScoreboard(player, newData);
			}
		}
	}

	private void addPlayerScoreboard(Player player, ScoreboardData data) {
		FastBoard board = new FastBoard(player);
		playerBoards.put(player.getUniqueId(), board);
		playerScoreboardData.put(player.getUniqueId(), data);
		data.updateBoard(board);
	}

	private void removePlayerScoreboard(Player player) {
		UUID playerId = player.getUniqueId();
		playerBoards.remove(playerId);
		playerScoreboardData.remove(playerId);
	}

	private void updateScoreboard(Player player, ScoreboardData data) {
		FastBoard board = playerBoards.get(player.getUniqueId());

		// Actualizar datos y mostrar el scoreboard
		playerScoreboardData.put(player.getUniqueId(), data);
		data.updateBoard(board);
	}

	private ScoreboardData getHighestPriorityValidScoreboard(Player player) {
		String worldCondition = player.getWorld().getName();
		ScoreboardData highestPriorityValid = null;

		for (ScoreboardData data : scoreboards.values()) {
			if (data.shouldDisplay(worldCondition) && (highestPriorityValid == null || data.getPriority() > highestPriorityValid.getPriority())) {
				highestPriorityValid = data;
			}
		}

		return highestPriorityValid;
	}
}
