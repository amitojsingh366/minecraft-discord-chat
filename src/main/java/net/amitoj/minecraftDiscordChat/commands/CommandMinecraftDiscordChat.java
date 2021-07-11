package net.amitoj.minecraftDiscordChat.commands;

import net.amitoj.minecraftDiscordChat.MinecraftDiscordChat;
import net.amitoj.minecraftDiscordChat.util.Config;
import net.amitoj.minecraftDiscordChat.util.Updater;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMinecraftDiscordChat implements CommandExecutor {
    Config _config;
    Updater _updater;

    public CommandMinecraftDiscordChat(MinecraftDiscordChat plugin) {
        this._config = plugin.config;
        this._updater = plugin.updater;
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
            case "update":
                _updater.checkForUpdates();
                _updater.tryUpdating();
                return true;
            default:
                return false;
        }
    }
}
