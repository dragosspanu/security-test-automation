package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
    public static String readTextFileAsString(String location) {
        BufferedReader objReader = null;
        String entireText = "";
        try {
            String strCurrentLine;
            objReader = new BufferedReader(new FileReader(location));
            while ((strCurrentLine = objReader.readLine()) != null) {
                entireText = entireText.concat(strCurrentLine).concat("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objReader != null)
                    objReader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return entireText;
    }
}