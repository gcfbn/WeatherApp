package app.fileOperations;

import lombok.Cleanup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TxtReader {

    public static String readLine(File file) throws IOException{
        @Cleanup BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.readLine();
    }

}
