package nl.lab.roy.iotbase.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.*;

public class Server {
    public static final String CONFIG_FILE = "app.config";
    public static final int PORT = 7789;

    private ExecutorService providerPool;
    private ExecutorService consumerPool;
    private BlockingQueue<String> queue;

    public static Properties config;

    public Server() {
        this.providerPool = Executors.newFixedThreadPool(10);
        this.consumerPool = Executors.newFixedThreadPool(10);
        this.queue = new LinkedBlockingQueue<>();

        Server.config = loadConfig();
    }

    public Properties loadConfig() {
        Properties config = new Properties();
        InputStream is = null;

        try {
            is = new FileInputStream(CONFIG_FILE);
        } catch (FileNotFoundException ex) {
            System.err.println("Couldn't load config file: app.config");
        }

        try {
            config.load(is);
        } catch (IOException ex) {
            System.err.println("Config file: app.config contains a malformed Unicode escape sequence.");
        }

        return config;
    }

    public void start() {
        createLogger();

        createConsumers();

        handleRequests();
    }

    public void createLogger() {
        new Thread(new Logger(providerPool, queue)).start();
    }

    public void createConsumers() {
        for(int i = 0; i < 10; ++i) {
            consumerPool.execute(new Consumer(queue));
        }
    }

    public void handleRequests() {
        Socket connection;
        ServerSocket server;

        try {
            server = new ServerSocket(PORT);

            while(true) {
                connection = server.accept();

                // Add the new connection to the connection pool
                providerPool.submit(new Provider(connection, queue));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
