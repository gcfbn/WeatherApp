package app.weatherAPI.results;

import app.weatherAPI.results.items.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResults {

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("weather")
    private Info info;

    @JsonProperty("sys")
    private Sun sun;

    @JsonProperty("main")
    private Temperature temperature;

    @JsonProperty("wind")
    private Wind wind;
}