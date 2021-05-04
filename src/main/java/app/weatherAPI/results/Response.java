package app.weatherAPI.results;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class Response {

    private final Results results;
    private final int status;

    public Response(HttpResponse<JsonNode> httpResponse, int status){
        results = ResultsMapper.mapResults(httpResponse);
        this.status = status;
    }

    public Results getResults() {
        return results;
    }

    public int getStatus() {
        return status;
    }
}
