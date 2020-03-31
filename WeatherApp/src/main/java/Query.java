public class Query
{
	public String city, units, language;
	
	Query(String city, String units, String language)
	{
		this.city = city;
		this.units = units;
		this.language = language;
	}
	@Override
	public String toString()
	{
		return (city + " " + units + " " + language);
	}
}