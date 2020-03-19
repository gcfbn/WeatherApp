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

public class APICaller
{
	public static void main(String[] args) throws UnirestException
	{
		/*
		String test = new String("http://api.openweathermap.org/data/2.5/weather?q=Tokyo&units=metric&appid=a52958f9ad25d7d64c67d97957bc6119&lang=pl");
		HttpResponse <JsonNode> response = Unirest.get(test).asJson();
		//System.out.println(response.getBody().toString());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(response.getBody().toString());
		String prettyJsonString = gson.toJson(element);
		System.out.println(prettyJsonString);
		*/
	}
	/*
	public Results call(Query query) throws UnirestException
	{
		String URL = new String("http://api.openweathermap.org/data/2.5/weather");
		URL.concat("?q=").concat(query.city);
		URL.concat("?units=").concat(query.units);
		URL.concat("&appid=a52958f9ad25d7d64c67d97957bc6119");
		URL.concat("?lang=").concat(query.language);
	}
	*/
}