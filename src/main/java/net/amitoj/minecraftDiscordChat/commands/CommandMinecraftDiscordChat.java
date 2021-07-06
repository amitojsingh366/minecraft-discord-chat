package net.amitoj.minecraftDiscordChat.commands;

import net.amitoj.minecraftDiscordChat.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMinecraftDiscordChat implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args[0]){
            case "on":
            case "enable":
                Util.EditData("enabled","true","bool");
                return true;
            case "off":
            case "disable":
                Util.EditData("enabled","false","bool");
                return true;
            default:
                return false;
        }
    }
}
