package app.dto.raw_data.inner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonDeserialize
public record MainData(
    double temp,
    @JsonProperty("temp_min") double tempMin,
    @JsonProperty("temo_max") double tempMax,
    @JsonProperty("feels_like") double tempFeelsLike,
    int humidity,
    int pressure
) {
}
