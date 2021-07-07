package net.amitoj.minecraftDiscordChat.discord.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class List {
    public List(SlashCommandEvent event) {
        StringBuilder onlinePlayerNames = new StringBuilder();
        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();

        if (onlinePlayers.isEmpty()) {
            event.reply("No one is online at the moment").setEphemeral(true).queue();
            return;
        }

        for (Player player : onlinePlayers) {
            onlinePlayerNames.append(player.getName()).append(", ");
        }

        event.reply("Players online: " + onlinePlayerNames.substring(0, onlinePlayerNames.length() - 2))
                .setEphemeral(true).queue();
    }
}
