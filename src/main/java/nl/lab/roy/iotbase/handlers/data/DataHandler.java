package nl.lab.roy.iotbase.handlers.data;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class DataHandler {
    private ExecutorService providerPool;
    private BlockingQueue<String> queue;

    public DataHandler() {
        this.providerPool = Executors.newFixedThreadPool(10);
        this.queue = new LinkedBlockingQueue<>();

        new Thread(new Consumer(queue)).start();

        createProviders();
    }

    public void createProviders() {
        String[] ips = {"192.168.2.30"};

        for(String ip: ips) {
            try {
                providerPool.execute(new Provider(queue, ip));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
