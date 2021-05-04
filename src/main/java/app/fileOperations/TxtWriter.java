package app.fileOperations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TxtWriter {

    public static void writeLine(File file, String line) throws IOException {

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(line);
    }
}
