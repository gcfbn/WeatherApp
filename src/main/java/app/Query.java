package app;

public class Query {

    private final String city, units;
    private final Language language;

    Query(String city, String units, Language language) {
        this.city = city;
        this.units = units;
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public String getUnits() {
        return units;
    }

    public Language getLanguage() {
        return language;
    }
}