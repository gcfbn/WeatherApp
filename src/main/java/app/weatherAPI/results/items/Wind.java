package app.weatherAPI.results.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class Wind {

    private String deg = "", speed = "";

    @JsonCreator

    public Wind(String deg, String speed) {
        this.deg = deg;
        this.speed = speed;
    }
}
