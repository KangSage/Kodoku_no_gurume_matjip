package com.kodoku.matjip.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ModernTimeUtil {
    public static String getZonedTime(Long timestamp, String pattern, String timeZone) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZonedDateTime korLocalTime = instant.atZone(ZoneId.of(timeZone));
        return korLocalTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
