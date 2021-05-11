package app.weatherAPI.results.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;

@Getter
public class Temperature {

    private final double temp, temp_min, temp_max,
            feels_like, pressure, humidity;

    @JsonCreator
    public Temperature(final double temp, final double temp_min, final double temp_max, final double feels_like, final double pressure, final double humidity) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.feels_like = feels_like;
        this.pressure = pressure;
        this.humidity = humidity;
    }
}
