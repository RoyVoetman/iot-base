package nl.lab.roy.iotbase.server;

import java.net.*;
import java.io.*;
import java.util.concurrent.BlockingQueue;

class Provider implements Runnable {
    private Socket connection;
    private BlockingQueue<String> queue;

    public Provider(Socket connection, BlockingQueue<String> queue) {
        this.connection = connection;
        this.queue = queue;
    }

    public void run() {
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                this.queue.add(line);
            }

            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

