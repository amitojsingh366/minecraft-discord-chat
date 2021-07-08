package net.amitoj.minecraftDiscordChat.commands;

import net.amitoj.minecraftDiscordChat.util.Config;
import net.amitoj.minecraftDiscordChat.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMinecraftDiscordChat implements CommandExecutor {
    Config _config;

    public CommandMinecraftDiscordChat(Config config) {
        this._config = config;
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args[0]) {
            case "on":
            case "enable":
                _config.setEnabled(true);
                return true;
            case "off":
            case "disable":
                _config.setEnabled(false);
                return true;
            default:
                return false;
        }
    }
}
