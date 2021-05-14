package app.weatherAPI.results;

import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.Getter;

@Getter
public class Response {

    private JsonResults jsonResults;
    private int status;
    private boolean isSuccessful;

    public Response(HttpResponse<JsonNode> httpResponse, int status) {

        try {
            jsonResults = JsonResultsMapper.mapResults(httpResponse);
            this.status = status;
            isSuccessful = (status == 200);
        } catch (JsonProcessingException e) {
            this.status = OpenWeatherMapCaller.JACKSON_EXCEPTION;
            isSuccessful = false;
        }
    }
}
