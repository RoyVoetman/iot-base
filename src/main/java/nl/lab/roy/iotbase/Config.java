package nl.lab.roy.iotbase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Config {
    public String apiUrl;
    public String apiToken;
    public String pusherAppKey;
    public String pusherCluster;
    public String pusherAuth;
    public JSONArray clients;

    public Config(String filepath) {
        JSONParser parser = new JSONParser();

        try {
            Reader reader = new FileReader(filepath);
            JSONObject config = (JSONObject) parser.parse(reader);

            this.clients = (JSONArray) config.get("clients");

            JSONObject api = (JSONObject) config.get("api");
            this.apiUrl = (String) api.get("url");
            this.apiToken = (String) api.get("token");

            JSONObject pusher = (JSONObject) config.get("pusher");
            this.pusherAppKey = (String) pusher.get("app-key");
            this.pusherCluster = (String) pusher.get("cluster");
            this.pusherAuth = (String) pusher.get("auth");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
