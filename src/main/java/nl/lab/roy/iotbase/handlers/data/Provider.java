package nl.lab.roy.iotbase.handlers.data;

import java.net.*;
import java.io.*;
import java.util.concurrent.BlockingQueue;

public class Provider implements Runnable {
    private Socket connection;
    private BlockingQueue<String> queue;

    public Provider(BlockingQueue<String> queue, String ip) throws IOException {
        this.connection = new Socket(ip, 8888);
        this.queue = queue;
    }

    public void run() {
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));

            while ((line = bufferedReader.readLine()) != null) {
                this.queue.add(line);
            }

            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

