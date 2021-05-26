package app;

import app.GUI.MainView;
import app.dto.raw_data.RawWeatherData;
import app.errorBuilders.*;
import app.errorBuilders.Error;
import app.fileIO.LastSearchData;
import app.fileIO.LastSearchFiles;
import app.resultPreparing.ResultsFormatter;
import app.language.Language;
import app.query.HexSpaceConverter;
import app.query.Query;
import app.query.Units;
import app.weatherAPI.results.Response;
import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class MainViewPresenter {
    private final MainView view;
    private final MainViewModel model;

    private Language defaultLanguage = Language.ENGLISH;
    private Language language = defaultLanguage;

    private Units defaultUnits = Units.METRIC;
    private Units units = defaultUnits;

    private Optional<String> lastSearchCity;
    private LastSearchFiles lastSearchFiles;

    public MainViewPresenter(MainView view, MainViewModel model) {
        this.view = view;
        this.model = model;

        this.lastSearchFiles = new LastSearchFiles("rawData.ser", "headers.ser");
    }

    public void initialize() {
        // TODO: odczytaj ostatnie ustawienia użytkownika z dysku: język, jednostkę miary;

        this.initView();

        try {
            this.lastSearchCity = lastSearchFiles.createOrReadLastSearchedCity();
            var enable = this.lastSearchCity.isPresent() && !"".equals(this.lastSearchCity.get());
            this.view.setEnabledForLastSearchButton(enable);
        } catch (IOException e) {
            // when something gone wrong when reading from file
            showError(null, ReadingErrorBuilder.buildReadingError(language));
        }

        view.setVisible(true);
    }

    private void initView() {
        view.selectLanguage(language);
        view.selectUnits(units);
    }

    public void onReset() {
        units = defaultUnits;
        language = defaultLanguage;

        this.initView();
        this.view.reset();
    }

    public void onLastSearch(Component senderComponent, Units units, Language language) {
        LastSearchData lastSearchData = lastSearchFiles.readFreshData();

        if (lastSearchData.data().isPresent()) {
            ResultsFormatter resultsFormatter = new ResultsFormatter(units, lastSearchData.data().get());
            this.view.viewResults(resultsFormatter);
        } else {
            onSearch(senderComponent, lastSearchData.city().get(), units, language);
        }

        this.view.setCity(lastSearchData.city().get());

        // replace spaces with hex code of space ("%20")
        // HexSpaceConverter.hexToSpaces(lastSearchCity)
        // this.view.setCity(lastSearchCity.get());
    }

    public void onSearch(Component senderComponent, String city, Units units, Language language) {

        if (city.equals("")) {
            this.showError(senderComponent, "City field is empty !");
            return;
        }

        var query = new Query(HexSpaceConverter.spacesToHex(city), units, language);

        Response response = new OpenWeatherMapCaller().callApiAndGetResponse(query);
        if (response.isError()) {
            Error error = StatusErrorBuilder.buildStatusError(response.getStatus(), query.language());
            this.showError(senderComponent, error);
            return;
        }

        // get results from response
        RawWeatherData rawWeatherData = response.getJsonResults().get();

        // create ResultsFormatter
        ResultsFormatter resultsFormatter = new ResultsFormatter(query.units(), rawWeatherData);

        // write object containing data
        this.lastSearchFiles.writeWeatherData(rawWeatherData);
        this.view.setEnabledForLastSearchButton(true);

        this.view.viewResults(resultsFormatter);
    }

    private void showError(Component parentComponent, String errorMessage) {
        JOptionPane.showMessageDialog(parentComponent, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showError(Component parentComponent, Error error) {
        JOptionPane.showMessageDialog(parentComponent, error.getText(), error.getTitle(), JOptionPane.ERROR_MESSAGE);
    }
}
