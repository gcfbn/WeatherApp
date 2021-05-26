package app.fileIO;

import app.dto.raw_data.RawWeatherData;
import app.timeCalculation.TimeCalculator;
import com.mashape.unirest.http.Headers;
import lombok.Cleanup;

import java.io.*;
import java.util.Optional;

public class LastSearchJsonFile {

    private File weatherDataFile;
    private File headersFile;

    public LastSearchJsonFile(String weatherDataPathname, String headersPathname) {
        this.weatherDataFile = new File(weatherDataPathname);
        this.headersFile = new File(headersPathname);
    }

    public LastSearchData readFreshData() {
        Optional<RawWeatherData> optWeatherData = readJson();

        if (optWeatherData.isEmpty()) return new LastSearchData(Optional.empty(), Optional.empty());

        Optional<String> optCity = Optional.of(optWeatherData.get().name());

        long timeDifference = TimeCalculator.calculateMinutesFromNow(optWeatherData.get());

        return (timeDifference < 30) ? new LastSearchData(optWeatherData, optCity) :
                new LastSearchData(Optional.empty(), optCity);
    }

    public Optional<String> createOrReadLastSearchedCity() throws IOException {
        // create file if it doesn't exist
        // in other case, try to read from file

        if (weatherDataFile.exists()) {
            @Cleanup BufferedReader bufferedReader = new BufferedReader(new FileReader(weatherDataFile));
            return Optional.of(bufferedReader.readLine());
        }

        return Optional.empty();
    }

    private Optional<RawWeatherData> readJson() {

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
