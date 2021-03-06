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
import java.util.HashSet;
import java.util.Optional;

public class MainViewPresenter {
    private final MainView view;
    private final MainViewModel model;

    private final LanguageUnitsIO languageUnitsIO;
    private final ResponseCacheIO responseCacheIO;
    private final LastSearchIO lastSearchIO;

    private final File serializationDirectory = new File("cache-serialized");

    private ResourceBundleLoader statusMessageLoader;
    private final String statusMessageBundleName = "statusMessages";

    private HashSet<String> cityComboSet;

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

        // get cities from cache and create ComboBoxModel
        cityComboSet = new HashSet<>(responseCacheIO.readCities());

        this.initView();

        Optional<LastSearch> lastSearchCity = lastSearchIO.readLast();
        if (lastSearchCity.isPresent()) {
            view.setEnabledForLastSearchButton(true);
            model.setLastSearchCity(lastSearchCity.get().city);
        }

        // check if directory containing .ser files exists
        // if not, create it
        if (!serializationDirectory.exists()) {
            serializationDirectory.mkdir();
        }

        view.setVisible(true);
    }

    private void initView() {
        view.selectLanguage(model.getLanguage());
        view.selectUnits(model.getUnits());
        view.setModelForCityCombo(cityComboSet.toArray(new String[0]));
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
            String filePath = writeResultsAndGetPath(rawWeatherData);

            // write record containing results filepath and parameters to ObjectBox database
            responseCacheIO.write(new ResponseRecord(language, units, filePath,
                    rawWeatherData.name(), rawWeatherData.dt().getEpochSecond()));

            this.view.setStatusMessage(statusMessageLoader.getString("result.from.api"));
        }

        String responseCityName = rawWeatherData.name();

        // update city combobox
        updateComboBox(responseCityName);

        // create ResultsFormatter
        ResultsFormatter resultsFormatter = new ResultsFormatter(query.units(), rawWeatherData);

        this.model.setCity(responseCityName);

        this.lastSearchIO.writeLast(new LastSearch(responseCityName));
        this.model.setLastSearchCity(responseCityName);

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

        view.clearCityCombo();
        this.cityComboSet.clear();
    }

    public void setIconInView(JLabel iconLabel, String icon) {
        try {
            iconLabel.setIcon(IconReader.readIcon(icon));
            iconLabel.setVisible(true);
        } catch (IOException e) {
            this.view.setStatusMessage(statusMessageLoader.getString("reading.error"));
        }
    }

    private String writeResultsAndGetPath(RawWeatherData rawWeatherData) {
        String filePath =
                serializationDirectory.getPath()
                        + "/" + rawWeatherData.name()
                        + "_" + rawWeatherData.dt().getEpochSecond()
                        + ".ser";
        LastSearchFiles.writeWeatherData(rawWeatherData, filePath);
        return filePath;
    }

    private void updateComboBox(String cityName) {
        // add searched city name to combo set and update combobox if necessary
        if (cityComboSet.add(HexSpaceConverter.hexToSpaces(cityName))) {
            view.updateCityCombo(HexSpaceConverter.hexToSpaces(cityName));
        }
    }
}
