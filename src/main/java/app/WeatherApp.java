package app;

import app.GUI.MainView;
import app.language.Language;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

public class WeatherApp implements Runnable {

    public void run() {
        // app will start in english
        Locale.setDefault(new Locale(Language.ENGLISH.getLanguageCode(), Language.ENGLISH.getCountryCode()));

        // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        MainView view = new MainView();
        WeatherApp.registerWindowClosingEvent(view);

        MainViewPresenter presenter = new MainViewPresenter(view, new MainViewModel());
        presenter.initialize();
        view.setPresenter(presenter);
    }

    public static void main(String[] args) {

        Runnable app = new WeatherApp();

        try {
            SwingUtilities.invokeAndWait(app);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static void registerWindowClosingEvent(JFrame mainFrame) {
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                // JOptionPane.showMessageDialog(null, "Exiting ...");
                mainFrame.setVisible(false );
                System.exit(0);
            }
        });
    }
}



