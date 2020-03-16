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
	}
	
	WeatherApp(String title) throws IOException
	{
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
		
		JLabel units = new JLabel ("Units:");
		units.setFont(new Font("Arial", Font.PLAIN, 20));
		constraints.gridwidth = 2;
		constraints.gridx = 3;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.CENTER;
		add(units, constraints);
		
		JRadioButton metricUnits = new JRadioButton ("Metric");
		metricUnits.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		metricUnits.setSelected(true);
		add(metricUnits, constraints);
		
		JRadioButton imperialUnits = new JRadioButton ("Imperial");
		imperialUnits.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridx = 4;
		constraints.gridwidth = 1;
		add(imperialUnits, constraints);
		
		ButtonGroup unitsGroup = new ButtonGroup();
		unitsGroup.add(metricUnits);
		unitsGroup.add(imperialUnits);
		
		JLabel language = new JLabel ("Language:");
		language.setFont(new Font("Arial", Font.PLAIN, 20));
		constraints.gridwidth = 2;
		constraints.gridx = 5;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.CENTER;
		add(language, constraints);
		
		JRadioButton englishLanguage = new JRadioButton ("English");
		englishLanguage.setFont(new Font("Arial", Font.PLAIN, 16));
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		englishLanguage.setSelected(true);
		add(englishLanguage, constraints);
		
		JRadioButton polishLanguage = new JRadioButton ("Polish");
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
		constraints.gridx = 2;
		add(currentTemperature, constraints);
		
		JLabel dateAndTime = new JLabel ("Date/Time:");
		dateAndTime.setFont(new Font("Arial", Font.PLAIN, 20));
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
		
		/*JLabel time = new JLabel ("Time: ");
		time.setFont(new Font("Arial", Font.PLAIN, 20));
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.gridx = 3;
		constraints.gridy = 3;
		add(time, constraints);
		
		SpinnerNumberModel hourModel = new SpinnerNumberModel(LocalDateTime.now().getHour(), 0, 23, 1);
		JSpinner hour = new JSpinner(hourModel);
		hour.setFont(new Font ("Arial", Font.PLAIN, 16));
		SpinnerNumberModel minuteModel = new SpinnerNumberModel(LocalDateTime.now().getMinute(), 0, 59, 1);
		JSpinner minute = new JSpinner(minuteModel);
		minute.setFont(new Font ("Arial", Font.PLAIN, 16));
		
		JPanel timeSpinners = new JPanel();
		timeSpinners.setLayout(new GridBagLayout());
		GridBagConstraints timeConstraints = new GridBagConstraints();
		timeConstraints.insets = new Insets(3,3,3,3);
		timeConstraints.gridheight = 1;
		timeConstraints.gridwidth = 1;
		timeConstraints.gridx = 0;
		timeConstraints.gridy = 0;
		
		timeSpinners.add(hour);
		timeConstraints.gridx = 1;
		timeSpinners.add(minute);
		constraints.gridx = 4;
		add(timeSpinners, constraints);*/
		
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
}