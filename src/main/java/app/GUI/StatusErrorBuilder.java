package app.GUI;

import app.query.Language;

import javax.swing.*;

public class StatusErrorBuilder {

    public static String buildErrorText(int status, Language l) {

        String error;

        if (status == 400 || status == 404) {   // invalid request
            error = (l == Language.ENGLISH) ? "Invalid city name!" : "Nieprawidłowe miasto!";
        } else if (status == 401 || status == 403) {    // authentication error
            error = (l == Language.ENGLISH) ? "Authentication error!" : "Błąd autoryzacji!";
        } else { // server error 500/501
            error = (l == Language.ENGLISH) ? "Server error!" : "Błąd serwera!";
        }

        return error;
    }

    public static String buildErrorTitle(Language l){

        return (l == Language.ENGLISH) ? "Error" : "Błąd";
    }
}
