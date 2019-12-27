package nl.lab.roy.iotbase.handlers.request;

import nl.lab.roy.iotbase.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestHandler {
    private ExecutorService providerPool;
    private BlockingQueue<String[]> queue;
    private ServerSocket server;

    public RequestHandler() throws IOException {
        this.providerPool = Executors.newFixedThreadPool(10);
        this.queue = new LinkedBlockingQueue<>();
        this.server = new ServerSocket(7789);

        new Thread(new Consumer(queue)).start();
    }

    public void handleRequests() {
        try {
            Socket socket = server.accept();

            providerPool.submit(new Provider(queue, socket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
