package net.amitoj.minecraftDiscordChat.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PlayerQuitListener implements Listener {
    private boolean _enabled = true;
    private String _webhookUrl;
    private String _serverName;
    private String _serverIcon;

    public PlayerQuitListener(boolean enabled, String webhookUrl, String serverName, String serverIcon) {
        this._enabled = enabled;
        this._webhookUrl = webhookUrl;
        this._serverName = serverName;
        this._serverIcon = serverIcon;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (_enabled) {
            HttpURLConnection con = null;
            try {
                JSONObject postData = new JSONObject();
                postData.put("content", "");


                JSONArray embeds = new JSONArray();
                JSONObject embed = new JSONObject();
                JSONObject thumbnail = new JSONObject();
                thumbnail.put("url", "https://mc-heads.net/avatar/" + event.getPlayer().getPlayerProfile().getName());

                embed.put("title", "Player Left");
                embed.put("description", event.getPlayer().getPlayerProfile().getName() + " has left the server");
                embed.put("color", 16711680);
                embed.put("thumbnail", thumbnail);
                embeds.add(embed);

                postData.put("embeds", embeds);
                postData.put("username", _serverName);
                postData.put("avatar_url", _serverIcon);

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

    public void set_enabled(boolean _enabled) {
        this._enabled = _enabled;
    }

    public void set_webhookUrl(String _webhookUrl) {
        this._webhookUrl = _webhookUrl;
    }

    public void set_serverName(String _serverName) {
        this._serverName = _serverName;
    }

    public void set_serverIcon(String _serverIcon) {
        this._serverIcon = _serverIcon;
    }

}
