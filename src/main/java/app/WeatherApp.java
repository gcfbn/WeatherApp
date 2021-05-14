package app;

import app.GUI.MainView;
import app.language.Language;

import java.util.Locale;

public class WeatherApp {
    public static void main(String[] args) {
        // app will start in english
        Locale.setDefault(new Locale(Language.ENGLISH.getLanguageCode(), Language.ENGLISH.getCountryCode()));
        new MainView();
    }
}
