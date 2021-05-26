package app.language;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public enum Language {

    ENGLISH("en", "US", 0),
    POLISH("pl", "PL", 1);

    private final String languageCode;
    private final String countryCode;
    private final int index;
    @Id
    public long id;

    public String getLanguageCode() {
        return languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getIndex() { return index; }

    Language(String languageCode, String countryCode, int index) {
        this.languageCode = languageCode;
        this.countryCode = countryCode;
        this.index = index;
    }
}
