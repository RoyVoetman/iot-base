package nl.lab.roy.iotbase;

import nl.lab.roy.iotbase.server.Server;

public class Runner {

    /**
     * Runner method
     *
     * @param args
     */
    public static void main(String[] args) {
        (new Server()).start();
    }
}
