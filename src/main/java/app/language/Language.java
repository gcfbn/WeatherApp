package app.language;

public enum Language {

    ENGLISH("en", "US"),
    POLISH("pl", "PL");

    private final String languageCode;
    private final String countryCode;

    public String getLanguageCode() {
        return languageCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    Language(String languageCode, String countryCode) {
        this.languageCode = languageCode;
        this.countryCode = countryCode;
    }
}
