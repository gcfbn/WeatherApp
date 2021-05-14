package app.fileOperations;

import lombok.Cleanup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TxtWriter {

    public static void writeLine(File file, String line) throws IOException {
        @Cleanup BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
        fileWriter.write(line);
    }
}
