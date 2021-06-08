package app.statusMessageBuilders;

import app.language.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class MessageFromResponseStatusTest {

    @BeforeAll
    static void setLocale() {
        Locale.setDefault(
                new Locale(Language.ENGLISH.getLanguageCode(), Language.ENGLISH.getCountryCode()));
    }

    @Test
    public void requestErrorEnglish() {
        int statusCode = 400;
        String expectedResult = "Invalid city name!";
        String actualResult = MessageFromResponseStatus.buildStatusMessage(statusCode, Language.ENGLISH);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void autenthicationErrorPolish() {
        int statusCode = 401;
        String expectedResult = "Błąd autoryzacji!";
        String actualResult = MessageFromResponseStatus.buildStatusMessage(statusCode, Language.POLISH);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void serverErrorEnglish() {
        int statusCode = 500;
        String expectedResult = "Server error!";
        String actualResult = MessageFromResponseStatus.buildStatusMessage(statusCode, Language.ENGLISH);
        assertEquals(expectedResult, actualResult);
    }
}