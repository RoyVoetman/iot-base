package nl.lab.roy.iotbase.handlers.data;

import java.net.*;
import java.io.*;
import java.util.concurrent.BlockingQueue;

public class Provider implements Runnable {
    private Socket socket;
    private BlockingQueue<String[]> queue;
    private String ip;

    public Provider(BlockingQueue<String[]> queue, String ip) throws IOException {
        this.ip = ip;
        this.socket = new Socket(ip, 8888);
        this.queue = queue;
    }

    public void run() {
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            while ((line = bufferedReader.readLine()) != null) {
                this.queue.add(new String[]{ip, line});
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

