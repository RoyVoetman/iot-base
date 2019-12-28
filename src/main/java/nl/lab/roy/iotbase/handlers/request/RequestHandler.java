package nl.lab.roy.iotbase.handlers.request;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PusherEvent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestHandler {
    private BlockingQueue<String[]> queue;

    public RequestHandler() {
        this.queue = new LinkedBlockingQueue<>();

        new Thread(new Consumer(queue)).start();

        this.bindRequestHandler();
    }

    public void bindRequestHandler() {
        PusherOptions options = new PusherOptions();
        options.setCluster("eu");
        Pusher pusher = new Pusher("2850701da12e978763d8", options);

        Channel channel = pusher.subscribe("units");

        channel.bind("App\\Events\\UnitUpdated", (PusherEvent event) -> {
            System.out.println(event.getData());
        });

        pusher.connect();
    }
}
