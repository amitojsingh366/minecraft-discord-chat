package net.amitoj.minecraftDiscordChat.discord;

import net.amitoj.minecraftDiscordChat.discord.listeners.ReadyListener;
import net.amitoj.minecraftDiscordChat.discord.listeners.SlashCommandListener;
import net.amitoj.minecraftDiscordChat.discord.listeners.MessageListener;
import net.amitoj.minecraftDiscordChat.util.Config;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class DiscordClient extends ListenerAdapter {
    private Config _config;

    JDA jda;
    MessageListener messageListener;

    public DiscordClient(Config config) {
        this._config = config;

        if (_config.enabled) {
            JDABuilder builder = JDABuilder.createDefault(_config.discordToken);
            builder.setActivity(Activity.watching("Your every move"));

            try {
                messageListener = new MessageListener(_config.channelID);
                builder.addEventListeners(new ReadyListener());
                builder.addEventListeners(new SlashCommandListener(_config.guildID));
                builder.addEventListeners(messageListener);

                jda = builder.build();
                jda.awaitReady();

                jda.getGuildById(_config.guildID).upsertCommand("list", "List all the online people on your minecraft server")
                        .queue();
            } catch (LoginException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        if (jda != null) {
            jda.shutdown();
        }
    }
}
