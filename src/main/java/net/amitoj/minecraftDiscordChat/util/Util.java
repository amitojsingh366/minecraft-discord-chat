package net.amitoj.minecraftDiscordChat.util;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public static String GetChatWebhookUrl(){
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
        return (String) config.get("chat_webhook_url");
    }

    public static String GetServerEventsWebhookUrl(){
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
        return (String) config.get("events_webhook_url");
    }

    public static String GetServerName(){
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
        return (String) config.get("server_name");
    }

    public static String GetServerIcon(){
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
        return (String) config.get("server_icon");
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

}
