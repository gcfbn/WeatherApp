package app;

import app.GUI.MainView;
import app.cache.CacheService;
import app.dto.raw_data.RawWeatherData;
import app.statusMessageBuilders.*;
import app.fileIO.IconReader;
import app.fileIO.LastSearchFiles;
import app.language.Language;
import app.language.ResourceBundleLoader;
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

    private ResourceBundleLoader statusMessageLoader;
    private final String statusMessageBundleName = "statusMessages";

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

        statusMessageLoader = new ResourceBundleLoader(statusMessageBundleName, model.getLanguage());

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
        onSettingsSwitch(null, model.getUnits(), model.getLanguage());
        this.view.reset();
        this.view.setStatusMessage(statusMessageLoader.getString("reset"));
    }

    public void onSettingsSwitch(Component senderComponent, Units units, Language language) {
        model.setUnits(units);
        model.setLanguage(language);
        // write language and units
        languageUnitsIO.writeLast(new LanguageUnits(language, units));

        // change locale of status message bundle
        statusMessageLoader = new ResourceBundleLoader(statusMessageBundleName, model.getLanguage());
    }

    public void onLastSearch(Component senderComponent) {

        onSearch(senderComponent, model.getLastSearchCity(), model.getUnits(), model.getLanguage());

        this.view.setCity(model.getLastSearchCity());
    }

    public void onSearch(Component senderComponent, String city, Units units, Language language) {

        if (city.equals("")) {
            this.view.setStatusMessage(statusMessageLoader.getString("empty.city.name"));
            return;
        }

        var query = new Query(HexSpaceConverter.spacesToHex(city), units, language);

        RawWeatherData rawWeatherData;
        Optional<RawWeatherData> optWeatherData = CacheService.readFreshData(query, responseCacheIO);

        if (optWeatherData.isPresent()) {
            rawWeatherData = optWeatherData.get();
            this.view.setStatusMessage(statusMessageLoader.getString("result.from.cache"));
        } else {
            this.view.setStatusMessage(statusMessageLoader.getString("communicating.with.api"));

            Response response = new OpenWeatherMapCaller().callApiAndGetResponse(query);
            if (response.isError()) {
                String statusMessage =
                        MessageFromResponseStatus.buildStatusMessage(response.getStatus(), query.language());
                this.view.setStatusMessage(statusMessage);
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

            this.view.setStatusMessage(statusMessageLoader.getString("result.from.api"));
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
        String statusMessage = statusMessageLoader.getString("clear.cache.success");

        try {
            FileUtils.cleanDirectory(new File("cache-serialized"));
            responseCacheIO.clear();
        } catch (IOException e) {
            statusMessage = statusMessageLoader.getString("clear.cache.error");
        } finally {
            this.view.setStatusMessage(statusMessage);
        }
    }

    public void setIconInView(JLabel iconLabel, String icon) {
        try {
            iconLabel.setIcon(IconReader.readIcon(icon));
            iconLabel.setVisible(true);
        } catch (IOException e) {
            this.view.setStatusMessage(statusMessageLoader.getString("reading.error"));
        }
    }
}
