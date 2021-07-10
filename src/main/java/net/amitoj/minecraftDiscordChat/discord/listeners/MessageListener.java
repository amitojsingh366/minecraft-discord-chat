package net.amitoj.minecraftDiscordChat.discord.listeners;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class MessageListener extends ListenerAdapter {
    private String _channelID = "";

    public MessageListener(String channelID) {
        this._channelID = channelID;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!_channelID.equals("")) {
            if (event.getChannel().getId().equals(_channelID)) {
                if (!event.getMessage().isWebhookMessage()) {
                    StringBuilder attachments = new StringBuilder();
                    boolean isEmpty = event.getMessage().getContentDisplay().equals("");

                    for (Message.Attachment attachment : event.getMessage().getAttachments()) {
                        if (isEmpty) {
                            attachments.append("\n[Sent ");
                        } else {
                            attachments.append("\n[Attached ");
                        }
                        attachments.append(attachment.getContentType()).append("]");
                    }

                    String message = ChatColor.DARK_AQUA + event.getAuthor().getAsTag() + ": " + ChatColor.WHITE + event.getMessage().getContentDisplay();
                    Bukkit.broadcastMessage(message + ChatColor.RED + attachments);
                }
            }
        }
    }

    public void set_channelID(String _channelID) {
        this._channelID = _channelID;
    }
}
