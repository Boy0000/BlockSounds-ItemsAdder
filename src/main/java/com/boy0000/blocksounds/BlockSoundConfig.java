package com.boy0000.blocksounds;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Objects;

public class BlockSoundConfig {
    public static HashMap<String, BlockSounds> customSounds = new HashMap<>();

    public static void loadConfig() {
        /*Set<ConfigurationSection> configs = ItemsAdder.getAllItems().stream().filter(i -> CustomBlock.byItemStack(i.getItemStack()) != null).map(s -> s.getConfig().getDefaultSection()).collect(Collectors.toSet());
        configs.forEach(c -> c.getKeys(false).forEach(Bukkit::broadcastMessage));*/
        ConfigurationSection section = BlockSoundPlugin.plugin.getConfig();
        customSounds.clear();
        section.getKeys(false).stream().filter(Objects::nonNull).forEach(key ->
                customSounds.put(key, new BlockSounds(section.getConfigurationSection(key))));
        // use soundgroup for this so that it can be used on all blocks we replace sounds for
        customSounds.put(Material.STONE.createBlockData().getSoundGroup().toString(), new BlockSounds("stone"));
        customSounds.put(Material.OAK_LOG.createBlockData().getSoundGroup().toString(), new BlockSounds("wood"));
    }
}
