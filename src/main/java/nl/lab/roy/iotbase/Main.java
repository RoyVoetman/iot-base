package nl.lab.roy.iotbase;

import nl.lab.roy.iotbase.handlers.data.DataHandler;
import nl.lab.roy.iotbase.handlers.request.RequestHandler;

import java.io.IOException;

public class Main {
    public static final String CONFIG_FILE = "config.xml";

    public static void main(String[] args) throws IOException {
        Config.load(CONFIG_FILE);

        DataHandler dataHandler = new DataHandler();
        RequestHandler requestHandler = new RequestHandler();

        dataHandler.startListening();

        while (true) {
            requestHandler.handleRequests();
        }
    }
}
