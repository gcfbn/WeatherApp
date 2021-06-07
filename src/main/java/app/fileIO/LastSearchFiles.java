package app.fileIO;

import app.dto.raw_data.RawWeatherData;
import com.mashape.unirest.http.Headers;

import java.io.*;
import java.util.Optional;

public class LastSearchFiles {

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

    private static <T> boolean write(T data, File file) {
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(data);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
