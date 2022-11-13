package com.boy0000.blocksounds;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

public final class BlockSoundPlugin extends JavaPlugin {
    public static BlockSoundPlugin plugin;
    public static ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        Bukkit.getPluginManager().registerEvents(new ItemsAdderLoadCheck(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        new BlockSoundPackets().unregisterListener();
    }

    void extractSoundJson() {
        saveResource("sounds.json", true);
        Path oldPath = Path.of(this.getDataFolder().getAbsolutePath().replace("\\", "/") + "/sounds.json");
        Path newPath = Path.of(this.getDataFolder().getParentFile().getAbsolutePath().replace("\\", "/") + "/ItemsAdder/data/resource_pack/assets/minecraft/sounds.json");
        File newFile = newPath.toFile();
        if (newFile.exists()) {
            try (JsonReader oldReader = new JsonReader(Files.newBufferedReader(oldPath)); JsonReader newReader = new JsonReader(Files.newBufferedReader(newFile.toPath()))) {
                JsonParser parser = new JsonParser();
                JsonObject oldJson = parser.parse(oldReader).getAsJsonObject();
                JsonObject newJson = parser.parse(newReader).getAsJsonObject();
                for (String key : oldJson.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toSet())) {
                    if (!newJson.has(key)) {
                        newJson.add(key, oldJson.get(key));
                    }
                }
                Files.writeString(newPath, newJson.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.copy(oldPath, newPath);
                logSuccess("Copied sounds.json to ItemsAdder's resource pack!");
            } catch (Exception e) {
                logError("Failed to copy sounds.json to ItemsAdder resource pack!");
            }
        }
    }

    void logSuccess(String message) {
        Bukkit.getConsoleSender().sendMessage("[BlockSounds - ItemsAdder] " + ChatColor.GREEN + message);
    }

    void logError(String message) {
        Bukkit.getConsoleSender().sendMessage("[BlockSounds - ItemsAdder] " + ChatColor.RED + message);
    }

    void logWarn(String message) {
        Bukkit.getConsoleSender().sendMessage("[BlockSounds - ItemsAdder] " + ChatColor.YELLOW + message);
    }
}
