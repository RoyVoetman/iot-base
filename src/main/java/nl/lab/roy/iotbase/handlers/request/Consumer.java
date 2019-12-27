package nl.lab.roy.iotbase.handlers.request;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private Socket socket;
    private BlockingQueue<String[]> queue;

    public Consumer(BlockingQueue<String[]> queue) {
        this.queue = queue;
    }

    public void run() {
        String[] item;

        while (true) {
            try {
                while ((item = this.queue.poll(10, TimeUnit.MILLISECONDS)) != null) {
                    System.out.println("IP: " + item[0] + " Data: " + item[1]);

                    socket = new Socket(item[0], 8888);
                    PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
                    out.println(item[1]);
                    out.flush();
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}