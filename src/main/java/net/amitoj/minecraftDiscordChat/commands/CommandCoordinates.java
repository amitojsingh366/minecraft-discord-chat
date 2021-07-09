package net.amitoj.minecraftDiscordChat.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCoordinates implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        player.chat("My current coordinates are: " +
                ChatColor.BOLD + ChatColor.LIGHT_PURPLE + "X:" + player.getLocation().getBlockX()
                + " Y:" + player.getLocation().getBlockY()
                + " Z:" + player.getLocation().getBlockZ()
        );
        return true;
    }
}
