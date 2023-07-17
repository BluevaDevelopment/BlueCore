/*
 * This code uses part of the code from the Config-Updater project, developed by tchristofferson.
 * You can find more information about the project at https://github.com/tchristofferson/Config-Updater/.
 *
 * Copyright (c) 2023, tchristofferson
 * This software is subject to the MIT License. You may obtain a copy of the license at
 * https://opensource.org/licenses/MIT.
 *
 */
package net.blueva.core.configuration.updater;

public class KeyUtils {

    public static boolean isSubKeyOf(final String parentKey, final String subKey, final char separator) {
        if (parentKey.isEmpty())
            return false;

        return subKey.startsWith(parentKey)
                && subKey.substring(parentKey.length()).startsWith(String.valueOf(separator));
    }

    public static String getIndents(final String key, final char separator) {
        final String[] splitKey = key.split("[" + separator + "]");
        final StringBuilder builder = new StringBuilder();

        for (int i = 1; i < splitKey.length; i++) {
            builder.append("  ");
        }
        return builder.toString();
    }
}