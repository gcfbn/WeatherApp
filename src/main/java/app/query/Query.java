package app.query;

public class Query {

    private final String city;
    private final Units units;
    private final Language language;

    public Query(String city, Units units, Language language) {
        this.city = city;
        this.units = units;
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public Units getUnits() {
        return units;
    }

    public Language getLanguage() {
        return language;
    }
}