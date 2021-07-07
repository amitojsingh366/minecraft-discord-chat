package net.amitoj.minecraftDiscordChat;

import net.amitoj.minecraftDiscordChat.commands.CommandMinecraftDiscordChat;
import net.amitoj.minecraftDiscordChat.listeners.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

import static net.amitoj.minecraftDiscordChat.util.Util.*;


public final class MinecraftDiscordChat extends JavaPlugin {
    JDA jda;
    DiscordChatListener discordChatListener;

    boolean isEnabled = GetIsEnabled();
    String chatWebhookUrl = GetConfig("chat_webhook_url");
    String serverEventsWebhookUrl = GetConfig("events_webhook_url");
    String serverName = GetConfig("server_name");
    String serverIcon = GetConfig("server_icon");
    String discordToken = GetConfig("discord_token");
    String channelID = GetConfig("channel_id");

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
        sendServerStartStopMessage(serverEventsWebhookUrl, serverName, serverIcon, "stop");
    }
}
