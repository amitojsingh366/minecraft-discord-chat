package net.amitoj.minecraftDiscordChat;

import net.amitoj.minecraftDiscordChat.commands.CommandCoordinates;
import net.amitoj.minecraftDiscordChat.commands.CommandMinecraftDiscordChat;
import net.amitoj.minecraftDiscordChat.discord.DiscordClient;
import net.amitoj.minecraftDiscordChat.listeners.*;
import net.amitoj.minecraftDiscordChat.util.Config;
import net.amitoj.minecraftDiscordChat.util.Updater;
import org.bukkit.plugin.java.JavaPlugin;

import static net.amitoj.minecraftDiscordChat.util.Util.*;


public final class MinecraftDiscordChat extends JavaPlugin {
    public DiscordClient discordClient;
    public Config config = new Config(this);
    public Updater updater = new Updater(this);

    @Override
    public void onEnable() {
        PlayerChatListener playerChatListener = new PlayerChatListener(this);
        PlayerJoinListener playerJoinListener = new PlayerJoinListener(this);
        PlayerQuitListener playerQuitListener = new PlayerQuitListener(this);
        PlayerDeathListener playerDeathListener = new PlayerDeathListener(this);

        getServer().getPluginManager().registerEvents(playerChatListener, this);
        getServer().getPluginManager().registerEvents(playerJoinListener, this);
        getServer().getPluginManager().registerEvents(playerQuitListener, this);
        getServer().getPluginManager().registerEvents(playerDeathListener, this);

        sendServerStartStopMessage(config, "start");

        discordClient = new DiscordClient(config);

        this.getCommand("minecraftdiscordchat").setExecutor(new CommandMinecraftDiscordChat(this));
        this.getCommand("coordinates").setExecutor(new CommandCoordinates(this));
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        discordClient.shutdown();
        sendServerStartStopMessage(config, "stop");
    }
}
