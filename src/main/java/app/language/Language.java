package app.language;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {

    ENGLISH("en", "US"),
    POLISH("pl", "PL");

    private final String languageCode;
    private final String countryCode;
}
