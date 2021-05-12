package app.GUI;

import app.language.Language;
import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;

public class StatusErrorBuilder {

    public static String buildErrorText(int status, Language l) {

        String error;

        if (status == 400 || status == 404) {   // invalid request
            error = (l == Language.ENGLISH) ? "Invalid city name!" : "Nieprawidłowe miasto!";
        } else if (status == 401 || status == 403) {    // authentication error
            error = (l == Language.ENGLISH) ? "Authentication error!" : "Błąd autoryzacji!";
        } else if (status == 500 || status == 501){
            error = (l == Language.ENGLISH) ? "Server error!" : "Błąd serwera!";
        } else if (status == OpenWeatherMapCaller.UNIREST_EXCEPTION){
            error = (l == Language.ENGLISH) ? "Unirest error!" : "Błąd biblioteki Unirest";
        } else { // status == OpenWeatherMapCaller.JACKSON_EXCEPTION
            error = (l == Language.ENGLISH) ? "Jackson error!" : "Błąd biblioteki Jackson";
        }

        return error;
    }

    public static String buildErrorTitle(Language l) {

        return (l == Language.ENGLISH) ? "Error" : "Błąd";
    }
}
