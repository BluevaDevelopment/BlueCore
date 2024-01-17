package net.blueva.core.modules.scoreboard;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.mvel2.MVEL;

import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardData {
    private final int ticks;
    private final String title;
    private final List<String> lines;
    private final String displayCondition;
    private final int priority;

    public ScoreboardData(Section config) {
        this.ticks = config.getInt("ticks", 20);
        this.title = ChatColor.translateAlternateColorCodes('&', config.getString("title"));
        this.lines = config.getStringList("lines").stream()
                .map(line -> ChatColor.translateAlternateColorCodes('&', line))
                .collect(Collectors.toList());
        this.displayCondition = config.getString("display_condition", "*");
        this.priority = config.getInt("priority", 0);
    }

    public boolean shouldDisplay(String worldCondition) {
        String condition = displayCondition.replace("{player_world}", worldCondition);

        try {
            return (boolean) MVEL.eval(condition);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("Error evaluating condition: " + condition);
            e.printStackTrace();
            return false;
        }
    }

    public void updateBoard(FastBoard board) {
        board.updateTitle(title);
        board.updateLines(lines.toArray(new String[0]));
    }

    public int getPriority() {
        return priority;
    }
}
