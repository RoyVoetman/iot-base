package nl.lab.roy.iotbase.handlers.data;

import nl.lab.roy.iotbase.Main;
import nl.lab.roy.iotbase.handlers.Handler;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataHandler implements Handler {
    private BlockingQueue<String[]> queue;

    public DataHandler() {
        this.queue = new LinkedBlockingQueue<>();

        new Thread(new Consumer(queue)).start();

        this.createProviders();
    }

    public void createProviders() {
        for (Object client : Main.config.clients) {
            try {
                String ip = (String) client;
                new Thread(new Provider(queue, ip)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
