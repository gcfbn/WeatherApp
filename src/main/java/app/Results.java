package app;

public class Results {

    public String icon;
    public double currentTemperature;
    public String description;
    public double minimalTemperature, maximalTemperature, feelsLike;
    public int pressure, humidity, windDirection;
    public double windSpeed;
    public String sunrise, sunset;
    public int overcast;

    @Override
    public String toString() {
        return icon + " " + currentTemperature + " " + description + " " + minimalTemperature + " " + maximalTemperature + " " + feelsLike + " " +
                pressure + " " + humidity + " " + windDirection + " " + windSpeed + " " + sunrise + " " + sunset + " " + overcast;
    }
}