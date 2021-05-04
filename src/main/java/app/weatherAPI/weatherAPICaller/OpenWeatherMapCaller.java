package app.weatherAPI.weatherAPICaller;

import app.query.Language;
import app.query.Query;
import app.query.Units;
import app.weatherAPI.results.Results;
import app.weatherAPI.results.ResultsMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import kong.unirest.json.JSONObject;

public class OpenWeatherMapCaller {

    private final static int UNIREST_EXCEPTION = 900;
    private final static int CORRECT_STATUS = 200;

    private final Results results;
    private final int status;

    public OpenWeatherMapCaller(Query query){

        // create URL from query
        String URL = buildURL(query);

        HttpResponse<JsonNode> response;

        // call for response
        try{
            response = Unirest.get(URL).asJson();
        } catch (UnirestException e) {
            status = UNIREST_EXCEPTION;
            results = null;
            return;
        }

        status = response.getStatus();

        // if status means that query is correct, build results
        // else set results field to null
        results = (status == CORRECT_STATUS) ? buildResults(response) : null;
    }

    public Results buildResults(HttpResponse<JsonNode> response) {

        // get JSONObject from response
        JSONObject resultsObject = new JSONObject(response.getBody().toString());

        return ResultsMapper.mapResults(resultsObject);
    }

    private String buildURL(Query query) {
        String URL = "http://api.openweathermap.org/data/2.5/weather";
        URL = URL + "?q=" + query.getCity();
        URL = URL + "&appid=a52958f9ad25d7d64c67d97957bc6119";  // API key
        URL = URL + "&units=" + ((query.getUnits() == Units.METRIC) ? "metric" : "imperial");
        URL = URL + "&lang=" + ((query.getLanguage() == Language.ENGLISH) ? "en" : "pl");
        return URL;
    }

    public int getStatus(){
        return this.status;
    }

    public Results getResults(){
        return this.results;
    }
}