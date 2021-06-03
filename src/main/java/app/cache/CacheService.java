package app.cache;

import app.dto.raw_data.RawWeatherData;
import app.fileIO.LastSearchFiles;
import app.objectBox.responseCache.ResponseCacheIO;
import app.objectBox.responseCache.ResponseRecord;
import app.query.Query;
import app.timeCalculation.TimeCalculator;

import java.util.List;
import java.util.Optional;

public class CacheService {

    private final static int MAX_FRESH_DATA_DELAY = 60;

    public static Optional<RawWeatherData> readFreshData(Query query, ResponseCacheIO cacheObjectBox) {

        List<ResponseRecord> recordList = cacheObjectBox.read(query);

        if (recordList.isEmpty()) return Optional.empty();

        ResponseRecord freshestRecord = recordList.get(0);

        for (ResponseRecord record : recordList) {
            if (record.timestamp < freshestRecord.timestamp)
                freshestRecord = record;
        }

        long delay = TimeCalculator.calculateMinutesFromNow(freshestRecord.timestamp);

        if (delay > MAX_FRESH_DATA_DELAY) return Optional.empty();
        return LastSearchFiles.read(freshestRecord.responseFilePath);
    }
}