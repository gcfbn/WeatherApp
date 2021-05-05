package app.weatherAPI.results;

import app.weatherAPI.results.items.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import kong.unirest.JsonObjectMapper;
import kong.unirest.ObjectMapper;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

// TODO use external JSON Mapper (for example JacksonObjectMapper)
public class JsonResultsMapper {

    public static JsonResults mapResults(HttpResponse<JsonNode> httpResponse){

        // get JSONObject from HttpResponse
        JSONObject resultsObject = new JSONObject(httpResponse.getBody().toString());

        // create ObjectMapper
        ObjectMapper objectMapper = new JsonObjectMapper();

        // create all results object with default values
        Temperature temperature = new Temperature();
        Clouds clouds = new Clouds();
        Wind wind = new Wind();
        Sun sun = new Sun();
        Info info = new Info();

        // get data from JSONObject

        // temperature etc.
        if (resultsObject.has("main")) {
            JSONObject temperatureObject = resultsObject.getJSONObject("main");

            temperature = objectMapper.readValue(temperatureObject.toString(), Temperature.class);
        }

        // overcast
        if (resultsObject.has("clouds")){
            JSONObject cloudsObject = resultsObject.getJSONObject("clouds");

            clouds = objectMapper.readValue(cloudsObject.toString(), Clouds.class);
        }

        // wind
        if (resultsObject.has("wind")) {
            JSONObject windObject = resultsObject.getJSONObject("wind");

            wind = objectMapper.readValue(windObject.toString(), Wind.class);

//            if (windObject.has("deg")) {
//                int windAngle = windObject.getInt("deg");
//                String windDirection;
//
//                if ((windAngle >= 330 && windAngle < 360) || (windAngle >= 0 && windAngle < 30)) windDirection = "N";
//                else if (windAngle >= 30 && windAngle < 60) windDirection = "NE";
//                else if (windAngle >= 60 && windAngle < 120) windDirection = "E";
//                else if (windAngle >= 120 && windAngle < 150) windDirection = "SE";
//                else if (windAngle >= 150 && windAngle < 210) windDirection = "S";
//                else if (windAngle >= 210 && windAngle < 240) windDirection = "SW";
//                else if (windAngle >= 240 && windAngle < 300) windDirection = "W";
//                else windDirection = "NW";
//
//                jsonResults.setDeg(windDirection);
//            }
//            if (windObject.has("speed")) jsonResults.setSpeed(Double.toString(windObject.getDouble("speed")));
        }

        // sun
        if (resultsObject.has("sys")) {
            JSONObject sunObject = resultsObject.getJSONObject("sys");

            sun = objectMapper.readValue(sunObject.toString(), Sun.class);
        }

        // icon & description
        // "weather" key in returned JSON file is an array
        // first element of this array will be displayed
        if (resultsObject.has("weather")) {
            JSONArray iconArray = (JSONArray) resultsObject.get("weather");
            JSONObject iconObject = (JSONObject) iconArray.get(0);

            info = objectMapper.readValue(iconObject.toString(), Info.class);
        }

        return new JsonResults(clouds, info, sun, temperature, wind);
    }
}
