package app.weatherAPI.results;

import app.weatherAPI.results.items.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import kong.unirest.JsonObjectMapper;
import kong.unirest.ObjectMapper;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class JsonResultsMapper {

    public static JsonResults mapResults(HttpResponse<JsonNode> httpResponse) {

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
        if (resultsObject.has("clouds")) {
            JSONObject cloudsObject = resultsObject.getJSONObject("clouds");

            clouds = objectMapper.readValue(cloudsObject.toString(), Clouds.class);
        }

        // wind
        if (resultsObject.has("wind")) {
            JSONObject windObject = resultsObject.getJSONObject("wind");

            wind = objectMapper.readValue(windObject.toString(), Wind.class);
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
