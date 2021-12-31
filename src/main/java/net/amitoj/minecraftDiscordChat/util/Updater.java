package net.amitoj.minecraftDiscordChat.util;

import net.amitoj.minecraftDiscordChat.MinecraftDiscordChat;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

public class Updater {
    private Config _config;
    private JavaPlugin _plugin;
    private String _apiUrl = "https://github.com/amitojsingh366/minecraft-discord-chat/releases/latest";

    public boolean updateAvailable;
    public String[] currentVersion;
    public Float newVersion;
    public String downloadUrl;
    public String newFileName;

    public Updater(MinecraftDiscordChat plugin) {
        this._plugin = plugin;
        this._config = plugin.config;
        checkForUpdates();
        deleteOldFiles();
        if (_config.shouldAutoUpdate && updateAvailable) {
            tryUpdating();
        }
    }

    public void checkForUpdates() {
        String[] current_version = _plugin.getDescription().getVersion().split("\\.");
        setCurrentVersion(current_version);

        try {
            URL url = new URL(_apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();
            if (responsecode == 200) {
                Scanner scanner = new Scanner(url.openStream());
                String response = scanner.useDelimiter("\\Z").next();
                scanner.close();

                JSONParser parser = new JSONParser();
                JSONObject data = (JSONObject) parser.parse(response);

                Float api_version = Float.parseFloat(data.get("tag_name").toString().replace("v", ""));

                if (!api_version.equals(currentVersion)) {
                    setUpdateAvailable(true);
                    setNewVersion(api_version);

                    JSONObject newAsset = (JSONObject) ((JSONArray) data.get("assets")).get(0);
                    setDownloadUrl((String) newAsset.get("browser_download_url"));
                    setNewFileName((String) newAsset.get("name"));

                    _plugin.getLogger().info("Update to version " + api_version + " is available");
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void deleteOldFiles() {
        File folder = new File("./plugins");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().startsWith("minecraftMentions")) {
                    if (file.getName().endsWith(".jar")) {
                        if (!file.getName().endsWith(currentVersion + ".jar")) {
                            String[] ver = file.getName().replace("minecraftMentions-", "")
                                    .replace(".jar", "").split("\\.");

                            if (isCurrentNewer(ver)) {
                                file.delete();
                                _plugin.getLogger().info("Deleted old version: " + file.getName());
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isCurrentNewer(String[] oldVer) {
        if (Integer.parseInt(this.currentVersion[0]) > Integer.parseInt(oldVer[0])) {
            if (Integer.parseInt(this.currentVersion[1]) > Integer.parseInt(oldVer[1])) {
                return Integer.parseInt(this.currentVersion[2]) > Integer.parseInt(oldVer[2]);
            }
        }
        return false;
    }

    public void tryUpdating() {
        if (newVersion != null && downloadUrl != null) {
            _plugin.getLogger().info("Starting download of version " + newVersion);
            URL url = null;
            try {
                url = new URL(downloadUrl);
                InputStream in = url.openStream();
                ReadableByteChannel rbc = Channels.newChannel(in);
                FileOutputStream fos = new FileOutputStream("./plugins/" + newFileName);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                _plugin.getLogger().info("Version " + newVersion + " downloaded, please restart/reload your server to apply the update");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUpdateAvailable(boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }

    public void setCurrentVersion(String[] currentVersion) {
        this.currentVersion = currentVersion;
    }

    public void setNewVersion(Float newVersion) {
        this.newVersion = newVersion;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }
}
