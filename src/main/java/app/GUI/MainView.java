package app.GUI;

import app.resultPreparing.ResultsFormatter;
import app.MainViewPresenter;
import app.language.Language;
import app.language.ResourceBundleLoader;
import app.query.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static app.utils.SwingUtils.setSwingObjectText;

public class MainView extends JFrame {
    private MainViewPresenter presenter;

    private JLabel cityLabel;
    private JTextField city;
    private JLabel units;
    private JLabel language;
    private JRadioButton metricUnits;
    private JRadioButton imperialUnits;
    private JRadioButton englishLanguage;
    private JRadioButton polishLanguage;
    private JButton searchButton;
    private JButton resetButton;
    private JButton lastSearchButton;
    private JLabel iconLabel;
    private JTextField description;
    private JLabel minimumTemperatureLabel;
    private JTextField minimumTemperatureValue;
    private JTextField currentTemperatureValue;
    private JLabel maximumTemperatureLabel;
    private JTextField maximumTemperatureValue;
    private JLabel feelsLikeLabel;
    private JTextField feelsLikeValue;
    private JLabel pressureLabel;
    private JTextField pressureValue;
    private JLabel humidityLabel;
    private JTextField humidityValue;
    private JLabel wind;
    private JLabel sky;
    private JLabel windSpeedLabel;
    private JTextField windSpeedValue;
    private JLabel windDirectionLabel;
    private JTextField windDirectionValue;
    private JLabel sunriseLabel;
    private JTextField sunriseValue;
    private JLabel sunsetLabel;
    private JTextField sunsetValue;
    private JLabel overcastLabel;
    private JTextField overcastValue;

    // panel containing all components above
    private JPanel mainPanel;
    private JPanel statusBar;
    private JLabel statusMessage;
    private JButton clearCacheButton;

    public MainView() {
        setContentPane(this.mainPanel);
        setTitle("Weather app");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocation(300, 300);

        // hide components displaying results
        setVisibilityOfResults(false);

        // resize the window
        pack();

        // add ActionListeners to buttons and radiobuttons
        addActionListeners();
    }

    private class MainActionListener implements ActionListener {

        // set events
        public void actionPerformed(ActionEvent arg0) {

            Object actionSource = arg0.getSource();
            if (englishLanguage == actionSource) {
                setLanguage(Language.ENGLISH);
                presenter.onSettingsSwitch((Component) arg0.getSource(), selectedUnits(), selectedLanguage());
            } else if (polishLanguage == actionSource) {
                setLanguage(Language.POLISH);
                presenter.onSettingsSwitch((Component) arg0.getSource(), selectedUnits(), selectedLanguage());
            } else if (metricUnits == actionSource) {
                presenter.onSettingsSwitch((Component) arg0.getSource(), selectedUnits(), selectedLanguage());
            } else if (imperialUnits == actionSource) {
                presenter.onSettingsSwitch((Component) arg0.getSource(), selectedUnits(), selectedLanguage());
            } else if (searchButton == actionSource) {
                presenter.onSearch((java.awt.Component) arg0.getSource(), city.getText(), selectedUnits(), selectedLanguage());
            } else if (resetButton == actionSource) {
                presenter.onReset();
            } else if (lastSearchButton == actionSource) {
                presenter.onLastSearch((java.awt.Component) arg0.getSource());
            } else if (clearCacheButton == actionSource) {
                presenter.onClearCache((java.awt.Component) arg0.getSource());
            }
        }
    }

    private void addActionListeners() {
        MainActionListener mainActionListener = new MainActionListener();
        englishLanguage.addActionListener(mainActionListener);
        polishLanguage.addActionListener(mainActionListener);
        metricUnits.addActionListener(mainActionListener);
        imperialUnits.addActionListener(mainActionListener);
        searchButton.addActionListener(mainActionListener);
        resetButton.addActionListener(mainActionListener);
        lastSearchButton.addActionListener(mainActionListener);
        clearCacheButton.addActionListener(mainActionListener);
    }

    public void setPresenter(MainViewPresenter presenter) {
        this.presenter = presenter;
    }

    public void setCity(String city) {
        this.city.setText(city);
    }

    public void selectLanguage(Language language) {
        this.setLanguage(language);

        switch (language) {
            case ENGLISH -> englishLanguage.setSelected(true);
            case POLISH -> polishLanguage.setSelected(true);
        }
    }

    public void selectUnits(Units units) {
        switch (units) {
            case METRIC -> metricUnits.setSelected(true);

            case IMPERIAL -> imperialUnits.setSelected(true);
        }
    }

    private void setLanguage(Language language) {
        // create bundle loader
        ResourceBundleLoader resourceBundleLoader = new ResourceBundleLoader("components", language);

        for (Component component : this.mainPanel.getComponents()) {
            String stringId = component.getName();
            if (stringId != null) {
                setSwingObjectText(component, resourceBundleLoader.getString(stringId));
            }
        }

        pack(); // resize window
    }

    public void setStatusMessage(String message) {
        statusMessage.setText(message);
    }

    public void reset() {
        city.setText("");
        description.setText("");
        setVisibilityOfResults(false);
    }

    public void setEnabledForLastSearchButton(boolean value) {
        lastSearchButton.setEnabled(value);
    }

    private void setVisibilityOfResults(boolean bool) {
        iconLabel.setVisible(bool);
        currentTemperatureValue.setVisible(bool);
        minimumTemperatureLabel.setVisible(bool);
        minimumTemperatureValue.setVisible(bool);
        maximumTemperatureLabel.setVisible(bool);
        maximumTemperatureValue.setVisible(bool);
        feelsLikeLabel.setVisible(bool);
        feelsLikeValue.setVisible(bool);
        pressureLabel.setVisible(bool);
        pressureValue.setVisible(bool);
        humidityLabel.setVisible(bool);
        humidityValue.setVisible(bool);
        wind.setVisible(bool);
        windSpeedLabel.setVisible(bool);
        windSpeedValue.setVisible(bool);
        windDirectionLabel.setVisible(bool);
        windDirectionValue.setVisible(bool);
        sky.setVisible(bool);
        sunriseLabel.setVisible(bool);
        sunriseValue.setVisible(bool);
        sunsetLabel.setVisible(bool);
        sunsetValue.setVisible(bool);
        overcastLabel.setVisible(bool);
        overcastValue.setVisible(bool);
        this.pack(); // resizes the window
    }

    public void viewResults(ResultsFormatter resultsFormatter) {
        // set formatted values
        currentTemperatureValue.setText(resultsFormatter.currentTemperature());
        minimumTemperatureValue.setText(resultsFormatter.minimumTemperature());
        maximumTemperatureValue.setText(resultsFormatter.maximumTemperature());
        feelsLikeValue.setText(resultsFormatter.feelsLike());
        humidityValue.setText(resultsFormatter.humidity());
        pressureValue.setText(resultsFormatter.pressure());

        description.setText(resultsFormatter.description());

        windSpeedValue.setText(resultsFormatter.windSpeed());
        windDirectionValue.setText(resultsFormatter.windDirection());

        sunriseValue.setText(resultsFormatter.sunrise());
        sunsetValue.setText(resultsFormatter.sunset());

        overcastValue.setText(resultsFormatter.overcast());

        presenter.setIconInView(iconLabel, resultsFormatter.icon());

        // show components containing results
        setVisibilityOfResults(true);
    }

    private Language selectedLanguage() {
        return (englishLanguage.isSelected()) ? Language.ENGLISH : Language.POLISH;
    }

    private Units selectedUnits() {
        return (metricUnits.isSelected()) ? Units.METRIC : Units.IMPERIAL;
    }
}
