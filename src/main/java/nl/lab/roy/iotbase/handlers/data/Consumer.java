package nl.lab.roy.iotbase.handlers.data;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        String item;

        while (true) {
            try {
                while ((item = this.queue.poll(10, TimeUnit.MILLISECONDS)) != null) {
                    System.out.println(item);

                    try {
                        URL url = new URL("http://php_tcp.test/api/update/read-unit/192.168.2.25/"+ item);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();

                        con.setRequestMethod("PUT");
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