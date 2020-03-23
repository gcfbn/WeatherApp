import java.net.URLEncoder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class APICaller
{
	APICaller()
	{
		/*try {
			call(new Query("London", "metric", "pl"));
		} catch (UnirestException e) {e.printStackTrace();}*/
	}
	
	public static Results call(Query query) throws UnirestException
	{
		String URL = "http://api.openweathermap.org/data/2.5/weather";
		URL = URL + "?q=" + query.city;
		URL = URL + "&appid=a52958f9ad25d7d64c67d97957bc6119";
		URL = URL + "&units=" + query.units;
		URL = URL + "&lang=" + query.language;
		HttpResponse <JsonNode> response = Unirest.get(URL).asJson();
		//TODO check if response status equals 201
		System.out.println(URL);
		JSONObject resultsObject = new JSONObject(response.getBody().toString());
		//get data from JSONObject
		Results results = new Results();
		//temperature etc.
		JSONObject temperatureObject = resultsObject.getJSONObject("main");
		results.currentTemperature = temperatureObject.getDouble("temp");
		results.minimal_temperature = temperatureObject.getDouble("temp_min");
		results.maximal_temperature = temperatureObject.getDouble("temp_max");
		results.humidity = temperatureObject.getInt("humidity");
		results.pressure = temperatureObject.getInt("pressure");
		results.feelsLike = temperatureObject.getDouble("feels_like");
		
		//overcast
		results.overcast = resultsObject.getJSONObject("clouds").getInt("all");
		System.out.println(results.overcast);
		
		//wind
		JSONObject windObject = resultsObject.getJSONObject("wind");
		results.windDirection = windObject.getInt("deg");
		results.windSpeed = windObject.getDouble("speed");
		
		//sun
		JSONObject sunObject = resultsObject.getJSONObject("sys");
		results.sunrise = sunObject.getString("sunrise");
		results.sunset = sunObject.getString("sunset");
		
		//icon & description
		//"weather" key in returned JSON file is an array
		//weather with index 0 is the prime weather and it will be shown
		JSONArray iconArray = (JSONArray)resultsObject.get("weather");
		JSONObject iconObject = (JSONObject) iconArray.get(0);
		results.icon = iconObject.getString("icon");
		results.descrpition = iconObject.getString("description");
		
		return results;
	}
	
}