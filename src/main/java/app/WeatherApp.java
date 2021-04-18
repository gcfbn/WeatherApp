package app;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.mashape.unirest.http.exceptions.UnirestException;

public class WeatherApp extends JFrame {

    public static void main(String[] args) {
        new WeatherApp();
    }

    // declare visible components (used for sending a request)
    private JLabel cityLabel, units, language, iconLabel;
    private JTextField city, description;
    private JRadioButton metricUnits, imperialUnits;
    private JRadioButton englishLanguage, polishLanguage;
    private JButton searchButton, reset, lastSearch;

    // declare hidden components (used for showing results)
    private JLabel currentTemperatureValue, minimalTemperatureLabel, maximalTemperatureLabel, feelsLikeLabel,
            pressureLabel, humidityLabel;
    private JTextField minimalTemperatureValue, maximalTemperatureValue, feelsLikeValue, pressureValue, humidityValue;
    private JLabel wind, windSpeedLabel, windDirectionLabel;
    private JTextField windSpeedValue, windDirectionValue;
    private JLabel sky, sunriseLabel, sunsetLabel, overcastLabel;
    private JTextField sunriseValue, sunsetValue, overcastValue;

    // variables used for loading last search
    public File lastSearchFile;
    private String lastSearchCity;

    WeatherApp() {

        if (!prepareGUI()) { // if something went wrong loading resources
            JOptionPane.showMessageDialog(this, "Could not load resources", "Resources error", JOptionPane.ERROR_MESSAGE);
            dispose();  // close app
        } else {  // everything works
            // set properties of the main frame
            this.setResizable(false);
            this.setTitle("Weather app");
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setLocation(300, 300);

            // resize window to fit the components
            pack();
            // show main window
            setVisible(true);
        }
    }

    /**
     * Initialize GUI components and add them to the main frame
     *
     * @return false if could not load some of the resources or true in other case
     */
    private boolean prepareGUI() {

        setLayout(new GridBagLayout());

        //TODO divide everything into classes
        //TODO code duplicates
        //TODO MVC?
        //TODO units to an Enum
        //TODO .txt file to .obj (does not make sense)

        // set spaces between components
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 3, 3, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // create fonts
        Font font16 = new Font("Arial", Font.PLAIN, 16);
        Font font20 = new Font("Arial", Font.PLAIN, 20);
        Font font26 = new Font("Arial", Font.PLAIN, 26);
        Font font32 = new Font("Arial", Font.PLAIN, 32);
        Font fontBold20 = new Font("Arial", Font.PLAIN, 20);
        Font fontBold32 = new Font("Arial", Font.BOLD, 32);

        // initialize components, set their properties and add them to the main frame

        cityLabel = new JLabel("City:");
        cityLabel.setFont(font26);
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(cityLabel, constraints);

        city = new JTextField();
        city.setFont(font32);
        city.setPreferredSize(new Dimension(250, 40));
        constraints.gridheight = 1;
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(city, constraints);

        units = new JLabel("Units:");
        units.setFont(font20);
        constraints.gridwidth = 2;
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.CENTER;
        add(units, constraints);

        metricUnits = new JRadioButton("Metric");
        metricUnits.setFont(font16);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        metricUnits.setSelected(true);
        add(metricUnits, constraints);

        imperialUnits = new JRadioButton("Imperial");
        imperialUnits.setFont(font16);
        constraints.gridx = 4;
        constraints.gridwidth = 1;
        add(imperialUnits, constraints);

        ButtonGroup unitsGroup = new ButtonGroup();
        unitsGroup.add(metricUnits);
        unitsGroup.add(imperialUnits);

        language = new JLabel("Language:");
        language.setFont(font20);
        constraints.gridwidth = 2;
        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.CENTER;
        add(language, constraints);

        englishLanguage = new JRadioButton("English");
        englishLanguage.setFont(font16);
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        englishLanguage.setSelected(true);
        add(englishLanguage, constraints);

        polishLanguage = new JRadioButton("Polish");
        polishLanguage.setFont(font16);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 6;
        add(polishLanguage, constraints);

        ButtonGroup languageGroup = new ButtonGroup();
        languageGroup.add(polishLanguage);
        languageGroup.add(englishLanguage);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 2;
        constraints.gridwidth = 1;

        // try to read empty icon from resources
        try {
            BufferedImage icon = ImageIO.read(new File("src/main/resources/empty.png"));
            iconLabel = new JLabel(new ImageIcon(icon));
            add(iconLabel, constraints);
        } catch (IOException e) {
            System.out.println("TUTAJ");
            return false;
        }

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 2;
        constraints.gridwidth = 1;

        currentTemperatureValue = new JLabel();
        currentTemperatureValue.setFont(font26);
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 1;
        add(currentTemperatureValue, constraints);

        searchButton = new JButton("Search");
        searchButton.setFont(font32);
        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.gridheight = 1;
        constraints.gridwidth = 4;
        add(searchButton, constraints);

        description = new JTextField();
        description.setEditable(false);
        description.setFont(font20);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        add(description, constraints);

        reset = new JButton("Reset");
        reset.setFont(fontBold32);
        constraints.gridx = 3;
        constraints.gridwidth = 2;
        add(reset, constraints);

        lastSearch = new JButton("Last search");
        lastSearch.setFont(fontBold32);
        constraints.gridx = 5;
        constraints.gridwidth = 2;
        lastSearch.setEnabled(false);
        add(lastSearch, constraints);

        minimalTemperatureLabel = new JLabel("Minimal temperature:");
        minimalTemperatureLabel.setFont(font16);
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(minimalTemperatureLabel, constraints);

        minimalTemperatureValue = new JTextField();
        minimalTemperatureValue.setFont(font16);
        minimalTemperatureValue.setEditable(false);
        constraints.gridx = 1;
        add(minimalTemperatureValue, constraints);

        maximalTemperatureLabel = new JLabel("Maximal temperature:");
        maximalTemperatureLabel.setFont(font16);
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(maximalTemperatureLabel, constraints);

        maximalTemperatureValue = new JTextField();
        maximalTemperatureValue.setFont(font16);
        maximalTemperatureValue.setEditable(false);
        constraints.gridx = 1;
        add(maximalTemperatureValue, constraints);

        feelsLikeLabel = new JLabel("Feels like:");
        feelsLikeLabel.setFont(font16);
        constraints.gridx = 0;
        constraints.gridy = 7;
        add(feelsLikeLabel, constraints);

        feelsLikeValue = new JTextField();
        feelsLikeValue.setFont(font16);
        feelsLikeValue.setEditable(false);
        constraints.gridx = 1;
        add(feelsLikeValue, constraints);

        pressureLabel = new JLabel("Atmospheric pressure: ");
        pressureLabel.setFont(font16);
        constraints.gridx = 0;
        constraints.gridy = 8;
        add(pressureLabel, constraints);

        pressureValue = new JTextField();
        pressureValue.setFont(font16);
        pressureValue.setEditable(false);
        constraints.gridx = 1;
        add(pressureValue, constraints);

        humidityLabel = new JLabel("Humidity: ");
        humidityLabel.setFont(font16);
        constraints.gridx = 0;
        constraints.gridy = 9;
        add(humidityLabel, constraints);

        humidityValue = new JTextField();
        humidityValue.setFont(font16);
        humidityValue.setEditable(false);
        constraints.gridx = 1;
        add(humidityValue, constraints);

        wind = new JLabel("Wind:");
        wind.setFont(fontBold20);
        constraints.gridx = 3;
        constraints.gridy = 5;
        add(wind, constraints);

        windSpeedLabel = new JLabel("Speed:");
        windSpeedLabel.setFont(font16);
        constraints.gridy = 6;
        add(windSpeedLabel, constraints);

        windSpeedValue = new JTextField();
        windSpeedValue.setFont(font16);
        windSpeedValue.setEditable(false);
        constraints.gridx = 4;
        add(windSpeedValue, constraints);

        windDirectionLabel = new JLabel("Direction:");
        windDirectionLabel.setFont(font16);
        constraints.gridx = 3;
        constraints.gridy = 7;
        add(windDirectionLabel, constraints);

        windDirectionValue = new JTextField();
        windDirectionValue.setFont(font16);
        windDirectionValue.setEditable(false);
        constraints.gridx = 4;
        add(windDirectionValue, constraints);

        sky = new JLabel("Sky:");
        sky.setFont(fontBold20);
        constraints.gridx = 5;
        constraints.gridy = 5;
        add(sky, constraints);

        sunriseLabel = new JLabel("Sunrise:");
        sunriseLabel.setFont(font16);
        constraints.gridy = 6;
        add(sunriseLabel, constraints);

        sunriseValue = new JTextField();
        sunriseValue.setFont(font16);
        sunriseValue.setEditable(false);
        constraints.gridx = 6;
        add(sunriseValue, constraints);

        sunsetLabel = new JLabel("Sunset:");
        sunsetLabel.setFont(font16);
        constraints.gridx = 5;
        constraints.gridy = 7;
        add(sunsetLabel, constraints);

        sunsetValue = new JTextField();
        sunsetValue.setFont(font16);
        sunsetValue.setEditable(false);
        constraints.gridx = 6;
        add(sunsetValue, constraints);

        overcastLabel = new JLabel("Overcast:");
        overcastLabel.setFont(font16);
        constraints.gridx = 5;
        constraints.gridy = 8;
        add(overcastLabel, constraints);

        overcastValue = new JTextField();
        overcastValue.setFont(font16);
        overcastValue.setEditable(false);
        constraints.gridx = 6;
        add(overcastValue, constraints);

        // create ActionListener
        MainActionListener actionListener = new MainActionListener();

        // add actionListeners to objects
        englishLanguage.addActionListener(actionListener);
        polishLanguage.addActionListener(actionListener);
        searchButton.addActionListener(actionListener);
        reset.addActionListener(actionListener);
        lastSearch.addActionListener(actionListener);

        // hide components that show results
        setVisibilityOfResults(false);

        // set path to file with last searched city
        lastSearchFile = new File("../lastSearch.txt");

        // create file if it doesn't exist
        // in other case, try to read from file
        if (lastSearchFile.exists()) {

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(lastSearchFile))) {
                if ((lastSearchCity = bufferedReader.readLine()) == null) lastSearch.setEnabled(false);
                lastSearch.setEnabled(true);
            } catch (IOException e) {
                return false;
            }
        }

        return true; // everything has ben loaded correctly
    }   // end of prepareGUI() method

    private class MainActionListener implements ActionListener {
        // set events
        public void actionPerformed(ActionEvent arg0) {

            Object actionSource = arg0.getSource();
            if (englishLanguage == actionSource) {
                setLanguage(Language.ENGLISH);
            } else if (polishLanguage == actionSource) {
                setLanguage(Language.POLISH);
            } else if (searchButton == actionSource)
                search(getQuery());

            else if (reset == actionSource) resetApp();

            else if (lastSearch == actionSource) {
                search(new Query(lastSearchCity, getUnits(), getLanguage()));
                String cityWithSpaces =
                        lastSearchCity.replaceAll("%20", " "); // converts spaces in ASCII code to visible spaces
                city.setText(cityWithSpaces);
            }
        }
    }

    private void setLanguage(Language l) {
        cityLabel.setText(l.cityLabel);
        units.setText(l.units);
        metricUnits.setText(l.metricUnits);
        imperialUnits.setText(l.imperialUnits);
        language.setText(l.language);
        englishLanguage.setText(l.englishLanguage);
        polishLanguage.setText(l.polishLangauge);
        searchButton.setText(l.searchButton);
        reset.setText(l.reset);
        lastSearch.setText(l.lastSearch);
        minimalTemperatureLabel.setText(l.minimalTemperatureLabel);
        maximalTemperatureLabel.setText(l.maximalTemperatureLabel);
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
        minimalTemperatureLabel.setVisible(bool);
        minimalTemperatureValue.setVisible(bool);
        maximalTemperatureLabel.setVisible(bool);
        maximalTemperatureValue.setVisible(bool);
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

        APICaller apiCaller = new APICaller();
        try {
            // checks if query is correct
            int status = apiCaller.getStatus(query);

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

                Results results = apiCaller.getResults(query);

                String temperatureUnit;
                if (query.getUnits().equals("metric")) temperatureUnit = "C";
                else temperatureUnit = "F";

                // set values from results
                {
                    currentTemperatureValue.setText(results.getCurrentTemperature() + " °" + temperatureUnit);
                    minimalTemperatureValue.setText(results.getMinimalTemperature() + " °" + temperatureUnit);
                    maximalTemperatureValue.setText(results.getMaximalTemperature() + " °" + temperatureUnit);
                    feelsLikeValue.setText(results.getFeelsLike() + " °" + temperatureUnit);
                    humidityValue.setText(results.getHumidity() + "%");
                    pressureValue.setText(results.getPressure() + " hPa");

                    description.setText(results.getDescription());

                    // try to read icon from file
                    try {
                        BufferedImage currentIcon = ImageIO.read(new File("/" + results.getIcon() + ".png"));
                        iconLabel.setIcon(new ImageIcon(currentIcon));
                        iconLabel.setVisible(true);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, "Could not load resource file!",
                                "Resource error", JOptionPane.ERROR_MESSAGE);
                    }

                    String windSpeedUnit;
                    if (query.getUnits().equals("metric")) windSpeedUnit = "m/s";
                    else windSpeedUnit = "mph";
                    windSpeedValue.setText(results.getWindSpeed() + " " + windSpeedUnit);

                    windDirectionValue.setText(results.getWindDirection());

                    if (!results.sunrise.equals("error")) {
                        Date sunriseDate =
                                new Date(Long.parseLong(results.sunrise) * 1000); // creates date from unix time (GMT)

                        Calendar sunriseCalendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
                        sunriseCalendar.setTime(sunriseDate);
                        String hours = Integer.toString(sunriseCalendar.get(Calendar.HOUR_OF_DAY));
                        String minutes = Integer.toString(sunriseCalendar.get(Calendar.MINUTE));
                        if (sunriseCalendar.get(Calendar.MINUTE) < 10)
                            minutes = "0" + minutes; // adds '0' to begin of minutes
                        sunriseValue.setText(hours + ":" + minutes);
                    }

                    if (!results.sunset.equals("error")) {
                        Date sunsetDate =
                                new Date(Long.parseLong(results.sunset) * 1000); // creates date from unix time (GMT)
                        Calendar sunsetCalendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
                        sunsetCalendar.setTime(sunsetDate);
                        String hours = Integer.toString(sunsetCalendar.get(Calendar.HOUR_OF_DAY));
                        String minutes = Integer.toString(sunsetCalendar.get(Calendar.MINUTE));
                        if (sunsetCalendar.get(Calendar.MINUTE) < 10)
                            minutes = "0" + minutes; // adds '0' to begin of minutes
                        sunsetValue.setText(hours + ":" + minutes);
                    }

                    overcastValue.setText(results.getOvercast() + "%");
                }

                // write name of the city in file with last search
                FileWriter fileWriter;
                try {
                    fileWriter = new FileWriter(lastSearchFile);
                    fileWriter.write(query.getCity());
                    fileWriter.close();
                    lastSearch.setEnabled(true);
                    lastSearchCity = query.getCity();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                setVisibilityOfResults(true);
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private Query getQuery() {
        String cityName = city.getText();
        String noSpacesCityName =
                cityName.replaceAll("\\s+", "%20"); // replaces spaces with hexadecimal ASCII code of space (to create URL properly)
        return new Query(noSpacesCityName, getUnits(), getLanguage());
    }

    private Language getLanguage() {
        if (englishLanguage.isSelected()) return Language.ENGLISH;
        else return Language.POLISH;
    }

    private String getUnits() {
        if (metricUnits.isSelected()) return "metric";
        else return "imperial";
    }
}