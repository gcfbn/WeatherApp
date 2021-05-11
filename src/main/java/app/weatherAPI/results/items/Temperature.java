package app.weatherAPI.results.items;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Temperature {

    private double temp, temp_min, temp_max,
            feels_like, pressure, humidity;
}
