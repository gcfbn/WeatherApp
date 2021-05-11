package app.weatherAPI.results.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class Sun {

    private long sunrise = 0, sunset = 0;

    @JsonCreator
    public Sun(long sunrise, long sunset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
}
