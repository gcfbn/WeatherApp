package app.GUI;

import app.GUI.resultPreparing.ResultsFormatter;
import app.fileOperations.IconReader;
import app.fileOperations.TxtReader;
import app.fileOperations.TxtWriter;
import app.language.Language;
import app.language.ResourceBundleLoader;
import app.query.*;
import app.weatherAPI.results.JsonResults;
import app.weatherAPI.results.Response;
import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
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
        // app will start in english
        Locale.setDefault(new Locale(Language.ENGLISH.getLanguageCode(), Language.ENGLISH.getCountryCode()));
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

    private void setLanguage(Language l) {
        // create bundle loader
        ResourceBundleLoader resourceBundleLoader = new ResourceBundleLoader("components", l);

        cityLabel.setText(resourceBundleLoader.getString("city"));
        units.setText(resourceBundleLoader.getString("units"));
        metricUnits.setText(resourceBundleLoader.getString("metric"));
        imperialUnits.setText(resourceBundleLoader.getString("imperial"));
        language.setText(resourceBundleLoader.getString("language"));
        englishLanguage.setText(resourceBundleLoader.getString("english"));
        polishLanguage.setText(resourceBundleLoader.getString("polish"));
        searchButton.setText(resourceBundleLoader.getString("search"));
        resetButton.setText(resourceBundleLoader.getString("reset"));
        lastSearchButton.setText(resourceBundleLoader.getString("last.search"));
        minimumTemperatureLabel.setText(resourceBundleLoader.getString("minimum.temperature"));
        maximumTemperatureLabel.setText(resourceBundleLoader.getString("maximum.temperature"));
        feelsLikeLabel.setText(resourceBundleLoader.getString("feels.like"));
        pressureLabel.setText(resourceBundleLoader.getString("atmospheric.pressure"));
        humidityLabel.setText(resourceBundleLoader.getString("humidity"));
        wind.setText(resourceBundleLoader.getString("wind"));
        windSpeedLabel.setText(resourceBundleLoader.getString("speed"));
        windDirectionLabel.setText(resourceBundleLoader.getString("direction"));
        sky.setText(resourceBundleLoader.getString("sky"));
        sunriseLabel.setText(resourceBundleLoader.getString("sunrise"));
        sunsetLabel.setText(resourceBundleLoader.getString("sunset"));
        overcastLabel.setText(resourceBundleLoader.getString("overcast"));
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

    private void search(Query query) {

        Response response = new OpenWeatherMapCaller().callApiAndGetResponse(query);

        // show error if response does not contain results
        if (!response.isSuccessful()) {
            String errorText = StatusErrorBuilder.buildErrorText(response.getStatus(), query.getLanguage());
            String errorTitle = StatusErrorBuilder.buildErrorTitle(query.getLanguage());

            JOptionPane.showMessageDialog(this, errorText, errorTitle, JOptionPane.ERROR_MESSAGE);
        }

        // query is correct
        else {

            // get results from response
            JsonResults jsonResults = response.getJsonResults();

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
