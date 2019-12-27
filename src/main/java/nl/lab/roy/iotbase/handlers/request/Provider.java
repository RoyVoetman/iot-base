package nl.lab.roy.iotbase.handlers.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Provider implements Runnable {
    private Socket socket;
    private BlockingQueue<String[]> queue;

    public Provider(BlockingQueue<String[]> queue, Socket socket) {
        this.socket = socket;
        this.queue = queue;
    }

    public void run() {
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            while ((line = bufferedReader.readLine()) != null) {
                String[] request = {"192.168.2.25", line};
                this.queue.add(request);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

