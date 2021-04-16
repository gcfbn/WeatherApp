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

public class WeatherApp extends JFrame implements ActionListener
{
	public static void main (String[] args) throws UnirestException, IOException
	{
		WeatherApp app = new WeatherApp("Weather app");
		app.setVisible(true);
		app.pack();
	}
	
	//declare visible components (used for sending a request)
	private final JLabel cityLabel, units, language, iconLabel;
	private final JTextField city, description;
	private final JRadioButton metricUnits, imperialUnits;
	private	final ButtonGroup unitsGroup;
	private	final JRadioButton englishLanguage, polishLanguage;
	private	final ButtonGroup languageGroup;
	private	final BufferedImage icon;
	private	final JButton searchButton, reset, lastSearch;
			
	//declare hidden components (used for showing results)
	private	final JLabel currentTemperatureValue, minimalTemperatureLabel, maximalTemperatureLabel, feelsLikeLabel, pressureLabel, humidityLabel;
	private	final JTextField minimalTemperatureValue, maximalTemperatureValue, feelsLikeValue, pressureValue, humidityValue;
	private	final JLabel wind, windSpeedLabel, windDirectionLabel;
	private	final JTextField windSpeedValue, windDirectionValue;
	private	final JLabel sky, sunriseLabel, sunsetLabel, overcastLabel;
	private	final JTextField sunriseValue, sunsetValue, overcastValue;
	
	//variables used for loading last search
	public File lastSearchFile;
	private String lastSearchCity;
	
	WeatherApp(String title) throws IOException
	{
		//set properties of the main frame
		this.setResizable(false);
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(700, 450);
		setLayout(new GridBagLayout());

		{
			//set spaces between components
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.insets = new Insets(5, 3, 3, 5);
			constraints.fill = GridBagConstraints.HORIZONTAL;

			//initialize components, set their properties and add them to the main frame
			cityLabel = new JLabel("City:");
			cityLabel.setFont(new Font("Arial", Font.PLAIN, 26));
			constraints.gridwidth = 2;
			constraints.gridx = 0;
			constraints.gridy = 0;
			add(cityLabel, constraints);

			city = new JTextField();
			city.setFont(new Font("Arial", Font.PLAIN, 32));
			city.setPreferredSize(new Dimension(250, 40));
			constraints.gridheight = 1;
			constraints.gridwidth = 3;
			constraints.gridx = 0;
			constraints.gridy = 1;
			add(city, constraints);

			units = new JLabel("Units:");
			units.setFont(new Font("Arial", Font.PLAIN, 20));
			constraints.gridwidth = 2;
			constraints.gridx = 3;
			constraints.gridy = 0;
			constraints.fill = GridBagConstraints.CENTER;
			add(units, constraints);

			metricUnits = new JRadioButton("Metric");
			metricUnits.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridy = 1;
			constraints.gridwidth = 1;
			metricUnits.setSelected(true);
			add(metricUnits, constraints);

			imperialUnits = new JRadioButton("Imperial");
			imperialUnits.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridx = 4;
			constraints.gridwidth = 1;
			add(imperialUnits, constraints);

			unitsGroup = new ButtonGroup();
			unitsGroup.add(metricUnits);
			unitsGroup.add(imperialUnits);

			language = new JLabel("Language:");
			language.setFont(new Font("Arial", Font.PLAIN, 20));
			constraints.gridwidth = 2;
			constraints.gridx = 5;
			constraints.gridy = 0;
			constraints.fill = GridBagConstraints.CENTER;
			add(language, constraints);

			englishLanguage = new JRadioButton("English");
			englishLanguage.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridy = 1;
			constraints.gridwidth = 1;
			englishLanguage.setSelected(true);
			add(englishLanguage, constraints);

			polishLanguage = new JRadioButton("Polish");
			polishLanguage.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.fill = GridBagConstraints.HORIZONTAL;
			constraints.gridx = 6;
			add(polishLanguage, constraints);

			languageGroup = new ButtonGroup();
			languageGroup.add(polishLanguage);
			languageGroup.add(englishLanguage);

			icon = ImageIO.read(WeatherApp.class.getResourceAsStream("/empty.png"));
			iconLabel = new JLabel(new ImageIcon(icon));
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.gridheight = 2;
			constraints.gridwidth = 1;
			add(iconLabel, constraints);

			currentTemperatureValue = new JLabel();
			currentTemperatureValue.setFont(new Font("Arial", Font.BOLD, 26));
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.gridx = 1;
			add(currentTemperatureValue, constraints);

			searchButton = new JButton("Search");
			searchButton.setFont(new Font("Arial", Font.BOLD, 32));
			constraints.gridx = 3;
			constraints.gridy = 3;
			constraints.gridheight = 1;
			constraints.gridwidth = 4;
			add(searchButton, constraints);

			description = new JTextField();
			description.setEditable(false);
			description.setFont(new Font("Arial", Font.PLAIN, 20));
			constraints.gridx = 0;
			constraints.gridy = 4;
			constraints.gridwidth = 3;
			add(description, constraints);

			reset = new JButton("Reset");
			reset.setFont(new Font("Arial", Font.BOLD, 32));
			constraints.gridx = 3;
			constraints.gridwidth = 2;
			add(reset, constraints);

			lastSearch = new JButton("Last search");
			lastSearch.setFont(new Font("Arial", Font.BOLD, 32));
			constraints.gridx = 5;
			constraints.gridwidth = 2;
			add(lastSearch, constraints);

			minimalTemperatureLabel = new JLabel("Minimal temperature:");
			minimalTemperatureLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridwidth = 1;
			constraints.gridx = 0;
			constraints.gridy = 5;
			add(minimalTemperatureLabel, constraints);

			minimalTemperatureValue = new JTextField();
			minimalTemperatureValue.setFont(new Font("Arial", Font.PLAIN, 16));
			minimalTemperatureValue.setEditable(false);
			constraints.gridx = 1;
			add(minimalTemperatureValue, constraints);

			maximalTemperatureLabel = new JLabel("Maximal temperature:");
			maximalTemperatureLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridx = 0;
			constraints.gridy = 6;
			add(maximalTemperatureLabel, constraints);

			maximalTemperatureValue = new JTextField();
			maximalTemperatureValue.setFont(new Font("Arial", Font.PLAIN, 16));
			maximalTemperatureValue.setEditable(false);
			constraints.gridx = 1;
			add(maximalTemperatureValue, constraints);

			feelsLikeLabel = new JLabel("Feels like:");
			feelsLikeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridx = 0;
			constraints.gridy = 7;
			add(feelsLikeLabel, constraints);

			feelsLikeValue = new JTextField();
			feelsLikeValue.setFont(new Font("Arial", Font.PLAIN, 16));
			feelsLikeValue.setEditable(false);
			constraints.gridx = 1;
			add(feelsLikeValue, constraints);

			pressureLabel = new JLabel("Atmospheric pressure: ");
			pressureLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridx = 0;
			constraints.gridy = 8;
			add(pressureLabel, constraints);

			pressureValue = new JTextField();
			pressureValue.setFont(new Font("Arial", Font.PLAIN, 16));
			pressureValue.setEditable(false);
			constraints.gridx = 1;
			add(pressureValue, constraints);

			humidityLabel = new JLabel("Humidity: ");
			humidityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridx = 0;
			constraints.gridy = 9;
			add(humidityLabel, constraints);

			humidityValue = new JTextField();
			humidityValue.setFont(new Font("Arial", Font.PLAIN, 16));
			humidityValue.setEditable(false);
			constraints.gridx = 1;
			add(humidityValue, constraints);

			wind = new JLabel("Wind:");
			wind.setFont(new Font("Arial", Font.BOLD, 20));
			constraints.gridx = 3;
			constraints.gridy = 5;
			add(wind, constraints);

			windSpeedLabel = new JLabel("Speed:");
			windSpeedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridy = 6;
			add(windSpeedLabel, constraints);

			windSpeedValue = new JTextField();
			windSpeedValue.setFont(new Font("Arial", Font.PLAIN, 16));
			windSpeedValue.setEditable(false);
			constraints.gridx = 4;
			add(windSpeedValue, constraints);

			windDirectionLabel = new JLabel("Direction:");
			windDirectionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridx = 3;
			constraints.gridy = 7;
			add(windDirectionLabel, constraints);

			windDirectionValue = new JTextField();
			windDirectionValue.setFont(new Font("Arial", Font.PLAIN, 16));
			windDirectionValue.setEditable(false);
			constraints.gridx = 4;
			add(windDirectionValue, constraints);

			sky = new JLabel("Sky:");
			sky.setFont(new Font("Arial", Font.BOLD, 20));
			constraints.gridx = 5;
			constraints.gridy = 5;
			add(sky, constraints);

			sunriseLabel = new JLabel("Sunrise:");
			sunriseLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridy = 6;
			add(sunriseLabel, constraints);

			sunriseValue = new JTextField();
			sunriseValue.setFont(new Font("Arial", Font.PLAIN, 16));
			sunriseValue.setEditable(false);
			constraints.gridx = 6;
			add(sunriseValue, constraints);

			sunsetLabel = new JLabel("Sunset:");
			sunsetLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridx = 5;
			constraints.gridy = 7;
			add(sunsetLabel, constraints);

			sunsetValue = new JTextField();
			sunsetValue.setFont(new Font("Arial", Font.PLAIN, 16));
			sunsetValue.setEditable(false);
			constraints.gridx = 6;
			add(sunsetValue, constraints);

			overcastLabel = new JLabel("Overcast:");
			overcastLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			constraints.gridx = 5;
			constraints.gridy = 8;
			add(overcastLabel, constraints);

			overcastValue = new JTextField();
			overcastValue.setFont(new Font("Arial", Font.PLAIN, 16));
			overcastValue.setEditable(false);
			constraints.gridx = 6;
			add(overcastValue, constraints);

			//add actionListeners to objects
			englishLanguage.addActionListener(this);
			polishLanguage.addActionListener(this);
			searchButton.addActionListener(this);
			reset.addActionListener(this);
			lastSearch.addActionListener(this);

			//hide components that show results
			setVisibilityOfResults(false);
		}
		
		//set path to file with last searched city
		lastSearchFile = new File("../lastSearch.txt");
		lastSearchFile.createNewFile();

		//check if the file exists
		if (lastSearchFile.exists())
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(lastSearchFile));
			if ((lastSearchCity = bufferedReader.readLine()) == null) lastSearch.setEnabled(false);
			bufferedReader.close();
		}
		else 
		{
			lastSearch.setEnabled(false);
		}
	}
	
	//set events
	public void actionPerformed(ActionEvent arg0) 
	{
		Object actionSource = arg0.getSource();
		if (englishLanguage == actionSource)
		{
			if (englishLanguage.isSelected()) setEnglishLanguage();
		}
		else if (polishLanguage == actionSource)
		{
			if (polishLanguage.isSelected()) setPolishLanguage();
		}
		
		else if (searchButton == actionSource) search(getQuery());
		
		else if (reset == actionSource) resetApp();
		
		else if (lastSearch == actionSource)
		{
			search(new Query(lastSearchCity, getUnits(), getLanguage()));
			String cityWithSpaces = lastSearchCity.replaceAll("%20", " "); //converts spaces in ASCII code to visible spaces
			city.setText(cityWithSpaces);
		}
	}
	
	private void setEnglishLanguage()
	{
		cityLabel.setText("City:");
		units.setText("Units:");
		metricUnits.setText("Metric");
		imperialUnits.setText("Imperial");
		language.setText("Language:");
		englishLanguage.setText("English");
		polishLanguage.setText("Polish");
		searchButton.setText("Search");
		reset.setText("Reset");
		lastSearch.setText("Last search");
		minimalTemperatureLabel.setText("Minimal temperature:");
		maximalTemperatureLabel.setText("Maximal temperature:");
		feelsLikeLabel.setText("Feels like: ");
		pressureLabel.setText("Atmospheric pressure:");
		humidityLabel.setText("Humidity:");
		wind.setText("Wind:");
		windSpeedLabel.setText("Speed:");
		windDirectionLabel.setText("Direction:");
		sky.setText("Sky:");
		sunriseLabel.setText("Sunrise:");
		sunsetLabel.setText("Sunset:");
		overcastLabel.setText("Overcast:");
		this.pack(); //resizes the window
	}
	
	private void setPolishLanguage()
	{
		cityLabel.setText("Miasto:");
		units.setText("Jednostki:");
		metricUnits.setText("Metryczne");
		imperialUnits.setText("Imperialne");
		language.setText("Jêzyk:");
		englishLanguage.setText("Angielski");
		polishLanguage.setText("Polski");
		searchButton.setText("Szukaj");
		reset.setText("Reset");
		lastSearch.setText("Ostatania lokalizacja");
		minimalTemperatureLabel.setText("Minimalna temperatura: ");
		maximalTemperatureLabel.setText("Maksymalna temperatura:");
		feelsLikeLabel.setText("Odczuwalna temperatura:");
		pressureLabel.setText("Ciœnienie:");
		humidityLabel.setText("Wilgotnoœæ:");
		wind.setText("Wiatr:");
		windSpeedLabel.setText("Prêdkoœæ:");
		windDirectionLabel.setText("Kierunek:");
		sky.setText("Niebo:");
		sunriseLabel.setText("Wschód s³oñca:");
		sunsetLabel.setText("Zachód s³oñca:");
		overcastLabel.setText("Zachmurzenie:");
		this.pack(); //resizes the window
	}
	
	private void resetApp()
	{
		setEnglishLanguage();
		englishLanguage.setSelected(true);
		polishLanguage.setSelected(false);
		city.setText("");
		description.setText("");
		setVisibilityOfResults(false);
	}
	
	private void setVisibilityOfResults(boolean bool)
	{
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
		this.pack(); //resizes the window
	}
	
	private void search(Query query)
	{
		APICaller apiCaller = new APICaller();
		try 
		{
			//checks if query is correct
			int status = apiCaller.getStatus(query);
			if (status == 200) //query is correct
			{
					Results results = apiCaller.call(query);
					
					String temperatureUnit;
					if (query.units.equals("metric")) temperatureUnit = "C";
					else temperatureUnit = "F";
					
					//checks if apiCaller returned value that means there is no such data in RESTapi
					{
						if (results.currentTemperature != -273.15) currentTemperatureValue.setText(Double.toString(results.currentTemperature) + " °" + temperatureUnit);
						if (results.minimalTemperature != -273.15) minimalTemperatureValue.setText(Double.toString(results.minimalTemperature) + " °" + temperatureUnit);
						if (results.maximalTemperature != -273.15) maximalTemperatureValue.setText(Double.toString(results.maximalTemperature) + " °" + temperatureUnit);
						if (results.feelsLike != -273.15) feelsLikeValue.setText(Double.toString(results.feelsLike) + " °" + temperatureUnit);
						
						if (results.humidity != -1) humidityValue.setText(Integer.toString(results.humidity) + "%");
						if (results.pressure != -1) pressureValue.setText(Integer.toString(results.pressure) + " hPa");
						
						if (!results.description.equals("error")) description.setText(results.description);
						if (!results.icon.equals("error"))
						{
							try 
							{
								//read icon file and show it in GUI
								BufferedImage currentIcon = ImageIO.read(WeatherApp.class.getResourceAsStream("/" + results.icon + ".png"));
								iconLabel.setIcon(new ImageIcon(currentIcon));
								iconLabel.setVisible(true);
							} 
							catch (IOException e) {e.printStackTrace();}
						}
						
						String windSpeedUnit;
						if (query.units.equals("metric")) windSpeedUnit = "m/s";
						else windSpeedUnit = "mph";
						if (results.windSpeed != -1.0) windSpeedValue.setText(Double.toString(results.windSpeed) + " " + windSpeedUnit);
						
						//sets the direction [N/NE/E/SE/S/SW/W/NW] based on degrees
						String windCompass = "";
						if ((results.windDirection >= 330 && results.windDirection < 360) || (results.windDirection >= 0 && results.windDirection < 30)) windCompass = "N";
						else if (results.windDirection >= 30 && results.windDirection < 60) windCompass = "NE";
						else if (results.windDirection >= 60 && results.windDirection < 120) windCompass = "E";
						else if (results.windDirection >= 120 && results.windDirection < 150) windCompass = "SE";
						else if (results.windDirection >= 150 && results.windDirection < 210) windCompass = "S";
						else if (results.windDirection >= 210 && results.windDirection < 240) windCompass = "SW";
						else if (results.windDirection >= 240 && results.windDirection < 300) windCompass = "W";
						else if (results.windDirection >= 300 && results.windDirection < 330) windCompass = "NW";
						windDirectionValue.setText(windCompass);
						
						if (!results.sunrise.equals("error"))
						{
							Date sunriseDate = new Date(Long.parseLong(results.sunrise) * 1000); //creates date from unix time (GMT)
							Calendar sunriseCalendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
							sunriseCalendar.setTime(sunriseDate);
							String hours = Integer.toString(sunriseCalendar.get(Calendar.HOUR_OF_DAY));
							String minutes = Integer.toString(sunriseCalendar.get(Calendar.MINUTE));
							if (sunriseCalendar.get(Calendar.MINUTE) < 10) minutes = "0" + minutes; //adds '0' to begin of minutes
							sunriseValue.setText(hours + ":" + minutes);
						}
						if (!results.sunset.equals("error"))
						{
							Date sunsetDate = new Date(Long.parseLong(results.sunset) * 1000); //creates date from unix time (GMT)
							Calendar sunsetCalendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
							sunsetCalendar.setTime(sunsetDate);
							String hours = Integer.toString(sunsetCalendar.get(Calendar.HOUR_OF_DAY));
							String minutes = Integer.toString(sunsetCalendar.get(Calendar.MINUTE));
							if (sunsetCalendar.get(Calendar.MINUTE) < 10) minutes = "0" + minutes; //adds '0' to begin of minutes
							sunsetValue.setText(hours + ":" + minutes);
						}
						
						if (results.overcast != -1) overcastValue.setText(Integer.toString(results.overcast) + "%");
					}
					
					//write name of the city in file with last search
					FileWriter fileWriter;
					try 
					{
						fileWriter = new FileWriter(lastSearchFile);
						fileWriter.write(query.city);
						fileWriter.close();
						lastSearch.setEnabled(true);
						lastSearchCity = query.city;
					} 
					catch (IOException e) {e.printStackTrace();}
					
					setVisibilityOfResults(true);
			}
			else if (status == 400 || status == 404) //invalid request
			{
				String error;
				if (query.language.equals("en")) error = "Inavlid city name";
				else error = "Nieprawid³owe miasto";
				JOptionPane.showMessageDialog(this, error + "!", error, JOptionPane.ERROR_MESSAGE);
			}
			else if (status == 401 || status == 403) //authentication error
			{
				String error;
				if (query.language.equals("en")) error = "Authentication error";
				else error = "B³¹d autoryzacji";
				JOptionPane.showMessageDialog(this, error + "!", error, JOptionPane.ERROR_MESSAGE);
			}
			else //server error
			{
				String error;
				if (query.language.equals("en")) error = "Server error";
				else error = "B³¹d serwera";
				JOptionPane.showMessageDialog(this, error + "!", error, JOptionPane.ERROR_MESSAGE);
			}
		}
		catch (UnirestException e) {e.printStackTrace();}
	}
	
	private Query getQuery()
	{
		String cityName = new String(city.getText());
		String noSpacesCityName = cityName.replaceAll("\\s+", "%20"); //replaces spaces with hexadecimal ASCII code of space (to create URL properly)
		Query query = new Query(noSpacesCityName, getUnits(), getLanguage());
		return query;
	}
	
	private String getLanguage()
	{
		if (englishLanguage.isSelected()) return "en";
		else return "pl";
	}
	
	private String getUnits()
	{
		if (metricUnits.isSelected()) return "metric";
		else return "imperial";
	}
}