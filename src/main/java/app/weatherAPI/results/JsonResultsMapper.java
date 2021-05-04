package app.weatherAPI.results;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

// TODO use external JSON Mapper (for example JacksonObjectMapper)
public class JsonResultsMapper {

    public static JsonResults mapResults(HttpResponse<JsonNode> httpResponse){

        // get JSONObject from HttpResponse
        JSONObject resultsObject = new JSONObject(httpResponse.getBody().toString());

        JsonResults jsonResults = new JsonResults();

        // get data from JSONObject

        // temperature etc.
        if (resultsObject.has("main")) {
            JSONObject temperatureObject = resultsObject.getJSONObject("main");

            if (temperatureObject.has("temp"))
                jsonResults.setCurrentTemperature(Double.toString(temperatureObject.getDouble("temp")));
            if (temperatureObject.has("temp_min"))
                jsonResults.setMinimalTemperature(Double.toString(temperatureObject.getDouble("temp_min")));
            if (temperatureObject.has("temp_max"))
                jsonResults.setMaximalTemperature(Double.toString(temperatureObject.getDouble("temp_max")));
            if (temperatureObject.has("humidity"))
                jsonResults.setHumidity(Integer.toString(temperatureObject.getInt("humidity")));
            if (temperatureObject.has("pressure"))
                jsonResults.setPressure(Double.toString(temperatureObject.getDouble("pressure")));
            if (temperatureObject.has("feels_like"))
                jsonResults.setFeelsLike(Double.toString(temperatureObject.getDouble("feels_like")));
        }

        // overcast
        if (resultsObject.has("clouds")){
            JSONObject cloudsObject = resultsObject.getJSONObject("clouds");
            if (cloudsObject.has("all"))
                jsonResults.setOvercast(Integer.toString(cloudsObject.getInt("all")));
        }

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

                jsonResults.setWindDirection(windDirection);
            }
            if (windObject.has("speed")) jsonResults.setWindSpeed(Double.toString(windObject.getDouble("speed")));
        }

        // sun
        if (resultsObject.has("sys")) {
            JSONObject sunObject = resultsObject.getJSONObject("sys");

            if (sunObject.has("sunrise")) jsonResults.setSunrise(sunObject.getLong("sunrise"));
            if (sunObject.has("sunset")) jsonResults.setSunset(sunObject.getLong("sunset"));
        }

        // icon & description
        // "weather" key in returned JSON file is an array
        // first element of this array will be displayed
        if (resultsObject.has("weather")) {
            JSONArray iconArray = (JSONArray) resultsObject.get("weather");
            JSONObject iconObject = (JSONObject) iconArray.get(0);
            if (iconObject.has("icon")) jsonResults.setIcon(iconObject.getString("icon"));
            if (iconObject.has("description")) jsonResults.setDescription(iconObject.getString("description"));
        }
        return jsonResults;
    }
}
