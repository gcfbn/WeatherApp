package app.timeCalculation;

import app.dto.raw_data.RawWeatherData;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class TimeCalculator {

    public static long calculateMinutesFromNow(RawWeatherData rawWeatherData) {

        Instant searchTimeInstant = rawWeatherData.dt();
        ZoneOffset searchTimeOffset = ZoneOffset.ofTotalSeconds(rawWeatherData.timezone());

        LocalDateTime searchDateTime = LocalDateTime.ofInstant(searchTimeInstant, searchTimeOffset);

        return ChronoUnit.MINUTES.between(searchDateTime, LocalDateTime.now(searchTimeOffset));
    }

}
