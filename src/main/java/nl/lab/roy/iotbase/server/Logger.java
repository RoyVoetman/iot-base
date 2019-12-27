package nl.lab.roy.iotbase.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

class Logger implements Runnable {

    ExecutorService providerPool;
    BlockingQueue<String> queue;

    public Logger(ExecutorService providerPool, BlockingQueue<String> queue) {
        this.providerPool = providerPool;
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                System.out.printf("Connections: %d Write Queue Size: %d\n",
                        ((ThreadPoolExecutor) providerPool).getActiveCount(),
                        queue.size()
                );
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}