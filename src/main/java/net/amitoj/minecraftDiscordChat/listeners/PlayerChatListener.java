package net.amitoj.minecraftDiscordChat.listeners;


import net.amitoj.minecraftDiscordChat.MinecraftDiscordChat;
import net.amitoj.minecraftDiscordChat.util.Config;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;

import static net.amitoj.minecraftDiscordChat.util.Util.sendWH;


public class PlayerChatListener implements Listener {
    private boolean _enabled = true;
    private String _webhookUrl;

    public PlayerChatListener(MinecraftDiscordChat plugin) {
        this._enabled = plugin.config.enabled;
        this._webhookUrl = plugin.config.chatWebhookUrl;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (_enabled) {
            JSONObject postData = new JSONObject();
            postData.put("content", ChatColor.stripColor(event.getMessage()));
            postData.put("username", event.getPlayer().getPlayerProfile().getName());
            postData.put("avatar_url", "https://mc-heads.net/avatar/" + event.getPlayer().getPlayerProfile().getName());

            sendWH(postData, _webhookUrl);
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
