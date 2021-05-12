package app.weatherAPI.weatherAPICaller;

import app.query.Query;
import app.query.Units;
import app.weatherAPI.results.Response;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class OpenWeatherMapCaller {

    public final static int UNIREST_EXCEPTION = 900;
    public final static int JACKSON_EXCEPTION = 1000;
    private final static String urlBegin = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final static String apiKey = "&appid=a52958f9ad25d7d64c67d97957bc6119";


    public Response callApiAndGetResponse(Query query) {

        // create URL from query
        String URL = buildURL(query);

        int status;
        HttpResponse<JsonNode> httpResponse = null;

        // call for response
        try {
            httpResponse = Unirest.get(URL).asJson();
            status = httpResponse.getStatus();
        } catch (UnirestException e) {
            status = UNIREST_EXCEPTION;
        }

        return new Response(httpResponse, status);
    }

    private String buildURL(Query query) {
        String units = ((query.getUnits() == Units.METRIC) ? "metric" : "imperial");
        String language = (query.getLanguage().getLanguageCode());
        return String.format(urlBegin + "%s" + apiKey + "&units=" + "%s" + "&lang=" + "%s", query.getCity(), units, language);
    }
}