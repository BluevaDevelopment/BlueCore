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
