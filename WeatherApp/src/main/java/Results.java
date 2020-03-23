public class Results
{
	public String icon;
	public double currentTemperature;
	public String descrpition;
	public double minimal_temperature, maximal_temperature, feelsLike;
	public int pressure, humidity, windDirection;
	public double windSpeed;
	public String sunrise, sunset;
	public int overcast;
	
	@Override
	public String toString()
	{
		return icon + " " + currentTemperature + " " + descrpition + " " + minimal_temperature + " " + maximal_temperature + " " + feelsLike + " " +
				pressure + " " + humidity + " " + windDirection + " " + windSpeed + " " + sunrise + " " + sunset + " " + overcast; 
	}
}