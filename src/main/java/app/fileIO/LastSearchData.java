package app.fileIO;

import app.dto.raw_data.RawWeatherData;

public record LastSearchData(RawWeatherData data, String city) {
}
