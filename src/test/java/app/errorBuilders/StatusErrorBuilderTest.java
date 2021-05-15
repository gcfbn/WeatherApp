package app.errorBuilders;

import app.language.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class StatusErrorBuilderTest {

    @BeforeAll
    static void setLocale(){
        Locale.setDefault(
                new Locale(Language.ENGLISH.getLanguageCode(), Language.ENGLISH.getCountryCode()));
    }

    @Test
    public void requestErrorEnglish(){
        int statusCode = 400;
        Error expectedResult = new Error("Error", "Invalid city name!");
        Error actualResult = StatusErrorBuilder.buildStatusError(statusCode, Language.ENGLISH);
        assertEquals(expectedResult.getTitle(), actualResult.getTitle());
        assertEquals(expectedResult.getText(), actualResult.getText());
    }

    @Test
    public void autenthicationErrorPolish(){
        int statusCode = 401;
        Error expectedResult = new Error("Błąd", "Błąd autoryzacji!");
        Error actualResult = StatusErrorBuilder.buildStatusError(statusCode, Language.POLISH);
        assertEquals(expectedResult.getTitle(), actualResult.getTitle());
        assertEquals(expectedResult.getText(), actualResult.getText());
    }

    @Test
    public void serverErrorEnglish(){
        int statusCode = 500;
        Error expectedResult = new Error("Error", "Server error!");
        Error actualResult = StatusErrorBuilder.buildStatusError(statusCode, Language.ENGLISH);
        assertEquals(expectedResult.getTitle(), actualResult.getTitle());
        assertEquals(expectedResult.getText(), actualResult.getText());
    }
}