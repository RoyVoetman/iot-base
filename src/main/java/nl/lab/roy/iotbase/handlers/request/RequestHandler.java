package nl.lab.roy.iotbase.handlers.request;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.util.HttpAuthorizer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
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
        HttpAuthorizer authorizer = new HttpAuthorizer("http://php_tcp.test/api/broadcasting/auth");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer VQwdTtxQJppOGpkY5Dld8hjuOOU4ojVTVfJ2VnRP5jYzys7NqWgXLb9vY5HsZB52zYLwpuoNPGNBuwSP");
        authorizer.setHeaders(headers);
        PusherOptions options = new PusherOptions();
        options.setCluster("eu");
        options.setEncrypted(true);
        options.setAuthorizer(authorizer);
        Pusher pusher = new Pusher("2850701da12e978763d8", options);

        PrivateChannel channel = pusher.subscribePrivate("private-requests");

        channel.bind("update.request", new PrivateChannelEventListener() {
            public void onEvent(PusherEvent pusherEvent) {
                String data = pusherEvent.getData();
                JSONParser jsonParser = new JSONParser();

                try {
                    JSONObject json = (JSONObject) jsonParser.parse(data);
                    String ip = (String) json.get("ip");
                    String payload = (String) json.get("payload");

                    queue.put(new String[]{ip, payload});
                } catch (ParseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            public void onSubscriptionSucceeded(String s) {
                System.err.println(s);
            }

            public void onAuthenticationFailure(String s, Exception e) {
                System.err.println(s);
                e.printStackTrace();
            }
        });
        pusher.connect();
    }
}
