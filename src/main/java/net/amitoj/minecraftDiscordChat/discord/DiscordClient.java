package net.amitoj.minecraftDiscordChat.discord;

import net.amitoj.minecraftDiscordChat.discord.listeners.ReadyListener;
import net.amitoj.minecraftDiscordChat.discord.listeners.SlashCommandListener;
import net.amitoj.minecraftDiscordChat.discord.listeners.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import static net.amitoj.minecraftDiscordChat.util.Util.GetConfig;

public class DiscordClient extends ListenerAdapter {
    JDA jda;
    MessageListener messageListener;
    String channelID = GetConfig("channel_id");
    String guildID = GetConfig("guild_id");


    public DiscordClient(String token){
        JDABuilder builder = JDABuilder.createDefault(token);
        builder.setActivity(Activity.watching("Your every move"));

        try {
            messageListener = new MessageListener(channelID);
            builder.addEventListeners(new ReadyListener());
            builder.addEventListeners(new SlashCommandListener(guildID));
            builder.addEventListeners(messageListener);

            jda = builder.build();
            jda.awaitReady();

            jda.getGuildById(guildID).upsertCommand("list", "List all the online people on your minecraft server")
                    .queue();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown(){
        jda.shutdown();
    }
}
