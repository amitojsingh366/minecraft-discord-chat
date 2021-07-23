package net.amitoj.minecraftDiscordChat.discord.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Date;

public class Stats {
    public Stats(SlashCommandEvent event) {
        final long duration = ManagementFactory.getRuntimeMXBean().getUptime();
        final long years = duration / 31104000000L;
        final long months = duration / 2592000000L % 12;
        final long days = duration / 86400000L % 30;
        final long hours = duration / 3600000L % 24;
        final long minutes = duration / 60000L % 60;
        final long seconds = duration / 1000L % 60;

        String uptime = (years == 0 ? "" : years + " years, ") + (months == 0 ? "" : months + " months, ") + (days == 0 ? "" : days + " days, ") + (hours == 0 ? "" : hours + " hours, ")
                + (minutes == 0 ? "" : minutes + " minutes, ") + (seconds == 0 ? "" : seconds + " seconds");

        String icon_url = "https://packpng.com/static/pack.png";

        File icon = new File("server-icon.png");
        if (icon.exists()) icon_url = "attachment://server-icon.png";

        EmbedBuilder embed = new EmbedBuilder();

        embed.setDescription("\uD83D\uDFE2 Server is online")
                .addField("Uptime", uptime, false)
                .addField("Server MOTD", Bukkit.getMotd(), false)
                .addField("Minecraft Version", Bukkit.getMinecraftVersion(), false)
                .addField("Online Players", Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(), false)
                .setTimestamp(new Date().toInstant())
                .setColor(0x00AA00)
                .setThumbnail(icon_url);

        MessageBuilder message = new MessageBuilder().setEmbeds(embed.build());

        if (icon.exists()) {
            event.reply(message.build())
                    .addFile(icon, "server-icon.png")
                    .queue();
        } else {
            event.reply(message.build())
                    .queue();
        }

    }
}
