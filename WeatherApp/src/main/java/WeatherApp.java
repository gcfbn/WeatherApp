import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

import com.mashape.unirest.http.exceptions.UnirestException;

class WeatherApp extends JFrame implements ActionListener
{
	public static void main (String[] args) throws UnirestException, IOException
	{
		WeatherApp app = new WeatherApp("Weather app");
		app.setVisible(true);
		app.setResizable(false);
		app.pack();
	}
	
	//declare visible components (used for sending a request)
	private JLabel cityLabel, units, language, iconLabel;
	private	JTextField city, description;
	private	JRadioButton metricUnits, imperialUnits;
	private	ButtonGroup unitsGroup;
	private	JRadioButton englishLanguage, polishLanguage;
	public	ButtonGroup languageGroup;
	public	BufferedImage icon;
	public	JButton search, reset;
			
	//declare hidden components (used for showing results)
	private	JLabel currentTemperature, minimalTemperatureLabel, maximalTemperatureLabel, feelsLikeLabel, pressureLabel, humidityLabel;
	private	JTextField minimalTemperatureValue, maximalTemperatureValue, feelsLikeValue, pressureValue, humidityValue;
	private	JLabel wind, windSpeedLabel, windDirectionLabel;
	private	JTextField windSpeedValue, windDirectionValue;
	private	JLabel sky, sunriseLabel, sunsetLabel, overcastLabel;
	private	JTextField sunriseValue, sunsetValue, overcastValue;
	
	WeatherApp(String title) throws IOException
	{
		//set properties of the main frame
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(700, 450);
		setLayout(new GridBagLayout());
		
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
		
		icon = ImageIO.read(new File("src/main/resources/empty.png"));
		iconLabel = new JLabel(new ImageIcon(icon));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridheight = 2;
		constraints.gridwidth = 2;
		add(iconLabel, constraints);
		
		currentTemperature = new JLabel();
		currentTemperature.setFont(new Font ("Arial", Font.BOLD, 26));
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 2;
		add(currentTemperature, constraints);
		
		search = new JButton("Search");
		search.setFont(new Font("Arial", Font.BOLD, 32));
		constraints.gridx = 3;
		constraints.gridy = 3;
		constraints.gridheight = 1;
		constraints.gridwidth = 4;
		add(search, constraints);
		
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
		constraints.gridwidth = 4;
		add(reset, constraints);
		
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
		
		//add actionListeners to some objects
		englishLanguage.addActionListener(this);
		polishLanguage.addActionListener(this);
		search.addActionListener(this);
		reset.addActionListener(this);
		
		//hide components that show results
		setVisibilityOfResults(false);
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		Object actionSource = arg0.getSource();
		if (englishLanguage == actionSource)
		{
			if (englishLanguage.isSelected()) 
				{
					setEnglishLanguage();
				}
		}
		else if (polishLanguage == actionSource)
		{
			if (polishLanguage.isSelected()) 
				{
					setPolishLanguage();
				}
		}
		else if (search == actionSource)
		{
			Results results = null;
			try {
				results = APICaller.call(getQuery());
			} catch (UnirestException e) {e.printStackTrace();}
			System.out.println(results.toString());
			//TODO set values of textFields
			setVisibilityOfResults(true);
		}
		else if (reset == actionSource)
		{
			resetApp();
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
		search.setText("Search");
		reset.setText("Reset");
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
		this.pack();
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
		search.setText("Szukaj");
		reset.setText("Reset");
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
		this.pack();
	}
	
	private void resetApp()
	{
		setEnglishLanguage();
		englishLanguage.setSelected(true);
		polishLanguage.setSelected(false);
		city.setText("");
		setVisibilityOfResults(false);
	}
	
	private void setVisibilityOfResults(boolean bool)
	{
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
		this.pack();
	}
	
	private Query getQuery()
	{
		String cityName = new String(city.getText());
		String units;
		if (metricUnits.isSelected()) units = new String("metric");
		else units = new String("imperial");
		String language;
		if (englishLanguage.isSelected()) language = new String("en");
		else language = new String("pl");
		Query query = new Query(cityName, units, language);
		return query;
	}
}