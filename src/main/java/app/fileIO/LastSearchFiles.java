package app.fileIO;

import app.dto.raw_data.RawWeatherData;
import app.timeCalculation.TimeCalculator;
import com.mashape.unirest.http.Headers;

import java.io.*;
import java.util.Optional;

public class LastSearchFiles {

    private File weatherDataFile;
    private File headersFile;

    public LastSearchFiles(String weatherDataPathname, String headersPathname) {
        this.weatherDataFile = new File(weatherDataPathname);
        this.headersFile = new File(headersPathname);
    }

    public LastSearchData readFreshData() {
        Optional<RawWeatherData> optWeatherData = readWeatherData();

        if (optWeatherData.isEmpty()) return new LastSearchData(Optional.empty(), Optional.empty());

        Optional<String> optCity = Optional.of(optWeatherData.get().name());

        long timeDifference = TimeCalculator.calculateMinutesFromNow(optWeatherData.get());

        return (timeDifference < 30) ? new LastSearchData(optWeatherData, optCity) :
                new LastSearchData(Optional.empty(), optCity);
    }

    public Optional<String> createOrReadLastSearchedCity() throws IOException {
        // create file if it doesn't exist
        // in other case, try to read from file

        if (weatherDataFile.exists()) return Optional.of(readWeatherData().get().name());
        return Optional.empty();
    }

    private Optional<RawWeatherData> readWeatherData() {

        try (var inputStream =
                     new ObjectInputStream(
                             new BufferedInputStream(new FileInputStream(weatherDataFile)))) {
            return Optional.of((RawWeatherData) inputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    private Optional<Headers> readHeaders() {

        try (var inputStream =
                     new ObjectInputStream(
                             new BufferedInputStream(new FileInputStream(headersFile)))) {
            return Optional.of((Headers) inputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    public boolean writeWeatherData(RawWeatherData rawWeatherData) {
        try (var outputStream =
                     new ObjectOutputStream(
                             new BufferedOutputStream(new FileOutputStream(weatherDataFile)))) {
            outputStream.writeObject(rawWeatherData);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writeHeaders(Headers headers) {
        try (var outputStream =
                     new ObjectOutputStream(
                             new BufferedOutputStream(new FileOutputStream(headersFile)))) {
            outputStream.writeObject(headers);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
