package app;

import app.language.Language;
import app.query.Units;

public class MainViewModel {

    private final static Units defaultUnits = Units.METRIC;
    private final static Language defaultLanguage = Language.ENGLISH;

    private String city = "";
    private Units units = defaultUnits;
    private Language language = defaultLanguage;
    private String lastSearchCity = "";

    public void reset(){
        units = defaultUnits;
        language = defaultLanguage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getLastSearchCity() {
        return lastSearchCity;
    }

    public void setLastSearchCity(String lastSearchCity) {
        this.lastSearchCity = lastSearchCity;
    }
}
