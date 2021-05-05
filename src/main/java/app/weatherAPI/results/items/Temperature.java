package app.weatherAPI.results.items;

public class Temperature {

    private String temp_min = "", temp_max = "",
            feels_like = "", pressure = "", humidity = "";

    public String getTemp_min() {
        return temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public String getFeels_like() {
        return feels_like;
    }

    public String getPressure() {
        return pressure;
    }

    public String getHumidity() {
        return humidity;
    }
}
