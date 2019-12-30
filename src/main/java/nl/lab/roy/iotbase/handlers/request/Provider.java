package nl.lab.roy.iotbase.handlers.request;

import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.PusherEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.concurrent.BlockingQueue;

public class Provider implements PrivateChannelEventListener {
    private BlockingQueue<String[]> queue;

    public Provider(BlockingQueue<String[]> queue) {
        this.queue = queue;
    }

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
}
