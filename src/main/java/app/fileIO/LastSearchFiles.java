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

    public static <T> Optional<T> read(String filepath) {
        File file = new File(filepath);
        try (var inputStream = new ObjectInputStream(new FileInputStream(file))) {
            return Optional.of((T) inputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    public static boolean writeWeatherData(RawWeatherData rawWeatherData, String filepath) {
        return LastSearchFiles.write(rawWeatherData, new File(filepath));
    }

    public static boolean writeHeaders(Headers headers, String filepath) {
        return LastSearchFiles.write(headers, new File(filepath));
    }

    public Optional<LastSearchData> readFreshData() {
        Optional<RawWeatherData> optWeatherData = read(weatherDataFile);

        if (optWeatherData.isEmpty()) return Optional.empty();

        String city = optWeatherData.get().name();

        long timeDifference = TimeCalculator.calculateMinutesFromNow(optWeatherData.get());

        return (timeDifference < 30) ? Optional.of(new LastSearchData(optWeatherData.get(), city)) :
                Optional.of(new LastSearchData(null, city));
    }

    public Optional<String> createOrReadLastSearchedCity() throws IOException {
        // create file if it doesn't exist
        // in other case, try to read from file

        if (weatherDataFile.exists()) {
            Optional<RawWeatherData> rawWeatherData = read(weatherDataFile);
            return Optional.of(rawWeatherData.get().name());
        }
        return Optional.empty();
    }

    private <T> Optional<T> read(File file) {
        try (var inputStream = new ObjectInputStream(new FileInputStream(file))) {
            return Optional.of((T) inputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    private static <T> boolean write(T data, File file) {
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(data);
            return true;
        } catch (IOException e) {
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
