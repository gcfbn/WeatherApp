package app;

import app.GUI.MainView;
import app.cache.CacheService;
import app.dto.raw_data.RawWeatherData;
import app.errorBuilders.*;
import app.errorBuilders.Error;
import app.fileIO.LastSearchFiles;
import app.language.Language;
import app.objectBox.languageUnits.LanguageUnits;
import app.objectBox.languageUnits.LanguageUnitsIO;
import app.objectBox.responseCache.ResponseCacheIO;
import app.objectBox.responseCache.ResponseRecord;
import app.query.HexSpaceConverter;
import app.query.Query;
import app.query.Units;
import app.resultPreparing.ResultsFormatter;
import app.weatherAPI.results.Response;
import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class MainViewPresenter {
    private final MainView view;
    private final MainViewModel model;

    private LanguageUnitsIO languageUnitsIO;
    private ResponseCacheIO responseCacheIO;

    private LastSearchFiles lastSearchFiles;

    private String filePathBegin = "cache-serialized/";

    public MainViewPresenter(MainView view, MainViewModel model) {
        this.view = view;
        this.model = model;

        this.lastSearchFiles = new LastSearchFiles("rawData.ser", "headers.ser");

        this.languageUnitsIO = new LanguageUnitsIO("language_units");
        this.responseCacheIO = new ResponseCacheIO("cache");
    }

    public void initialize() {

        Optional<LanguageUnits> lastLUOpt = languageUnitsIO.readLast();
        if (lastLUOpt.isPresent()) {
            model.setLanguage(lastLUOpt.get().language);
            model.setUnits(lastLUOpt.get().units);
        }

        this.initView();

//        try {
//            Optional<String> lastSearchCity = lastSearchFiles.createOrReadLastSearchedCity();
//            var enable = lastSearchCity.isPresent() && !"".equals(lastSearchCity.get());
//            this.view.setEnabledForLastSearchButton(enable);
//            this.model.setLastSearchCity(lastSearchCity.get());
//        } catch (IOException e) {
//            // when something gone wrong when reading from file
//            showError(null, ReadingErrorBuilder.buildReadingError(model.getLanguage()));
//        }

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

    public void onLastSearch(Component senderComponent) {
//        Query weatherQuery = new Query(model.getLastSearchCity(), model.getUnits(), model.getLanguage());
//        RawWeatherData weatherData;
//        if (lastSearchData.isPresent() && (weatherData = lastSearchData.get().data()) != null) {
//            ResultsFormatter resultsFormatter = new ResultsFormatter(units, weatherData);
//            this.view.viewResults(resultsFormatter);
//        } else {
//            // TODO: use units from lastSearchData, not from GUI
//            onSearch(senderComponent, lastSearchData.get().city(), units, language);
//        }

        onSearch(senderComponent, model.getLastSearchCity(), model.getUnits(), model.getLanguage());

        this.view.setCity(model.getLastSearchCity());
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

        RawWeatherData rawWeatherData;
        Optional<RawWeatherData> optWeatherData = CacheService.readFreshData(query, responseCacheIO);

        if (optWeatherData.isPresent()) rawWeatherData = optWeatherData.get();
        else {
            System.out.println("calling query");
            Response response = new OpenWeatherMapCaller().callApiAndGetResponse(query);
            if (response.isError()) {
                Error error = StatusErrorBuilder.buildStatusError(response.getStatus(), query.language());
                this.showError(senderComponent, error);
                return;
            }
            // get results from response
            rawWeatherData = response.getJsonResults().get();

            // write results
            String filePath =
                    filePathBegin + rawWeatherData.name() + "_" + rawWeatherData.dt().getEpochSecond() + ".ser";
            LastSearchFiles.writeWeatherData(rawWeatherData, filePath);

//             write record containing results filepath to ObjectBox database
            responseCacheIO.write(new ResponseRecord(query, filePath, rawWeatherData.dt().getEpochSecond()));
        }

        // create ResultsFormatter
        ResultsFormatter resultsFormatter = new ResultsFormatter(query.units(), rawWeatherData);

        this.model.setCity(rawWeatherData.name());
        this.model.setLastSearchCity(rawWeatherData.name());

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
