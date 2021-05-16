package app.dto.raw_data;

import app.dto.raw_data.inner.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;
import java.util.List;

@JsonSerialize
@JsonDeserialize
public record RawWeatherData(
    int visibility,
    int timezone,
    @JsonProperty("main") MainData mainData,
    Clouds clouds,
    @JsonProperty("sys")  SysData sysData,
    Instant dt,
    Coord coord,
    List<Weather> weather,
    String name,
    int cod,
    int id,
    String base,
    Wind wind
) {}




