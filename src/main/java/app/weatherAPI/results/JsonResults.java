package app.weatherAPI.results;

// TODO use Lombok here and maybe in other classes too

import app.weatherAPI.results.items.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JsonResults {

    private final Clouds clouds;
    private final Info info;
    private final Sun sun;
    private final Temperature temperature;
    private final Wind wind;
}