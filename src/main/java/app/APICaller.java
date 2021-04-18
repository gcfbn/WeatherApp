package app;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class APICaller {

    public Results getResults(Query query) throws UnirestException {

        String URL = getURL(query); // create URL from query
        HttpResponse<JsonNode> response = Unirest.get(URL).asJson(); // get results as JSON

        JSONObject resultsObject = new JSONObject(response.getBody().toString()); // get JSONObject from response

        Results results = new Results();

        // get data from JSONObject

        // temperature etc.
        if (resultsObject.has("main")) {
            JSONObject temperatureObject = resultsObject.getJSONObject("main");

            if (temperatureObject.has("temp")) results.setCurrentTemperature(Double.toString(temperatureObject.getDouble("temp")));
            if (temperatureObject.has("temp_min")) results.setMinimalTemperature(Double.toString(temperatureObject.getDouble("temp_min")));
            if (temperatureObject.has("temp_max")) results.setMaximalTemperature(Double.toString(temperatureObject.getDouble("temp_max")));
            if (temperatureObject.has("humidity")) results.setHumidity(Double.toString(temperatureObject.getDouble("humidity")));
            if (temperatureObject.has("pressure")) results.setPressure(Double.toString(temperatureObject.getDouble("pressure")));
            if (temperatureObject.has("feels_like")) results.setFeelsLike(Double.toString(temperatureObject.getDouble("feels_like")));
        }

        // overcast
        if (resultsObject.has("clouds")) results.setOvercast(Double.toString(resultsObject.getJSONObject("clouds").getInt("all")));

        // wind
        if (resultsObject.has("wind")) {
            JSONObject windObject = resultsObject.getJSONObject("wind");

            if (windObject.has("deg")) {
                int windAngle = windObject.getInt("deg");
                String windDirection;

                if ((windAngle >= 330 && windAngle < 360) || (windAngle >= 0 && windAngle < 30)) windDirection = "N";
                else if (windAngle >= 30 && windAngle < 60) windDirection = "NE";
                else if (windAngle >= 60 && windAngle < 120) windDirection = "E";
                else if (windAngle >= 120 && windAngle < 150) windDirection = "SE";
                else if (windAngle >= 150 && windAngle < 210) windDirection = "S";
                else if (windAngle >= 210 && windAngle < 240) windDirection = "SW";
                else if (windAngle >= 240 && windAngle < 300) windDirection = "W";
                else windDirection = "NW";

                results.setWindDirection(windDirection);
            }
            if (windObject.has("speed")) results.setWindSpeed(Double.toString(windObject.getDouble("speed")));
        }

        // sun
        if (resultsObject.has("sys")) {
            JSONObject sunObject = resultsObject.getJSONObject("sys");

            if (sunObject.has("sunrise")) results.setSunrise(sunObject.getString("sunrise"));
            if (sunObject.has("sunset")) results.setSunset(sunObject.getString("sunset"));
        }

        // icon & description
        // "weather" key in returned JSON file is an array
        // first element of this array will be displayed
        if (resultsObject.has("weather")) {
            JSONArray iconArray = (JSONArray) resultsObject.get("weather");
            JSONObject iconObject = (JSONObject) iconArray.get(0);
            if (iconObject.has("icon")) results.setIcon(iconObject.getString("icon"));
            if (iconObject.has("description")) results.setDescription(iconObject.getString("description"));
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
        URL = URL + "&appid=a52958f9ad25d7d64c67d97957bc6119";  // API key
        URL = URL + "&units=" + query.getUnits();
        URL = URL + "&lang=" + ((query.getLanguage() == Language.ENGLISH) ? "en" : "pl");
        return URL;
    }
}