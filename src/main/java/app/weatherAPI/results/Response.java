package app.weatherAPI.results;

import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import lombok.Getter;

import java.util.Optional;

@Getter
public class Response {

    private Optional<JsonResults> jsonResults;
    private int status;
    private boolean isError;

    public Response(HttpResponse<JsonNode> httpResponse, int status) {

        try {
            jsonResults = Optional.of(JsonResultsMapper.mapResults(httpResponse));
            this.status = status;
            this.isError = false;
        } catch (JsonProcessingException e) {
            this.jsonResults = Optional.empty();
            this.status = OpenWeatherMapCaller.JACKSON_EXCEPTION;
            isError = true;
        }
    }

    private Response(int status, boolean isError) {
        this.jsonResults = Optional.empty();
        this.status = status;
        this.isError = isError;
    }

    public static Response fromStatus(int status, boolean isError) {
        return new Response(status, isError);
    }
}
