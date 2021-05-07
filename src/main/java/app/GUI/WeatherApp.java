package app.GUI;

import app.GUI.resultPreparing.ResultsFormatter;
import app.fileOperations.IconReader;
import app.fileOperations.TxtReader;
import app.fileOperations.TxtWriter;
import app.query.*;
import app.weatherAPI.results.JsonResults;
import app.weatherAPI.results.Response;
import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class WeatherApp extends JFrame {

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

    private final File lastSearchFile;
    private String lastSearchCity;

    public static void main(String[] args) {
        new WeatherApp();
    }

    WeatherApp() {
        setContentPane(this.mainPanel);
        setTitle("Weather app");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocation(300, 300);

        // hide components displaying results
        setVisibilityOfResults(false);

        // resize the window
        pack();

        setVisible(true);

        lastSearchFile = new File("lastSearch.txt");

        // create file if it doesn't exist
        // in other case, try to read from file
        createOrReadLastSearch(lastSearchFile);

        // add ActionListeners to buttons and radiobuttons
        addActionListeners();
    }

    private class MainActionListener implements ActionListener {

        // set events
        public void actionPerformed(ActionEvent arg0) {

            Object actionSource = arg0.getSource();
            if (englishLanguage == actionSource) {
                setLanguage(Language.ENGLISH);
            } else if (polishLanguage == actionSource) {
                setLanguage(Language.POLISH);
            } else if (searchButton == actionSource) {
                search(buildQuery());
            } else if (resetButton == actionSource) {
                resetApp();
            } else if (lastSearchButton == actionSource) {
                search(QueryBuilder.buildQuery(lastSearchCity, getUnits(), getLanguage()));
                // replace spaces with hex code of space ("%20")
                city.setText(HexSpaceConverter.hexToSpaces(lastSearchCity));
            }
        }
    }


    private void addActionListeners() {

        MainActionListener mainActionListener = new MainActionListener();
        englishLanguage.addActionListener(mainActionListener);
        polishLanguage.addActionListener(mainActionListener);
        searchButton.addActionListener(mainActionListener);
        resetButton.addActionListener(mainActionListener);
        lastSearchButton.addActionListener(mainActionListener);
    }

    // TODO use some multi-language-support library
    private void setLanguage(Language l) {
        cityLabel.setText(l.cityLabel);
        units.setText(l.units);
        metricUnits.setText(l.metricUnits);
        imperialUnits.setText(l.imperialUnits);
        language.setText(l.language);
        englishLanguage.setText(l.englishLanguage);
        polishLanguage.setText(l.polishLangauge);
        searchButton.setText(l.searchButton);
        resetButton.setText(l.reset);
        lastSearchButton.setText(l.lastSearch);
        minimumTemperatureLabel.setText(l.minimalTemperatureLabel);
        maximumTemperatureLabel.setText(l.maximalTemperatureLabel);
        feelsLikeLabel.setText(l.feelsLikeLabel);
        pressureLabel.setText(l.pressureLabel);
        humidityLabel.setText(l.humidityLabel);
        wind.setText(l.wind);
        windSpeedLabel.setText(l.windSpeedLabel);
        windDirectionLabel.setText(l.windDirectionLabel);
        sky.setText(l.sky);
        sunriseLabel.setText(l.sunriseLabel);
        sunsetLabel.setText(l.sunsetLabel);
        overcastLabel.setText(l.overcastLabel);
        pack(); // resize window
    }

    private void resetApp() {
        setLanguage(Language.ENGLISH);
        englishLanguage.setSelected(true);
        polishLanguage.setSelected(false);
        city.setText("");
        description.setText("");
        setVisibilityOfResults(false);
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

    // TODO refactor and shorten this method
    private void search(Query query) {

        // TODO refactor - first create object, then call API
        OpenWeatherMapCaller openWeatherMapCaller = new OpenWeatherMapCaller(query);

        Response response = openWeatherMapCaller.buildResponse();

        // checks if query is correct
        int status = response.getStatus();

        // TODO don't use status, instead use boolean like isSuccessful
        // show error if status is not equal 200
        if (status != 200) {
            String errorText = StatusErrorBuilder.buildErrorText(status, query.getLanguage());
            String errorTitle = StatusErrorBuilder.buildErrorTitle(query.getLanguage());

            JOptionPane.showMessageDialog(this, errorText, errorTitle, JOptionPane.ERROR_MESSAGE);
        }

        // query is correct
        else {

            // get results from response
            JsonResults jsonResults = response.getResults();

            // create ResultsFormatter
            ResultsFormatter resultsFormatter = new ResultsFormatter(query.getUnits(), jsonResults);

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

            setAndShowIcon(iconLabel, resultsFormatter.icon());

            // write name of the city in file with last search
            writeLastSearch(query.getCity());

            // show components containing results
            setVisibilityOfResults(true);
        }
    }

    private Query buildQuery() {
        return QueryBuilder.buildQuery(city.getText(), getUnits(), getLanguage());
    }

    private Language getLanguage() {
        if (englishLanguage.isSelected()) return Language.ENGLISH;
        else return Language.POLISH;
    }

    private Units getUnits() {
        if (metricUnits.isSelected()) return Units.METRIC;
        else return Units.IMPERIAL;
    }

    private void createOrReadLastSearch(File file) {
        if (file.exists()) {
            try {
                lastSearchCity = TxtReader.readLine(file);
                if (!Objects.equals(lastSearchCity, ""))
                    lastSearchButton.setEnabled(true);
            } catch (IOException e) {
                // when something gone wrong when reading from file
                JOptionPane.showMessageDialog(this, "Could not read from file!",
                        "Reading error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void writeLastSearch(String cityName) {
        try {
            TxtWriter.writeLine(lastSearchFile, cityName);
            lastSearchButton.setEnabled(true);
            lastSearchCity = cityName;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not write file!",
                    "Writing error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setAndShowIcon(JLabel iconLabel, String icon) {
        try {
            iconLabel.setIcon(IconReader.readIcon(icon));
            iconLabel.setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not load resource file!",
                    "Resource error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
