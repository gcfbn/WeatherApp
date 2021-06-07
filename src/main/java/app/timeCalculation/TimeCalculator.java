package app.timeCalculation;

import java.time.Clock;
import java.time.Instant;

public class TimeCalculator {

    public static long calculateMinutesFromNow(long timestamp) {
        return (Instant.now(Clock.systemUTC()).toEpochMilli() - timestamp * 1000) / 60000;
    }
}
