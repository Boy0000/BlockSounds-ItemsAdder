package com.boy0000.blocksounds;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.ItemsAdder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class BlockSoundConfig {
    private static HashMap<String, BlockSounds> customSounds = new HashMap<>();

    public static void loadConfig() {
        Set<ConfigurationSection> iaBlocks = ItemsAdder.getAllItems().stream()
                .map(CustomStack::getNamespacedID).filter(id -> CustomBlock.isInRegistry(id) || CustomFurniture.isInRegistry(id))
                .map(s -> CustomStack.getInstance(s).getConfig().getConfigurationSection("items").getConfigurationSection(s.split(":")[1]))
                .filter(c -> c.isConfigurationSection("specific_properties.block.sound") || c.isConfigurationSection("behaviours.furniture.sound")).filter(Objects::nonNull)
                .collect(Collectors.toSet());

        customSounds.clear();
        iaBlocks.forEach(key -> customSounds.put(
                getNamespace(key) + ":" + key.getName(),
                new BlockSounds(key.isConfigurationSection("specific_properties.block.sound")
                        ? key.getConfigurationSection("specific_properties.block.sound")
                        : key.getConfigurationSection("behaviours.furniture.sound")))
        );
        // use soundgroup for this so that it can be used on all blocks we replace sounds for
        customSounds.put(Material.STONE.createBlockData().getSoundGroup().toString(), new BlockSounds("stone"));
        customSounds.put(Material.OAK_LOG.createBlockData().getSoundGroup().toString(), new BlockSounds("wood"));
    }

    public static Map<String, BlockSounds> getBlockSounds() {
        return customSounds;
    }

    private static String getNamespace(ConfigurationSection section) {
        ConfigurationSection root = section.getRoot();
        if (root == null) return "minecraft";
        ConfigurationSection info = root.getConfigurationSection("info");
        if (info == null) return "minecraft";
        return info.getString("namespace", "minecraft");
    }
}
