package app.weatherAPI.results;

// TODO use Lombok here and maybe in other classes too

import app.weatherAPI.results.items.*;

public class JsonResults {

    private final Clouds clouds;
    private final Info info;
    private final Sun sun;
    private final Temperature temperature;
    private final Wind wind;

    public JsonResults(Clouds clouds, Info info, Sun sun, Temperature temperature, Wind wind) {
        this.clouds = clouds;
        this.info = info;
        this.sun = sun;
        this.temperature = temperature;
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Info getInfo() {
        return info;
    }

    public Sun getSun() {
        return sun;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Wind getWind() {
        return wind;
    }
}