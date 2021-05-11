package app.weatherAPI.results.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class Info {

    private String icon = "", temp = "", description = "";

    @JsonCreator
    public Info(String icon, String temp, String description) {
        this.icon = icon;
        this.temp = temp;
        this.description = description;
    }
}
