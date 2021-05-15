package app.fileIO;

import lombok.Cleanup;

import java.io.*;
import java.util.Optional;

public class LastSearchFile {
    File lastSearchFile;

    public LastSearchFile( String pathName ) {
        this.lastSearchFile = new File(pathName);
    }

    public Optional<String> createOrReadLastSearchedCity() throws IOException {
        // create file if it doesn't exist
        // in other case, try to read from file

        if (lastSearchFile.exists()) {
            @Cleanup BufferedReader bufferedReader = new BufferedReader(new FileReader(lastSearchFile));
            return Optional.of(bufferedReader.readLine());
        }

        return Optional.empty();
    }

    public void writeLastSearch( String city ) throws IOException {
        @Cleanup BufferedWriter fileWriter = new BufferedWriter(new FileWriter(lastSearchFile));
        fileWriter.write(city);
    }
}
