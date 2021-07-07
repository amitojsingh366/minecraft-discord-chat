package net.amitoj.minecraftDiscordChat;

import net.amitoj.minecraftDiscordChat.commands.CommandMinecraftDiscordChat;
import net.amitoj.minecraftDiscordChat.listeners.DiscordChatListener;
import net.amitoj.minecraftDiscordChat.listeners.PlayerChatListener;
import net.amitoj.minecraftDiscordChat.listeners.PlayerJoinListener;
import net.amitoj.minecraftDiscordChat.listeners.PlayerQuitListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

import static net.amitoj.minecraftDiscordChat.util.Util.*;


public final class MinecraftDiscordChat extends JavaPlugin {
    JDA jda;
    DiscordChatListener discordChatListener;
    @Override
    public void onEnable() {
        InitialisePlugin();

        boolean isEnabled = GetIsEnabled();
        String chatWebhookUrl = GetConfig("chat_webhook_url");
        String serverEventsWebhookUrl = GetConfig("events_webhook_url");
        String serverName = GetConfig("server_name");
        String serverIcon = GetConfig("server_icon");
        String discordToken = GetConfig("discord_token");
        String channelID = GetConfig("channel_id");

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

        discordChatListener = new DiscordChatListener();
        discordChatListener.set_channelID(channelID);

        JDABuilder builder = JDABuilder.createDefault(discordToken);
        builder.setActivity(Activity.watching("You"));

        try {
            jda = builder.build();
            jda.addEventListener(discordChatListener);
        } catch (LoginException e) {
            e.printStackTrace();
        }

        this.getCommand("minecraftdiscordchat").setExecutor(new CommandMinecraftDiscordChat());
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        jda.removeEventListener(discordChatListener);
    }
}
