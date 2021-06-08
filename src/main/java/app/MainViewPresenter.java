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
import app.objectBox.lastSearch.LastSearch;
import app.objectBox.lastSearch.LastSearchIO;
import app.objectBox.responseCache.ResponseCacheIO;
import app.objectBox.responseCache.ResponseRecord;
import app.query.HexSpaceConverter;
import app.query.Query;
import app.query.Units;
import app.resultPreparing.ResultsFormatter;
import app.weatherAPI.results.Response;
import app.weatherAPI.weatherAPICaller.OpenWeatherMapCaller;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MainViewPresenter {
    private final MainView view;
    private final MainViewModel model;

    private final LanguageUnitsIO languageUnitsIO;
    private final ResponseCacheIO responseCacheIO;
    private final LastSearchIO lastSearchIO;

    private final String filePathBegin = "cache-serialized/";

    public MainViewPresenter(MainView view, MainViewModel model) {
        this.view = view;
        this.model = model;

        this.languageUnitsIO = new LanguageUnitsIO("language_units");
        this.responseCacheIO = new ResponseCacheIO("cache");
        this.lastSearchIO = new LastSearchIO("last_search");
    }

    public void initialize() {

        Optional<LanguageUnits> lastLUOpt = languageUnitsIO.readLast();
        if (lastLUOpt.isPresent()) {
            model.setLanguage(lastLUOpt.get().language);
            model.setUnits(lastLUOpt.get().units);
        }

        this.initView();

        Optional<LastSearch> lastSearchCity = lastSearchIO.readLast();
        if (lastSearchCity.isPresent()) {
            view.setEnabledForLastSearchButton(true);
            model.setLastSearchCity(lastSearchCity.get().city);
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

    public void onLastSearch(Component senderComponent) {

        onSearch(senderComponent, model.getLastSearchCity(), model.getUnits(), model.getLanguage());

        this.view.setCity(model.getLastSearchCity());
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

            // write record containing results filepath to ObjectBox database
            responseCacheIO.write(new ResponseRecord(query, filePath, rawWeatherData.dt().getEpochSecond()));
        }

        // create ResultsFormatter
        ResultsFormatter resultsFormatter = new ResultsFormatter(query.units(), rawWeatherData);

        this.model.setCity(rawWeatherData.name());

        this.lastSearchIO.writeLast(new LastSearch(rawWeatherData.name()));
        this.model.setLastSearchCity(rawWeatherData.name());

        this.view.setEnabledForLastSearchButton(true);

        this.view.viewResults(resultsFormatter);
    }

    public void onClearCache(Component parentComponent) {
        try {
            FileUtils.cleanDirectory(new File("cache-serialized"));
        } catch (IOException e) {
            Error error = CleaningErrorBuilder.buildCleaningError(model.getLanguage());
            showError(parentComponent, error);
        }
    }

    private void showError(Component parentComponent, String errorMessage) {
        JOptionPane.showMessageDialog(parentComponent, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showError(Component parentComponent, Error error) {
        JOptionPane.showMessageDialog(parentComponent, error.text(), error.title(), JOptionPane.ERROR_MESSAGE);
    }
}
