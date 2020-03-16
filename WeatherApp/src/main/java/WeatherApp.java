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
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
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
		//APICaller.main(null);
		app.setVisible(true);
		app.pack();
		app.setResizable(false);
	}
	
	WeatherApp(String title) throws IOException
	{
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(700, 450);
		setLayout(new GridBagLayout());
		
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 3, 3, 5);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel cityLabel = new JLabel("City:");
		cityLabel.setFont(new Font("Arial", Font.PLAIN, 26));
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(cityLabel, constraints);
		
		JTextField city = new JTextField();
		city.setFont(new Font("Arial", Font.PLAIN, 32));
		city.setPreferredSize(new Dimension(250, 40));
		constraints.gridheight = 1;
		constraints.gridwidth = 3;
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(city, constraints);
		
		JLabel units = new JLabel("Units:");
		units.setFont(new Font("Arial", Font.PLAIN, 20));
		constraints.gridwidth = 2;
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.CENTER;
		add(units, constraints);
		
		JRadioButton metricUnits = new JRadioButton("Metric");
		metricUnits.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		metricUnits.setSelected(true);
		add(metricUnits, constraints);
		
		JRadioButton imperialUnits = new JRadioButton("Imperial");
		imperialUnits.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridx = 4;
		constraints.gridwidth = 1;
		add(imperialUnits, constraints);
		
		ButtonGroup unitsGroup = new ButtonGroup();
		unitsGroup.add(metricUnits);
		unitsGroup.add(imperialUnits);
		
		JLabel language = new JLabel("Language:");
		language.setFont(new Font("Arial", Font.PLAIN, 20));
		constraints.gridwidth = 2;
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.CENTER;
		add(language, constraints);
		
		JRadioButton englishLanguage = new JRadioButton("English");
		englishLanguage.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		englishLanguage.setSelected(true);
		add(englishLanguage, constraints);
		
		JRadioButton polishLanguage = new JRadioButton("Polish");
		polishLanguage.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 6;
		add(polishLanguage, constraints);
		
		ButtonGroup languageGroup = new ButtonGroup();
		languageGroup.add(polishLanguage);
		languageGroup.add(englishLanguage);
		
		BufferedImage icon = ImageIO.read(new File("src/main/resources/empty.png"));
		JLabel iconLabel = new JLabel(new ImageIcon(icon));
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridheight = 2;
		constraints.gridwidth = 2;
		add(iconLabel, constraints);
		
		JLabel currentTemperature = new JLabel("12 °C");
		currentTemperature.setFont(new Font ("Arial", Font.BOLD, 26));
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 2;
		add(currentTemperature, constraints);
		
		JLabel dateAndTime = new JLabel("Date/Time:");
		dateAndTime.setFont(new Font("Arial", Font.PLAIN, 20));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.gridx = 3;
		constraints.gridy = 2;
		add(dateAndTime, constraints);
		
		JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd.MM.yyyy");
		dateSpinner.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridx = 5;
		add(dateSpinner, constraints);
		
		JButton search = new JButton("Search");
		search.setFont(new Font("Arial", Font.BOLD, 32));
		constraints.gridx = 3;
		constraints.gridy = 3;
		constraints.gridwidth = 4;
		add(search, constraints);
		
		JTextField description = new JTextField();
		description.setEditable(false);
		description.setFont(new Font("Arial", Font.PLAIN, 20));
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 3;
		add(description, constraints);
		
		JButton reset = new JButton("Reset");
		reset.setFont(new Font("Arial", Font.BOLD, 32));
		constraints.gridx = 3;
		constraints.gridwidth = 4;
		add(reset, constraints);
		
		JLabel minimalTemperatureLabel = new JLabel("Minimal temperature:");
		minimalTemperatureLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridwidth = 1;
		constraints.gridx = 0;
		constraints.gridy = 5;
		add(minimalTemperatureLabel, constraints);
		
		JTextField minimalTemperatureValue = new JTextField();
		minimalTemperatureValue.setFont(new Font("Arial", Font.PLAIN, 16));
		minimalTemperatureValue.setEditable(false);
		constraints.gridx = 1;
		add(minimalTemperatureValue, constraints);
		
		JLabel maximalTemperatureLabel = new JLabel("Maximal temperature:");
		maximalTemperatureLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridx = 0;
		constraints.gridy = 6;
		add(maximalTemperatureLabel, constraints);
		
		JTextField maximalTemperatureValue = new JTextField();
		maximalTemperatureValue.setFont(new Font("Arial", Font.PLAIN, 16));
		maximalTemperatureValue.setEditable(false);
		constraints.gridx = 1;
		add(maximalTemperatureValue, constraints);
		
		JLabel feelsLikeLabel = new JLabel("Feels like:");
		feelsLikeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridx = 0;
		constraints.gridy = 7;
		add(feelsLikeLabel, constraints);
		
		JTextField feelsLikeValue = new JTextField();
		feelsLikeValue.setFont(new Font("Arial", Font.PLAIN, 16));
		feelsLikeValue.setEditable(false);
		constraints.gridx = 1;
		add(feelsLikeValue, constraints);
		
		JLabel pressureLabel = new JLabel("Atmospheric pressure: ");
		pressureLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridx = 0;
		constraints.gridy = 8;
		add(pressureLabel, constraints);
		
		JTextField pressureValue = new JTextField();
		pressureValue.setFont(new Font("Arial", Font.PLAIN, 16));
		pressureValue.setEditable(false);
		constraints.gridx = 1;
		add(pressureValue, constraints);
		
		JLabel humidityLabel = new JLabel("Humidity: ");
		humidityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridx = 0;
		constraints.gridy = 9;
		add(humidityLabel, constraints);
		
		JTextField humidityValue = new JTextField();
		humidityValue.setFont(new Font("Arial", Font.PLAIN, 16));
		humidityValue.setEditable(false);
		constraints.gridx = 1;
		add(humidityValue, constraints);
		
		JLabel wind = new JLabel("Wind:");
		wind.setFont(new Font("Arial", Font.BOLD, 20));
		constraints.gridx = 3;
		constraints.gridy = 5;
		add(wind, constraints);
		
		JLabel windSpeedLabel = new JLabel("Speed:");
		windSpeedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridy = 6;
		add(windSpeedLabel, constraints);
		
		JTextField windSpeedValue = new JTextField();
		windSpeedValue.setFont(new Font("Arial", Font.PLAIN, 16));
		windSpeedValue.setEditable(false);
		constraints.gridx = 4;
		add(windSpeedValue, constraints);
		
		JLabel windDirectionLabel = new JLabel("Direction:");
		windDirectionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridx = 3;
		constraints.gridy = 7;
		add(windDirectionLabel, constraints);
		
		JTextField windDirectionValue = new JTextField();
		windDirectionValue.setFont(new Font("Arial", Font.PLAIN, 16));
		windDirectionValue.setEditable(false);
		constraints.gridx = 4;
		add(windDirectionValue, constraints);
		
		JLabel sky = new JLabel("Sky:");
		sky.setFont(new Font("Arial", Font.BOLD, 20));
		constraints.gridx = 5;
		constraints.gridy = 5;
		add(sky, constraints);
		
		JLabel sunriseLabel = new JLabel("Sunrise:");
		sunriseLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridy = 6;
		add(sunriseLabel, constraints);
		
		JTextField sunriseValue = new JTextField();
		sunriseValue.setFont(new Font("Arial", Font.PLAIN, 16));
		sunriseValue.setEditable(false);
		constraints.gridx = 6;
		add(sunriseValue, constraints);
		
		JLabel sunsetLabel = new JLabel("Sunset:");
		sunsetLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridx = 5;
		constraints.gridy = 7;
		add(sunsetLabel, constraints);
		
		JTextField sunsetValue = new JTextField();
		sunsetValue.setFont(new Font("Arial", Font.PLAIN, 16));
		sunsetValue.setEditable(false);
		constraints.gridx = 6;
		add(sunsetValue, constraints);
		
		JLabel overcastLabel = new JLabel("Overcast:");
		overcastLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridx = 5;
		constraints.gridy = 8;
		add(overcastLabel, constraints);
		
		JTextField overcastValue = new JTextField();
		overcastValue.setFont(new Font("Arial", Font.PLAIN, 16));
		overcastValue.setEditable(false);
		constraints.gridx = 6;
		add(overcastValue, constraints);
		
		//make part of the components not visible	
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
}