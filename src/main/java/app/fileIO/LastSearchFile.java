package app.fileIO;

import java.io.*;
import java.util.Optional;

public class LastSearchFile {
    File lastSearchFile;

    public LastSearchFile(String pathName) {
        this.lastSearchFile = new File(pathName);
    }

    public Optional<String> createOrReadLastSearchedCity() throws IOException {
        // create file if it doesn't exist
        // in other case, try to read from file

        if (lastSearchFile.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(lastSearchFile))) {
                return Optional.of(bufferedReader.readLine());
            }
        }

        return Optional.empty();
    }

    public void writeLastSearch(String city) throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(lastSearchFile))) {
            fileWriter.write(city);
        }
    }
}
