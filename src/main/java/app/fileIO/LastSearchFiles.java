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
        Optional<RawWeatherData> optWeatherData = read(weatherDataFile);

        if (optWeatherData.isEmpty()) return new LastSearchData(Optional.empty(), Optional.empty());

        Optional<String> optCity = Optional.of(optWeatherData.get().name());

        long timeDifference = TimeCalculator.calculateMinutesFromNow(optWeatherData.get());

        return (timeDifference < 30) ? new LastSearchData(optWeatherData, optCity) :
                new LastSearchData(Optional.empty(), optCity);
    }

    public Optional<String> createOrReadLastSearchedCity() throws IOException {
        // create file if it doesn't exist
        // in other case, try to read from file

        if (weatherDataFile.exists()){
            Optional<RawWeatherData> rawWeatherData = read(weatherDataFile);
            return Optional.of(rawWeatherData.get().name());
        }
        return Optional.empty();
    }

    private <T> Optional<T> read(File file){
        try (var inputStream = new ObjectInputStream(new FileInputStream(file))){
            return Optional.of((T) inputStream.readObject());
        } catch (IOException | ClassNotFoundException e){
            return Optional.empty();
        }
    }

    private <T> boolean write(T data, File file){
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(file))){
            outputStream.writeObject(data);
            return true;
        } catch (IOException e){
            return false;
        }
    }

    public boolean writeWeatherData(RawWeatherData rawWeatherData) {
        return write(rawWeatherData, weatherDataFile);
    }

    public boolean writeHeaders(Headers headers) {
        return write(headers, headersFile);
    }
}
