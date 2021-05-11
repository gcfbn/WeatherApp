package app.weatherAPI.results;

import app.weatherAPI.results.items.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class JsonResultsMapper {

    // TODO maybe use some builder with annotations (check how could it be done in Java)
    public static JsonResults mapResults(HttpResponse<JsonNode> httpResponse) {

        // get JSONObject from HttpResponse
        JSONObject resultsObject = new JSONObject(httpResponse.getBody().toString());

        // create ObjectMapper
        ObjectMapper objectMapper =
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // get data from JSONObject

        try {
            return objectMapper.readValue(resultsObject.toString(), JsonResults.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

//        // temperature etc.
//        Temperature temperature = new Temperature();
//        if (resultsObject.has("main")) {
//            JSONObject temperatureObject = resultsObject.getJSONObject("main");
//
//            temperature = objectMapper.readValue(temperatureObject.toString(), Temperature.class);
//        }
//
//        // overcast
//        Clouds clouds = new Clouds();
//        if (resultsObject.has("clouds")) {
//            JSONObject cloudsObject = resultsObject.getJSONObject("clouds");
//
//            clouds = objectMapper.readValue(cloudsObject.toString(), Clouds.class);
//        }
//
//        // wind
//        Wind wind = new Wind();
//        if (resultsObject.has("wind")) {
//            JSONObject windObject = resultsObject.getJSONObject("wind");
//
//            wind = objectMapper.readValue(windObject.toString(), Wind.class);
//        }
//
//        // sun
//        Sun sun = new Sun();
//        if (resultsObject.has("sys")) {
//            JSONObject sunObject = resultsObject.getJSONObject("sys");
//
//            sun = objectMapper.readValue(sunObject.toString(), Sun.class);
//        }
//
//        // icon & description
//        // "weather" key in returned JSON file is an array
//        // first element of this array will be displayed
//        Info info = new Info();
//        if (resultsObject.has("weather")) {
//            JSONArray iconArray = (JSONArray) resultsObject.get("weather");
//            JSONObject iconObject = (JSONObject) iconArray.get(0);
//
//            info = objectMapper.readValue(iconObject.toString(), Info.class);
//        }
//
//        return new JsonResults(clouds, info, sun, temperature, wind);
    }
}
