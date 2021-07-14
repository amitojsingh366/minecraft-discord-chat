package net.amitoj.minecraftDiscordChat.commands;

import net.amitoj.minecraftDiscordChat.MinecraftDiscordChat;
import net.amitoj.minecraftDiscordChat.util.Config;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import static net.amitoj.minecraftDiscordChat.util.Util.sendWH;

public class CommandCoordinates implements CommandExecutor {
    private Config _config;

    public CommandCoordinates(MinecraftDiscordChat plugin) {
        this._config = plugin.config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        String coordinates = "X:" + player.getLocation().getBlockX()
                + " Y:" + player.getLocation().getBlockY()
                + " Z:" + player.getLocation().getBlockZ();

        String dimension = "Overworld";
        String location = "My current";

        if (args != null) {
            if (String.join(",", args) != "") {
                location = String.join(",", args);
            }
        }

        switch (player.getWorld().getEnvironment()) {
            case NORMAL:
                dimension = "Overworld";
                break;
            case NETHER:
                dimension = "Nether";
                break;
            case THE_END:
                dimension = "End";
                break;
        }

        TextComponent message = new TextComponent("<" + player.getName() + "> " + location + " coordinates are: " +
                ChatColor.YELLOW + dimension + " " +
                ChatColor.BOLD + ChatColor.LIGHT_PURPLE + coordinates);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, coordinates));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Copy coordinates")));


        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.spigot().sendMessage(message);
        }

        JSONObject postData = new JSONObject();
        postData.put("content", location + " coordinates are: **" + dimension + "** `" + coordinates + "`");
        postData.put("username", sender.getName());
        postData.put("avatar_url", "https://mc-heads.net/avatar/" + sender.getName());

        sendWH(postData, _config.chatWebhookUrl);
        return true;
    }
}
