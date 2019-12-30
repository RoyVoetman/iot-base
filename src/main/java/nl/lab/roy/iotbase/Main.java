package nl.lab.roy.iotbase;

import nl.lab.roy.iotbase.handlers.data.DataHandler;
import nl.lab.roy.iotbase.handlers.request.RequestHandler;

public class Main {
    public static final Config config = new Config( "config.json");

    public static void main(String[] args) {
        System.out.println("Starting Data handler...");
        new DataHandler();
        System.out.println("Starting Request handler...");
        new RequestHandler();
        System.out.println("All services started :)");
    }
}
