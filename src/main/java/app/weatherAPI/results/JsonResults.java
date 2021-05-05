package app.weatherAPI.results;

// TODO use Lombok here and maybe in other classes too

import app.weatherAPI.results.items.*;

public class JsonResults {

    private Clouds clouds;
    private Info info;
    private Sun sun;
    private Temperature temperature;
    private Wind wind;

    public JsonResults(Clouds clouds, Info info, Sun sun, Temperature temperature, Wind wind) {
        this.clouds = clouds;
        this.info = info;
        this.sun = sun;
        this.temperature = temperature;
        this.wind = wind;
    }
}