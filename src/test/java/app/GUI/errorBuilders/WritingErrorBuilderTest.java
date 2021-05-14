package app.GUI.errorBuilders;

import app.language.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class WritingErrorBuilderTest {

    @BeforeAll
    static void setLocale() {
        Locale.setDefault(
                new Locale(Language.ENGLISH.getLanguageCode(), Language.ENGLISH.getCountryCode()));
    }

    @Test
    public void englishError() {
        Error expectedError = new Error("Writing error", "Could not write to file!");
        Error actualError = WritingErrorBuilder.buildWritingError(Language.ENGLISH);
        assertEquals(expectedError.getTitle(), actualError.getTitle());
        assertEquals(expectedError.getText(), actualError.getText());
    }

    @Test
    public void polishError() {
        Error expectedError = new Error("Błąd zapisu", "Nie udało się zapisać do pliku!");
        Error actualError = WritingErrorBuilder.buildWritingError(Language.POLISH);
        assertEquals(expectedError.getTitle(), actualError.getTitle());
        assertEquals(expectedError.getText(), actualError.getText());
    }
}