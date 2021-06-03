package app;

import app.GUI.MainView;
import app.dto.raw_data.RawWeatherData;
import app.errorBuilders.*;
import app.errorBuilders.Error;
import app.fileIO.LastSearchData;
import app.fileIO.LastSearchFiles;
import app.objectBox.languageUnits.LanguageUnits;
import app.objectBox.languageUnits.LanguageUnitsIO;
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

    private LanguageUnitsIO languageUnitsIO;

    private LastSearchFiles lastSearchFiles;

    public MainViewPresenter(MainView view, MainViewModel model) {
        this.view = view;
        this.model = model;

        this.lastSearchFiles = new LastSearchFiles("rawData.ser", "headers.ser");

        this.languageUnitsIO = new LanguageUnitsIO("language_units");
    }

    public void initialize() {

        Optional<LanguageUnits> lastLUOpt = languageUnitsIO.readLast();
        if (lastLUOpt.isPresent()) {
            model.setLanguage(lastLUOpt.get().language);
            model.setUnits(lastLUOpt.get().units);
        }

        this.initView();

        try {
            Optional<String> lastSearchCity = lastSearchFiles.createOrReadLastSearchedCity();
            var enable = lastSearchCity.isPresent() && !"".equals(lastSearchCity.get());
            this.view.setEnabledForLastSearchButton(enable);
            this.model.setLastSearchCity(lastSearchCity.get());
        } catch (IOException e) {
            // when something gone wrong when reading from file
            showError(null, ReadingErrorBuilder.buildReadingError(model.getLanguage()));
        }

        view.setVisible(true);
    }

    private void initView() {
        view.selectLanguage(model.getLanguage());
        view.selectUnits(model.getUnits());
    }

    public void onReset() {
        this.model.reset();
        this.initView();
        this.view.reset();
    }

    public void onSettingsSwitch(Component senderComponent, Units units, Language language) {
        model.setUnits(units);
        model.setLanguage(language);
        // write language and units
        languageUnitsIO.writeLast(new LanguageUnits(language, units));
    }

    public void onLastSearch(Component senderComponent, Units units, Language language) {
        Optional<LastSearchData> lastSearchData = lastSearchFiles.readFreshData();
        RawWeatherData weatherData;
        if (lastSearchData.isPresent() && (weatherData = lastSearchData.get().data()) != null) {
            ResultsFormatter resultsFormatter = new ResultsFormatter(units, weatherData);
            this.view.viewResults(resultsFormatter);
        } else {
            // TODO: use units from lastSearchData, not from GUI
            onSearch(senderComponent, lastSearchData.get().city(), units, language);
        }

        this.model.setCity(lastSearchData.get().city());
        this.view.setCity(lastSearchData.get().city());

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
        JOptionPane.showMessageDialog(parentComponent, error.text(), error.title(), JOptionPane.ERROR_MESSAGE);
    }
}
