package net.amitoj.minecraftDiscordChat;

import net.amitoj.minecraftDiscordChat.commands.CommandMinecraftDiscordChat;
import net.amitoj.minecraftDiscordChat.discord.DiscordClient;
import net.amitoj.minecraftDiscordChat.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;
import static net.amitoj.minecraftDiscordChat.util.Util.*;


public final class MinecraftDiscordChat extends JavaPlugin {
    DiscordClient discordClient;

    boolean isEnabled = GetIsEnabled();
    String chatWebhookUrl = GetConfig("chat_webhook_url");
    String serverEventsWebhookUrl = GetConfig("events_webhook_url");
    String serverName = GetConfig("server_name");
    String serverIcon = GetConfig("server_icon");
    String discordToken = GetConfig("discord_token");


    @Override
    public void onEnable() {
        InitialisePlugin();

        PlayerChatListener playerChatListener = new PlayerChatListener(isEnabled, chatWebhookUrl);
        PlayerJoinListener playerJoinListener = new PlayerJoinListener(isEnabled,serverEventsWebhookUrl,serverName,serverIcon);
        PlayerQuitListener playerQuitListener = new PlayerQuitListener(isEnabled,serverEventsWebhookUrl,serverName,serverIcon);
        PlayerDeathListener playerDeathListener = new PlayerDeathListener(isEnabled,serverEventsWebhookUrl,serverName,serverIcon);

        getServer().getPluginManager().registerEvents(playerChatListener, this);
        getServer().getPluginManager().registerEvents(playerJoinListener, this);
        getServer().getPluginManager().registerEvents(playerQuitListener, this);
        getServer().getPluginManager().registerEvents(playerDeathListener, this);

        sendServerStartStopMessage(serverEventsWebhookUrl, serverName, serverIcon, "start");

        discordClient = new DiscordClient(discordToken);

        this.getCommand("minecraftdiscordchat").setExecutor(new CommandMinecraftDiscordChat());
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        discordClient.shutdown();
        sendServerStartStopMessage(serverEventsWebhookUrl, serverName, serverIcon, "stop");
    }
}
