package app.weatherAPI.results.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class Clouds {

    private String all = "";

    @JsonCreator
    public Clouds(String all) {
        this.all = all;
    }
}
