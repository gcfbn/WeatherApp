package app;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class APICaller {

    public Results call(Query query) throws UnirestException {

        String URL = getURL(query);
        HttpResponse<JsonNode> response = Unirest.get(URL).asJson();

        JSONObject resultsObject = new JSONObject(response.getBody().toString());

        Results results = new Results();

        // get data from JSONObject

        // temperature etc.
        if (resultsObject.has("main")) {
            JSONObject temperatureObject = resultsObject.getJSONObject("main");
            if (temperatureObject.has("temp")) results.currentTemperature = temperatureObject.getDouble("temp");
            else results.currentTemperature = -273.15;
            if (temperatureObject.has("temp_min")) results.minimalTemperature = temperatureObject.getDouble("temp_min");
            else results.minimalTemperature = -273.15;
            if (temperatureObject.has("temp_max")) results.maximalTemperature = temperatureObject.getDouble("temp_max");
            else results.maximalTemperature = -273.15;
            if (temperatureObject.has("humidity")) results.humidity = temperatureObject.getInt("humidity");
            else results.humidity = -1;
            if (temperatureObject.has("pressure")) results.pressure = temperatureObject.getInt("pressure");
            else results.pressure = -1;
            if (temperatureObject.has("feels_like")) results.feelsLike = temperatureObject.getDouble("feels_like");
            else results.feelsLike = -273.15;
        }

        // overcast
        if (resultsObject.has("clouds")) results.overcast = resultsObject.getJSONObject("clouds").getInt("all");
        else results.overcast = -1;

        // wind
        if (resultsObject.has("wind")) {
            JSONObject windObject = resultsObject.getJSONObject("wind");
            if (windObject.has("deg")) results.windDirection = windObject.getInt("deg");
            else results.windDirection = -1;
            if (windObject.has("speed")) results.windSpeed = windObject.getDouble("speed");
            else results.windSpeed = -1.0;
        }

        // sun
        if (resultsObject.has("sys")) {
            JSONObject sunObject = resultsObject.getJSONObject("sys");
            if (sunObject.has("sunrise")) results.sunrise = sunObject.getString("sunrise");
            else results.sunrise = "error";
            if (sunObject.has("sunset")) results.sunset = sunObject.getString("sunset");
            else results.sunset = "error";
        }

        // icon & description
        // "weather" key in returned JSON file is an array
        // weather with index 0 is the prime weather and it will be shown
        if (resultsObject.has("weather")) {
            JSONArray iconArray = (JSONArray) resultsObject.get("weather");
            JSONObject iconObject = (JSONObject) iconArray.get(0);
            if (iconObject.has("icon")) results.icon = iconObject.getString("icon");
            else results.icon = "error";
            if (iconObject.has("description")) results.description = iconObject.getString("description");
            else results.description = "error";
        }
        return results;
    }

    public int getStatus(Query query) throws UnirestException {
        String URL = getURL(query);
        HttpResponse<JsonNode> response = Unirest.get(URL).asJson();
        return response.getStatus();
    }

    private String getURL(Query query) {
        String URL = "http://api.openweathermap.org/data/2.5/weather";
        URL = URL + "?q=" + query.getCity();
        URL = URL + "&appid=a52958f9ad25d7d64c67d97957bc6119";
        URL = URL + "&units=" + query.getUnits();
        URL = URL + "&lang=" + ((query.getLanguage() == Language.ENGLISH) ? "en" : "pl");
        return URL;
    }
}