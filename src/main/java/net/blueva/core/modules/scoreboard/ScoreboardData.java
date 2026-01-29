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

package net.blueva.core.modules.scoreboard;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import fr.mrmicky.fastboard.adventure.FastBoard;
import net.blueva.core.utils.MessagesUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
        this.title = config.getString("title");
        this.lines = config.getStringList("lines");
        this.displayCondition = config.getString("display_condition", "true");
        this.priority = config.getInt("priority", 0);
    }

    public boolean shouldDisplay(String worldCondition) {
        String condition = displayCondition.replace("{player_world}", worldCondition);

        try {
            return (boolean) MVEL.eval(condition);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("[BlueCore] Error evaluating condition: " + condition);
            Bukkit.getConsoleSender().sendMessage("[BlueCore] " + e.fillInStackTrace());
            return false;
        }
    }

    public void updateBoard(FastBoard board, Player player) {
        Component titleAdventure = MessagesUtils.format(player, title);
        List<Component> linesAdventure = lines.stream()
                        .map(line -> MessagesUtils.format(player, line))
                        .toList();

        board.updateTitle(titleAdventure);
        board.updateLines(linesAdventure.toArray(new Component[0]));
    }

    public int getPriority() {
        return priority;
    }
}
