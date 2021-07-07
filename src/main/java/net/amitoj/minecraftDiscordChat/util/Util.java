package net.amitoj.minecraftDiscordChat.util;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Util {

    private static String _configPath = "./plugins/minecraftdiscordchat/config.json";

    public static void InitialisePlugin() {
        JSONObject config = new JSONObject();
        config.put("enabled", true);
        config.put("chat_webhook_url", "");
        config.put("events_webhook_url", "");
        config.put("server_name", "Minecarft Server");
        config.put("server_icon", "https://packpng.com/static/pack.png");
        config.put("discord_token", "");
        config.put("channel_id", "");

        if(!Files.exists(Paths.get("./plugins/minecraftdiscordchat/"))){
            File file = new File("./plugins/minecraftdiscordchat/");
            if(file.mkdir()){
                System.out.println("Successfully created plugin folder");
            }
        }
        if(!Files.exists(Paths.get(_configPath))){
            try {
                Files.write(Paths.get(_configPath),config.toJSONString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean GetIsEnabled(){
        JSONObject config = new JSONObject();
        FileReader reader = null;
        try {
            reader = new FileReader(_configPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONParser jsonParser = new JSONParser();
        try {
            config = (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return (boolean) config.get("enabled");
    }


    public static String GetConfig(String key){
        JSONObject config = new JSONObject();
        FileReader reader = null;
        try {
            reader = new FileReader(_configPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONParser jsonParser = new JSONParser();
        try {
            config = (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return (String) config.get(key);
    }

    public static boolean EditData(String key, String value, String type) {
        JSONObject config = new JSONObject();
        FileReader reader = null;
        try {
            reader = new FileReader(_configPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONParser jsonParser = new JSONParser();
        try {
            config = (JSONObject) jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        File file = new File(_configPath);
        switch (type){
            case "bool":
                config.put(key,Boolean.parseBoolean(value));
                break;
            case "string":
                config.put(key,value);
                break;
            case "integer":
                config.put(key,Integer.parseInt(value));
                break;
            case "long":
                config.put(key,Long.parseLong(value));
                break;
        }
        if(file.delete()){
            if(!Files.exists(Paths.get(_configPath))){
                try {
                    Files.write(Paths.get(_configPath),config.toJSONString().getBytes());
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void sendServerStartStopMessage(String webhookUrl, String serverName, String serverIcon, String event){
        HttpURLConnection con = null;
        try {
            JSONObject postData = new JSONObject();
            postData.put("content", "");

            JSONArray embeds = new JSONArray();
            JSONObject embed = new JSONObject();

            if(event.equals("start")){
                embed.put("title", "Server Started");
                embed.put("description", "The server has started");
                embed.put("color", 65280);
            }else{
                embed.put("title", "Server Shutting Down");
                embed.put("description", "The minecraft server is shutting down");
                embed.put("color", 16711680);
            }

            embeds.add(embed);

            postData.put("embeds", embeds);
            postData.put("username", serverName);
            postData.put("avatar_url", serverIcon);

            con = (HttpURLConnection) new URL(webhookUrl).openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData.toJSONString().getBytes());
            }
            con.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
