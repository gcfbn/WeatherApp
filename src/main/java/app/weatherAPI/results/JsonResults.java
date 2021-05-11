package app.weatherAPI.results;

import app.weatherAPI.results.items.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class JsonResults {

    private final Clouds clouds;

    private final Info info;

    private final Sun sun;

    private final Temperature temperature;

    private final Wind wind;

    @JsonCreator
    public JsonResults(@JsonProperty("clouds") final Clouds clouds, @JsonProperty("weather") final Info info,
                       @JsonProperty("sys") final Sun sun, @JsonProperty("main") final Temperature temperature,
                       @JsonProperty("wind") final Wind wind) {
        this.clouds = clouds;
        this.info = info;
        this.sun = sun;
        this.temperature = temperature;
        this.wind = wind;
    }
}