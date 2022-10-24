package com.boy0000.blocksounds;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Files;
import java.nio.file.Path;

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
        String oldPath = this.getDataFolder().getAbsolutePath().replace("\\", "/") + "/sounds.json";
        String newPath = this.getDataFolder().getParentFile().getAbsolutePath().replace("\\", "/") + "/ItemsAdder/data/resource_pack/assets/minecraft/sounds.json";
        try {
            Files.copy(Path.of(oldPath), Path.of(newPath));
            logSuccess("Copied sounds.json to ItemsAdder's resource pack!");
        } catch (Exception e) {
            logError("Failed to copy sounds.json to ItemsAdder resource pack!");
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
