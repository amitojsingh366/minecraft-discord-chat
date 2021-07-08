package net.amitoj.minecraftDiscordChat.listeners;


import net.amitoj.minecraftDiscordChat.util.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class PlayerChatListener implements Listener {
    private boolean _enabled = true;
    private String _webhookUrl;

    public PlayerChatListener(Config config) {
        this._enabled = config.enabled;
        this._webhookUrl = config.chatWebhookUrl;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if(_enabled){
            HttpURLConnection con = null;
            try {
                JSONObject postData = new JSONObject();
                postData.put("content", event.getMessage());
                postData.put("username", event.getPlayer().getPlayerProfile().getName());
                postData.put("avatar_url", "https://mc-heads.net/avatar/" + event.getPlayer().getPlayerProfile().getName());

                con = (HttpURLConnection) new URL(_webhookUrl).openConnection();
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


    public String get_webhookUrl() {
        return _webhookUrl;
    }

    public void set_webhookUrl(String _webhookUrl) {
        this._webhookUrl = _webhookUrl;
    }

    public void set_enabled(boolean _enabled) {
        this._enabled = _enabled;
    }
}
