package app.weatherAPI.results.items;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Temperature {

    private double temp;
    private double temp_min, temp_max,
            feels_like, pressure, humidity;

}