package net.amitoj.minecraftDiscordChat;

import net.amitoj.minecraftDiscordChat.commands.CommandMinecraftDiscordChat;
import net.amitoj.minecraftDiscordChat.listeners.PlayerChatListener;
import net.amitoj.minecraftDiscordChat.listeners.PlayerJoinListener;
import net.amitoj.minecraftDiscordChat.listeners.PlayerQuitListener;
import org.bukkit.plugin.java.JavaPlugin;

import static net.amitoj.minecraftDiscordChat.util.Util.*;


public final class MinecraftDiscordChat extends JavaPlugin {

    @Override
    public void onEnable() {
        InitialisePlugin();

        boolean isEnabled = GetIsEnabled();
        String chatWebhookUrl = GetChatWebhookUrl();
        String serverEventsWebhookUrl = GetServerEventsWebhookUrl();
        String serverName = GetServerName();
        String serverIcon = GetServerIcon();

        PlayerChatListener playerChatListener = new PlayerChatListener();
        playerChatListener.set_enabled(isEnabled);
        playerChatListener.set_webhookUrl(chatWebhookUrl);

        PlayerJoinListener playerJoinListener = new PlayerJoinListener();
        playerJoinListener.set_enabled(isEnabled);
        playerJoinListener.set_webhookUrl(serverEventsWebhookUrl);
        playerJoinListener.set_serverName(serverName);
        playerJoinListener.set_serverIcon(serverIcon);

        PlayerQuitListener playerQuitListener = new PlayerQuitListener();
        playerQuitListener.set_enabled(isEnabled);
        playerQuitListener.set_webhookUrl(serverEventsWebhookUrl);
        playerQuitListener.set_serverName(serverName);
        playerQuitListener.set_serverIcon(serverIcon);

        getServer().getPluginManager().registerEvents(playerChatListener, this);
        getServer().getPluginManager().registerEvents(playerJoinListener, this);
        getServer().getPluginManager().registerEvents(playerQuitListener, this);

        this.getCommand("minecraftdiscordchat").setExecutor(new CommandMinecraftDiscordChat());
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
