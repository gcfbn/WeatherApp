package app;

public class Results {

    private String icon;
    private double currentTemperature;
    private String description;
    private double minimalTemperature, maximalTemperature, feelsLike;
    private int pressure, humidity, windDirection;
    private double windSpeed;
    private String sunrise, sunset;
    private int overcast;

    @Override
    public String toString() {
        return icon + " " + currentTemperature + " " + description + " " + minimalTemperature + " " + maximalTemperature + " " + feelsLike + " " +
                pressure + " " + humidity + " " + windDirection + " " + windSpeed + " " + sunrise + " " + sunset + " " + overcast;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMinimalTemperature() {
        return minimalTemperature;
    }

    public void setMinimalTemperature(double minimalTemperature) {
        this.minimalTemperature = minimalTemperature;
    }

    public double getMaximalTemperature() {
        return maximalTemperature;
    }

    public void setMaximalTemperature(double maximalTemperature) {
        this.maximalTemperature = maximalTemperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public int getOvercast() {
        return overcast;
    }

    public void setOvercast(int overcast) {
        this.overcast = overcast;
    }
}