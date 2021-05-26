package app.resultPreparing;

import app.dto.raw_data.RawWeatherData;
import app.query.Units;

import java.time.Instant;

public class ResultsFormatter {

//    private final Clouds clouds;
//    private final Temperature temperature;
//    private final Info info;
//    private final Sun sun;
//    private final Wind wind;

    private final RawWeatherData results;

    private final String temperatureUnit;
    private final String speedUnit;

    public ResultsFormatter(Units units, RawWeatherData results) {
        this.results = results;

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
        return results.mainData().temp() + " " + temperatureUnit;
    }

    public String minimumTemperature(){
        return results.mainData().tempMin() + " " + temperatureUnit;
    }

    public String maximumTemperature(){
        return results.mainData().tempMax() + " " + temperatureUnit;
    }

    public String feelsLike(){
        return results.mainData().tempFeelsLike() + " " + temperatureUnit;
    }

    public String humidity(){
        return results.mainData().humidity() + " %";
    }

    public String pressure(){
        return results.mainData().tempFeelsLike() + " hPa";
    }

    public String description(){
        return results.weather().get(0).description();
    }

    public String icon(){
        return results.weather().get(0).icon();
    }

    public String windSpeed(){
        return Double.toString(results.wind().speed());
    }

    public String windDirection(){
        return WindDirectionBuilder.buildDirection(results.wind().deg());
    }

    public String sunrise(){
        return (results.sysData().sunrise().equals(Instant.ofEpochSecond(0L))) ?
                " " : DateBuilder.time(results.sysData().sunrise());
    }

    public String sunset(){
        return (results.sysData().sunset().equals(Instant.ofEpochSecond(0L))) ?
                " " : DateBuilder.time(results.sysData().sunset());
    }

    public String overcast(){
        return results.clouds().all() + " %";
    }
}
