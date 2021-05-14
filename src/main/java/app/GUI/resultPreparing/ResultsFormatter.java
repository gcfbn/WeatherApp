package app.GUI.resultPreparing;

import app.query.Units;
import app.weatherAPI.results.JsonResults;
import app.weatherAPI.results.items.*;

public class ResultsFormatter {

    private final Clouds clouds;
    private final Temperature temperature;
    private final Info info;
    private final Sun sun;
    private final Wind wind;

    private final String temperatureUnit;
    private final String speedUnit;

    public ResultsFormatter(Units units, JsonResults results) {
        this.clouds = results.getClouds();
        this.temperature = results.getTemperature();
        this.info = results.getInfo();
        this.sun = results.getSun();
        this.wind = results.getWind();

        this.temperatureUnit = calculateTemperatureUnit(units);
        this.speedUnit = calculateSpeedUnit(units);
    }

    private static String calculateTemperatureUnit(Units units){
        return (units == Units.METRIC) ? "°C" : "°F";
    }

    private static String calculateSpeedUnit(Units units){
        return (units == Units.METRIC) ? "m/s" : "mph";
    }

    public String currentTemperature(){
        return temperature.getTemp() + " " + temperatureUnit;
    }

    public String minimumTemperature(){
        return temperature.getTemp_max() + " " + temperatureUnit;
    }

    public String maximumTemperature(){
        return temperature.getTemp_min() + " " + temperatureUnit;
    }

    public String feelsLike(){
        return temperature.getFeels_like() + " " + temperatureUnit;
    }

    public String humidity(){
        return temperature.getHumidity() + " %";
    }

    public String pressure(){
        return temperature.getPressure() + " hPa";
    }

    public String description(){
        return info.getDescription();
    }

    public String icon(){
        return info.getIcon();
    }

    public String windSpeed(){
        return wind.getSpeed() + " " + speedUnit;
    }

    public String windDirection(){
        return WindDirectionBuilder.buildDirection(Integer.parseInt(wind.getDeg()));
    }

    public String sunrise(){
        return (sun.getSunrise() == 0) ? " " : DateBuilder.time(sun.getSunrise());
    }

    public String sunset(){
        return (sun.getSunset() == 0) ? " " : DateBuilder.time(sun.getSunset());
    }

    public String overcast(){
        return clouds.getAll() + " %";
    }
}
