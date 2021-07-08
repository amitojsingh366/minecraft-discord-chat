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

public class Config {
    public String configPath = "./plugins/minecraftdiscordchat/config.json";
    public JSONObject defaultConfig = new JSONObject();

    public boolean enabled;
    public String chatWebhookUrl;
    public String eventsWebhookUrl;
    public String serverName;
    public String serverIcon;
    public String discordToken;
    public String channelID;
    public String guildID;

    public Config() {
        setDefaults();
        checkConfigPath();
        checkConfigUpdates();
        getConfig();
    }

    private void getConfig() {
        JSONObject config = new JSONObject();
        FileReader reader = null;
        try {
            reader = new FileReader(configPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONParser jsonParser = new JSONParser();
        try {
            config = (JSONObject) jsonParser.parse(reader);
            reader.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        setEnabled((Boolean) config.get("enabled"));
        setChatWebhookUrl((String) config.get("chat_webhook_url"));
        setEventsWebhookUrl((String) config.get("events_webhook_url"));
        setServerName((String) config.get("server_name"));
        setServerIcon((String) config.get("server_icon"));
        setDiscordToken((String) config.get("discord_token"));
        setChannelID((String) config.get("channel_id"));
        setGuildID((String) config.get("guild_id"));
    }

    private void checkConfigUpdates() {
        JSONObject config = new JSONObject();
        FileReader reader = null;
        try {
            reader = new FileReader(configPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONParser jsonParser = new JSONParser();
        try {
            config = (JSONObject) jsonParser.parse(reader);
            reader.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        System.out.println(0);
        for (Object o : defaultConfig.keySet()) {
            String key = (String) o;
            if (config.get(key) == null) {
                System.out.println("Adding " + key + " to config");
                config.put(key, defaultConfig.get(key));
            }
        }

        File file = new File(configPath);
        if (file.delete()) {
            if (!Files.exists(Paths.get(configPath))) {
                try {
                    Files.write(Paths.get(configPath), config.toJSONString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setConfig() {
        JSONObject config = new JSONObject();
        config.put("enabled", enabled);
        config.put("chat_webhook_url", chatWebhookUrl);
        config.put("events_webhook_url", eventsWebhookUrl);
        config.put("server_name", serverName);
        config.put("server_icon", serverIcon);
        config.put("discord_token", discordToken);
        config.put("channel_id", channelID);
        config.put("guild_id", guildID);

        File file = new File(configPath);
        if (file.delete()) {
            if (!Files.exists(Paths.get(configPath))) {
                try {
                    Files.write(Paths.get(configPath), config.toJSONString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setDefaults() {
        defaultConfig.put("enabled", true);
        defaultConfig.put("chat_webhook_url", "");
        defaultConfig.put("events_webhook_url", "");
        defaultConfig.put("server_name", "Minecarft Server");
        defaultConfig.put("server_icon", "https://packpng.com/static/pack.png");
        defaultConfig.put("discord_token", "");
        defaultConfig.put("channel_id", "");
        defaultConfig.put("guild_id", "");
    }

    private void checkConfigPath() {
        if (!Files.exists(Paths.get("./plugins/minecraftdiscordchat/"))) {
            File file = new File("./plugins/minecraftdiscordchat/");
            if (file.mkdir()) {
                System.out.println("Successfully created plugin folder");
            }
        }
        if (!Files.exists(Paths.get(configPath))) {
            try {
                Files.write(Paths.get(configPath), defaultConfig.toJSONString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        setConfig();
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
        setConfig();
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
        setConfig();
    }

    public void setChatWebhookUrl(String chatWebhookUrl) {
        this.chatWebhookUrl = chatWebhookUrl;
        setConfig();
    }

    public void setServerIcon(String serverIcon) {
        this.serverIcon = serverIcon;
        setConfig();
    }

    public void setDiscordToken(String discordToken) {
        this.discordToken = discordToken;
        setConfig();
    }

    public void setEventsWebhookUrl(String eventsWebhookUrl) {
        this.eventsWebhookUrl = eventsWebhookUrl;
        setConfig();
    }

    public void setGuildID(String guildID) {
        this.guildID = guildID;
        setConfig();
    }
}
