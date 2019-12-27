package nl.lab.roy.iotbase.handlers.data;

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
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}