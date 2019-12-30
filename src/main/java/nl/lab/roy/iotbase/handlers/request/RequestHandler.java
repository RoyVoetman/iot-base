package nl.lab.roy.iotbase.handlers.request;

import com.pusher.client.Authorizer;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.util.HttpAuthorizer;
import nl.lab.roy.iotbase.Main;
import nl.lab.roy.iotbase.handlers.Handler;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestHandler implements Handler {
    private BlockingQueue<String[]> queue;

    public RequestHandler() {
        this.queue = new LinkedBlockingQueue<>();

        new Thread(new Consumer(queue)).start();

        this.bindProvider();
    }

    public void bindProvider() {
        Pusher pusher = new Pusher(Main.config.pusherAppKey, getPusherOptions());

        PrivateChannel channel = pusher.subscribePrivate("private-requests");
        channel.bind("update.request", new Provider(queue));
        pusher.connect();
    }

    private PusherOptions getPusherOptions() {
        PusherOptions options = new PusherOptions();
        options.setCluster(Main.config.pusherCluster);
        options.setEncrypted(true);
        options.setAuthorizer(getPusherAuthorizer());

        return options;
    }

    private Authorizer getPusherAuthorizer() {
        HttpAuthorizer authorizer = new HttpAuthorizer(Main.config.apiUrl + Main.config.pusherAuth);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + Main.config.apiToken);
        authorizer.setHeaders(headers);

        return authorizer;
    }
}
