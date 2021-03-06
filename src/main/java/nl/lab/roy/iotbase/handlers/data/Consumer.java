package nl.lab.roy.iotbase.handlers.data;

import nl.lab.roy.iotbase.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private BlockingQueue<String[]> queue;

    public Consumer(BlockingQueue<String[]> queue) {
        this.queue = queue;
    }

    public void run() {
        String[] item;

        while (true) {
            try {
                while ((item = this.queue.poll(10, TimeUnit.MILLISECONDS)) != null) {
                    try {
                        URL url = new URL(Main.config.apiUrl + "/webhook/update-unit/" + item[0] + "/" + item[1]);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();

                        con.setRequestMethod("PUT");
                        con.setRequestProperty("Authorization", "Bearer " + Main.config.apiToken);
                        con.connect();
                        con.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}