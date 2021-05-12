package app.language;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Language {

    ENGLISH("en"),
    POLISH("pl");

    private final String languageCode;
}
