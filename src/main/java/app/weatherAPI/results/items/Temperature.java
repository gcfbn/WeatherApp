package app.weatherAPI.results.items;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Temperature {

    private double temp;
    private double temp_min, temp_max,
            feels_like, pressure, humidity;

}