package app.weatherAPI.weatherAPICaller;

import app.query.Language;
import app.query.Query;
import app.query.Units;
import app.weatherAPI.results.Response;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class OpenWeatherMapCaller {

    private final static int UNIREST_EXCEPTION = 900;

    private HttpResponse<JsonNode> httpResponse = null;
    private final int status;

    public OpenWeatherMapCaller(Query query) {

        // create URL from query
        String URL = buildURL(query);

        // call for response
        try {
            httpResponse = Unirest.get(URL).asJson();
        } catch (UnirestException e) {
            status = UNIREST_EXCEPTION;
            return;
        }

        status = httpResponse.getStatus();
    }

    public Response buildResponse() {
        return new Response(httpResponse, status);
    }

    private String buildURL(Query query) {
        return "http://api.openweathermap.org/data/2.5/weather" + "?q=" + query.getCity() +
                // TODO hide API key
                "&appid=a52958f9ad25d7d64c67d97957bc6119" +  // API key
                "&units=" +
                ((query.getUnits() == Units.METRIC) ? "metric" : "imperial") +
                "&lang=" +
                ((query.getLanguage() == Language.ENGLISH) ? "en" : "pl");
    }
}