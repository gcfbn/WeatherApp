package app.weatherAPI.results;

// TODO use Lombok here and maybe in other classes too

public class JsonResults {

    // FIELDS
    private String icon = "", currentTemperature = "", description = "", minimalTemperature = "", maximalTemperature = "",
            feelsLike = "", pressure = "", humidity = "", windDirection = "", windSpeed = "", overcast = "";
    private long sunrise, sunset;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(String currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinimalTemperature() {
        return minimalTemperature;
    }

    public void setMinimalTemperature(String minimalTemperature) {
        this.minimalTemperature = minimalTemperature;
    }

    public String getMaximalTemperature() {
        return maximalTemperature;
    }

    public void setMaximalTemperature(String maximalTemperature) {
        this.maximalTemperature = maximalTemperature;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public String getOvercast() {
        return overcast;
    }

    public void setOvercast(String overcast) {
        this.overcast = overcast;
    }
}