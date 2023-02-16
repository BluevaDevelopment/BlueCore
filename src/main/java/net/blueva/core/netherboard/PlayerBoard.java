/*
 * This code uses part of the code from the Netherbord project, developed by MinusKube.
 * You can find more information about the project at https://github.com/MinusKube/Netherbord.
 *
 * Copyright (c) 2023, MinusKube
 * This software is subject to the Apache License, Version 2.0. You may obtain a copy of the license at
 * https://www.apache.org/licenses/LICENSE-2.0.html.
 *
 */
package net.blueva.core.netherboard;

import java.util.Map;

public interface PlayerBoard<V, N, S> {

    /**
     * Gets the name of the line from its score.
     *
     * @param score the score of the line
     * @return      the name of the line, or null if the line doesn't exist.
     */
    V get(N score);

    /**
     * Sets a line with its name and its score.
     * This will update the line if it already exists, and create it if it doesn't.
     *
     * @param name  the name of the line
     * @param score the score of the line
     */
    void set(V name, N score);

    /**
     * Sets all the lines of the scoreboard.
     * This will clear all of the current board lines, then set
     * all of the given lines, from top to down, by giving them each a score
     * determined by {@code lines.length - index}.
     *
     * @param lines the new board lines
     */
    @SuppressWarnings("unchecked")
    void setAll(V... lines);

    /**
     * Clears all the lines of the scoreboard.
     */
    void clear();

    /**
     * Removes a line from its score.
     *
     * @param score the score of the line to remove
     */
    void remove(N score);

    /**
     * Totally deletes the board, after this, you can't use this instance again,
     * you'll need to create another PlayerBoard if you want to create the scoreboard again.
     */
    void delete();

    /**
     * Gets the name of the board.
     *
     * @return the name of the board
     */
    S getName();

    /**
     * Sets the name of the board.
     *
     * @param name the new name of the board
     */
    void setName(S name);

    /**
     * Gets the current lines of the board.
     *
     * @return the lines of the board
     */
    Map<N, V> getLines();

}
