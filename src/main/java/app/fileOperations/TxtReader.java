package app.fileOperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TxtReader {

    public static String readLine(File file) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.readLine();
    }

}
