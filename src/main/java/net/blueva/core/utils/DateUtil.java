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
 * GitHub repository: https://github.com/BluevaDevelopment/BlueMenu
 *
 * Copyright (c) 2023 Blueva Development. All rights reserved.
 */

package net.blueva.core.utils;

import net.blueva.core.Main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static boolean isConfigDatePassed(LocalDateTime futureDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        return futureDate.isBefore(currentDate);
    }

    public static long getTimeUntilFutureDate(LocalDateTime futureDate, ChronoUnit unit) {
        LocalDateTime currentDate = LocalDateTime.now();
        Duration duration = Duration.between(currentDate, futureDate);
        return unit.between(currentDate, futureDate);
    }

    public static String getTimeUntilFutureDateAsString(LocalDateTime futureDate, String kit) {
        LocalDateTime currentDate = LocalDateTime.now();
        Duration duration = Duration.between(currentDate, futureDate);

        long years = duration.toDays() / 365;
        long months = duration.toDays() % 365 / 30;
        long days = duration.toDays() % 365 % 30;
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        String text = Main.getPlugin().configManager.getLang().getString("messages.error.kit_delay").replace("%kit_name%", kit)
                .replace("%kit_delay_year%", Long.toString(years))
                .replace("%kit_delay_month%", Long.toString(months))
                .replace("%kit_delay_day%", Long.toString(days))
                .replace("%kit_delay_hour%", Long.toString(hours))
                .replace("%kit_delay_minute%", Long.toString(minutes))
                .replace("%kit_delay_second%", Long.toString(seconds));

        return text;
    }



}
