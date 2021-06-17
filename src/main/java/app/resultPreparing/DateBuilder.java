package app.resultPreparing;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DateBuilder {

    public static String time(long unix) {
        LocalDateTime date = buildDateFromUnix(unix);
        return buildTime(date.getHour(), date.getMinute());
    }

    public static String time(Instant instant) {
        return DateBuilder.time(instant.getEpochSecond());
    }

    private static LocalDateTime buildDateFromUnix(long unix) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unix),
                TimeZone.getDefault().toZoneId()); // creates date from unix time (GMT)
    }

    private static String buildTime(int hours, int minutes) {
        String stringMinutes = (minutes < 10) ? "0" + minutes : Integer.toString(minutes);
        return String.format("%d:%s", hours, stringMinutes);
    }
}
