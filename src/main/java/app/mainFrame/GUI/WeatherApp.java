package app.mainFrame.GUI;

import app.fileOperations.IconReader;
import app.fileOperations.TxtReader;
import app.fileOperations.TxtWriter;
import app.query.Language;
import app.query.Query;
import app.query.QueryBuilder;
import app.query.Units;
import app.weatherAPI.results.JsonResults;
import app.weatherAPI.results.Response;
import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class WeatherApp extends JFrame{

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

    private File lastSearchFile;
    private String lastSearchCity;

    public static void main(String[] args){
        WeatherApp weatherApp = new WeatherApp();

    }

    WeatherApp(){
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
        if (lastSearchFile.exists()) {
            try {
                lastSearchCity = TxtReader.readLine(lastSearchFile);
                if (lastSearchCity != null)
                    lastSearchButton.setEnabled(true);
            } catch (IOException e) {
                // when something gone wrong when reading from file
                // TODO show error
                e.printStackTrace();
            }
        }
    }

    // TODO make ActionListeners working

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

        OpenWeatherMapCaller openWeatherMapCaller = new OpenWeatherMapCaller(query);

        Response response = openWeatherMapCaller.buildResponse();

        // checks if query is correct
        int status = response.getStatus();

        // show error if status is not equal 200
        if (status != 200) {

            String error;

            if (status == 400 || status == 404) {   // invalid request
                error = (query.getLanguage() == Language.ENGLISH) ? "Invalid city name!" : "Nieprawidłowe miasto!";
            } else if (status == 401 || status == 403) {    // authentication error
                error = (query.getLanguage() == Language.ENGLISH) ? "Authentication error!" : "Błąd autoryzacji!";
            } else if (status == 900) {  // UnirestException, my own code
                error = (query.getLanguage() == Language.ENGLISH) ? "Unirest error!" : "Wystąpił problem z Unirest!";
            } else { // server error 500/501
                error = (query.getLanguage() == Language.ENGLISH) ? "Server error!" : "Błąd serwera!";
            }

            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }

        // query is correct
        else {

            JsonResults jsonResults = response.getResults();

            String temperatureUnit;
            if (query.getUnits() == Units.METRIC) temperatureUnit = "C";
            else temperatureUnit = "F";

            // set values from results

            if (!jsonResults.getCurrentTemperature().equals(""))
                currentTemperatureValue.setText(jsonResults.getCurrentTemperature() + " °" + temperatureUnit);
            else currentTemperatureValue.setText("");

            if (!jsonResults.getMinimalTemperature().equals(""))
                minimumTemperatureValue.setText(jsonResults.getMinimalTemperature() + " °" + temperatureUnit);
            else minimumTemperatureValue.setText("");

            if (!jsonResults.getMaximalTemperature().equals(""))
                maximumTemperatureValue.setText(jsonResults.getMaximalTemperature() + " °" + temperatureUnit);
            else maximumTemperatureValue.setText("");

            if (!jsonResults.getFeelsLike().equals(""))
                feelsLikeValue.setText(jsonResults.getFeelsLike() + " °" + temperatureUnit);
            else feelsLikeValue.setText("");

            if (!jsonResults.getHumidity().equals(""))
                humidityValue.setText(jsonResults.getHumidity() + "%");
            else humidityValue.setText("");

            if (!jsonResults.getPressure().equals(""))
                pressureValue.setText(jsonResults.getPressure() + " hPa");
            else pressureValue.setText("");

            description.setText(jsonResults.getDescription());

            // try to read icon from file
            try {
                iconLabel.setIcon(IconReader.readIcon(jsonResults.getIcon()));
                iconLabel.setVisible(true);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Could not load resource file!",
                        "Resource error", JOptionPane.ERROR_MESSAGE);
            }

            String windSpeedUnit;
            if (query.getUnits() == Units.METRIC) windSpeedUnit = "m/s";
            else windSpeedUnit = "mph";
            windSpeedValue.setText(jsonResults.getWindSpeed() + " " + windSpeedUnit);

            windDirectionValue.setText(jsonResults.getWindDirection());

            if (jsonResults.getSunrise() != 0) {
                LocalDateTime sunriseDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(jsonResults.getSunrise()),
                        TimeZone.getDefault().toZoneId()); // creates date from unix time (GMT)

                String hours = Integer.toString(sunriseDate.getHour());
                String minutes = (sunriseDate.getMinute() < 10) ? "0" + sunriseDate.getMinute() :
                        Integer.toString(sunriseDate.getMinute());
                sunriseValue.setText(hours + ":" + minutes);
            } else sunriseValue.setText("");

            if (jsonResults.getSunset() != 0) {
                LocalDateTime sunsetDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(jsonResults.getSunset()),
                        TimeZone.getDefault().toZoneId()); // creates date from unix time (GMT)

                String hours = Integer.toString(sunsetDate.getHour());
                String minutes = (sunsetDate.getMinute() < 10) ? "0" + sunsetDate.getMinute() :
                        Integer.toString(sunsetDate.getMinute());
                sunsetValue.setText(hours + ":" + minutes);
            } else sunriseValue.setText("");

            if (!jsonResults.getOvercast().equals(""))
                overcastValue.setText(jsonResults.getOvercast() + "%");
            else overcastValue.setText("");

            // write name of the city in file with last search
            try {
                String cityToWrite = query.getCity();
                TxtWriter.writeLine(lastSearchFile, cityToWrite);
                lastSearchButton.setEnabled(true);
                lastSearchCity = cityToWrite;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Could not write file!",
                        "Writing error", JOptionPane.ERROR_MESSAGE);
            }

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
}
