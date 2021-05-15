package app;

import app.GUI.MainView;
import app.language.Language;
import app.utils.AppUtils;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Locale;

public class WeatherApp {
    public static void main(String[] args) {
        // app will start in english
        Locale.setDefault(new Locale(Language.ENGLISH.getLanguageCode(), Language.ENGLISH.getCountryCode()));

        AppUtils.invokeLater(() -> {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            MainView view = new MainView();
            WeatherApp.registerWindowClosingEvent(view);

            MainViewPresenter presenter = new MainViewPresenter(view, new MainViewModel());
            presenter.initialize();
            view.setPresenter(presenter);
        });
    }

    private static void registerWindowClosingEvent(JFrame view) {
        view.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                // JOptionPane.showMessageDialog(null, "Exiting ...");
                System.exit(0);
            }
        });
    }
}



