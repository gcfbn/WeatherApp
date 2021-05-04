package app.weatherAPI.results;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class Response {

    private final JsonResults jsonResults;
    private final int status;

    public Response(HttpResponse<JsonNode> httpResponse, int status){
        jsonResults = JsonResultsMapper.mapResults(httpResponse);
        this.status = status;
    }

    public JsonResults getResults() {
        return jsonResults;
    }

    public int getStatus() {
        return status;
    }
}
