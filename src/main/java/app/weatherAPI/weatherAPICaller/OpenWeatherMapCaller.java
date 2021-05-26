package app.weatherAPI.weatherAPICaller;

import app.dto.raw_data.RawWeatherDataJsonReader;
import app.query.Query;
import app.weatherAPI.results.Response;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Optional;

public class OpenWeatherMapCaller {

    public final static int UNIREST_EXCEPTION = 900;
    public final static int JACKSON_EXCEPTION = 1000;
    private final static String urlBegin = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final static String apiKey = "&appid=a52958f9ad25d7d64c67d97957bc6119";

    private RawWeatherDataJsonReader rawWeatherDataJsonReader;

    public OpenWeatherMapCaller() {
        this.rawWeatherDataJsonReader = new RawWeatherDataJsonReader();
    }

    public Response callApiAndGetResponse(Query query) {

        var callResult = callService(query);
        if (callResult.isError()) {
            return Response.fromStatus(callResult.getStatus(), callResult.isError());
        }

        /*
        Optional<JsonResults> jsonResults;

        try {
            // var x = rawWeatherDataJsonReader.fromJson(callResult.getHttpResponse().get().getBody().toString());
            jsonResults = Optional.of(JsonResultsMapper.mapResults(callResult.getHttpResponse().get()));
        } catch (JsonProcessingException e) {
            jsonResults = Optional.empty();
        }
         */

        return new Response(callResult.getHttpResponse().get(), callResult.getStatus());
    }

    public ServiceCallResult callService(Query query) {
        // create URL from query
        String URL = buildURL(query);

        try {
            return ServiceCallResult.fromResponse(Unirest.get(URL).asJson());
        } catch (UnirestException e) {
            return ServiceCallResult.fromException(e);
        }
    }

    private String buildURL(Query query) {
        String units = query.units().getUnitsCode();
        String language = (query.language().getLanguageCode());
        return String.format(urlBegin + "%s" + apiKey + "&units=" + "%s" + "&lang=" + "%s", query.city(), units, language);
    }

    private static class ServiceCallResult {
        int status;
        Optional<HttpResponse<JsonNode>> httpResponse;


        public boolean isError() {
            return this.status != 200;
        }

        public static ServiceCallResult fromResponse(HttpResponse<JsonNode> httpResponse) {
            return new ServiceCallResult(httpResponse.getStatus(), Optional.of(httpResponse));
        }

        public static ServiceCallResult fromException(UnirestException err) {
            return new ServiceCallResult(UNIREST_EXCEPTION, Optional.empty());
        }

        public int getStatus() {
            return status;
        }

        public Optional<HttpResponse<JsonNode>> getHttpResponse() {
            return httpResponse;
        }

        private ServiceCallResult(int status, Optional<HttpResponse<JsonNode>> httpResponse) {
            this.status = status;
            this.httpResponse = httpResponse;
        }
    }
}