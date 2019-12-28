package nl.lab.roy.iotbase.handlers.data;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class DataHandler {
    private ExecutorService providerPool;
    private BlockingQueue<String> queue;
    private String[] ips = {"192.168.2.35"};

    public DataHandler() {
        this.providerPool = Executors.newFixedThreadPool(ips.length);
        this.queue = new LinkedBlockingQueue<>();

        new Thread(new Consumer(queue)).start();

        this.createProviders();
    }

    public void createProviders() {
        for(String ip: ips) {
            try {
                providerPool.execute(new Provider(queue, ip));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
