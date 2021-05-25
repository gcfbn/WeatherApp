package app.fileIO;

import app.dto.raw_data.RawWeatherData;

import java.util.Optional;

public record LastSearchData(Optional<RawWeatherData> data, Optional<String> city) {
}
