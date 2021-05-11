package app.weatherAPI.results;

import app.weatherAPI.results.items.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class JsonResults {

    @JsonProperty("clouds")
    private Clouds clouds;

    @JsonProperty("weather")
    private Info weather;

    @JsonProperty("sys")
    private Sun sys;

    @JsonProperty("main")
    private Temperature temperature;

    @JsonProperty("wind")
    private Wind wind;

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Sun getSys() {
        return sys;
    }

    public void setSys(Sun sys) {
        this.sys = sys;
    }

    @JsonProperty("main")
    public Temperature getTemperature() {
        return temperature;
    }

    @JsonProperty("main")
    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Info getWeather() {
        return weather;
    }

    public void setWeather(Info weather) {
        this.weather = weather;
    }
}