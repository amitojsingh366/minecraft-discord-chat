package net.amitoj.minecraftDiscordChat.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class DiscordChatListener extends ListenerAdapter {
    private String _channelID = "";

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(!_channelID.equals("")){
            if(event.getChannel().getId().equals(_channelID)){
                if(!event.getMessage().isWebhookMessage()){
                    Bukkit.broadcastMessage(ChatColor.DARK_AQUA + event.getAuthor().getAsTag() + ": " + ChatColor.WHITE + event.getMessage().getContentRaw());
                }
            }
        }
    }

    public void set_channelID(String _channelID) {
        this._channelID = _channelID;
    }
}
