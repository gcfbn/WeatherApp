package app.errorBuilders;

import app.language.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ReadingErrorBuilderTest {

    @BeforeAll
    static void setLocale() {
        Locale.setDefault(new Locale(Language.ENGLISH.getLanguageCode(), Language.ENGLISH.getCountryCode()));
    }

    @Test
    public void englishError() {
        Error expectedError = new Error("Reading error", "Could not read from file!");
        Error actualError = ReadingErrorBuilder.buildReadingError(Language.ENGLISH);
        assertEquals(expectedError.title(), actualError.title());
        assertEquals(expectedError.text(), actualError.text());
    }

    @Test
    public void polishError() {
        Error expectedError = new Error("Błąd odczytu", "Nie udało się odczytać z pliku!");
        Error actualError = ReadingErrorBuilder.buildReadingError(Language.POLISH);
        assertEquals(expectedError.title(), actualError.title());
        assertEquals(expectedError.text(), actualError.text());
    }
}