package app.weatherAPI.results;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import kong.unirest.json.JSONObject;

public class JsonResultsMapper {

    public static JsonResults mapResults(HttpResponse<JsonNode> httpResponse) throws JsonProcessingException {

        // get JSONObject from HttpResponse
        JSONObject resultsObject = new JSONObject(httpResponse.getBody().toString());

        // create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // do not throw an exception if json contains fields that do not exist in class
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // get data from JSONObject
        return objectMapper.readValue(resultsObject.toString(), JsonResults.class);
    }
}
