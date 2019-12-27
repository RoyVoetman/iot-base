package nl.lab.roy.iotbase.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

class Consumer implements Runnable {
    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public void run() {
        String item;

        while (true) {
            try {
                while ((item = this.queue.poll(10, TimeUnit.MILLISECONDS)) != null) {
                    System.err.println(item);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}