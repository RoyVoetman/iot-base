package nl.lab.roy.iotbase;

import nl.lab.roy.iotbase.handlers.data.DataHandler;
import nl.lab.roy.iotbase.handlers.request.RequestHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static final String CLIENTS = "config.xml";

    public static Properties config;

    public static void main(String[] args) {
        Main.config = loadConfig();

        new DataHandler();
        new RequestHandler();
    }

    public static Properties loadConfig() {
        Properties config = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream(CLIENTS);
        } catch (FileNotFoundException ex) {
            System.err.println("Couldn't load config file: config.xml");
        }

        try {
            config.load(is);
        } catch (IOException ex) {
            System.err.println("Config file: app.config contains a malformed Unicode escape sequence.");
        }

        return config;
    }
}
