package nl.lab.roy.iotbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static Properties config;

    public static void load(String filepath) {
        Properties config = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream(filepath);
        } catch (FileNotFoundException ex) {
            System.err.println("Couldn't load config file: config.xml");
        }

        try {
            config.load(is);
        } catch (IOException ex) {
            System.err.println("Config file: app.config contains a malformed Unicode escape sequence.");
        }

        Config.config = config;
    }
}
