package net.amitoj.minecraftDiscordChat;

import net.amitoj.minecraftDiscordChat.commands.CommandMinecraftDiscordChat;
import net.amitoj.minecraftDiscordChat.discord.DiscordClient;
import net.amitoj.minecraftDiscordChat.listeners.*;
import net.amitoj.minecraftDiscordChat.util.Config;
import org.bukkit.plugin.java.JavaPlugin;

import static net.amitoj.minecraftDiscordChat.util.Util.*;


public final class MinecraftDiscordChat extends JavaPlugin {
    DiscordClient discordClient;
    Config config = new Config();

    @Override
    public void onEnable() {
        PlayerChatListener playerChatListener = new PlayerChatListener(config);
        PlayerJoinListener playerJoinListener = new PlayerJoinListener(config);
        PlayerQuitListener playerQuitListener = new PlayerQuitListener(config);
        PlayerDeathListener playerDeathListener = new PlayerDeathListener(config);

        getServer().getPluginManager().registerEvents(playerChatListener, this);
        getServer().getPluginManager().registerEvents(playerJoinListener, this);
        getServer().getPluginManager().registerEvents(playerQuitListener, this);
        getServer().getPluginManager().registerEvents(playerDeathListener, this);

        sendServerStartStopMessage(config, "start");

        discordClient = new DiscordClient(config);

        this.getCommand("minecraftdiscordchat").setExecutor(new CommandMinecraftDiscordChat(config));
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        discordClient.shutdown();
        sendServerStartStopMessage(config, "stop");
    }
}
