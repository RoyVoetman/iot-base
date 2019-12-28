package nl.lab.roy.iotbase;

import nl.lab.roy.iotbase.handlers.data.DataHandler;
import nl.lab.roy.iotbase.handlers.request.RequestHandler;

public class Main {
    public static final String CONFIG_FILE = "config.xml";

    public static void main(String[] args) {
        Config.load(CONFIG_FILE);

        //new DataHandler();
        new RequestHandler();
    }
}
