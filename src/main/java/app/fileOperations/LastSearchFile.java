package app.fileOperations;

import java.io.File;
import java.io.IOException;

public class LastSearchFile {
    File lastSearchFile;

    public LastSearchFile( String pathName ) {
        this.lastSearchFile = new File(pathName);
    }

    public String createOrReadLastSearchedCity() throws IOException {
        // create file if it doesn't exist
        // in other case, try to read from file

        if (lastSearchFile.exists()) {
            return TxtReader.readLine(lastSearchFile);
        }

        return null;
    }

    public void writeLastSearch( String city ) throws IOException {
        TxtWriter.writeLine(lastSearchFile, city);
    }
}
