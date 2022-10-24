package com.boy0000.blocksounds;

import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemsAdderLoadCheck implements Listener {

    @EventHandler
    public void onItemsAdderLoad(ItemsAdderLoadDataEvent event) {
        if (event.getCause() == ItemsAdderLoadDataEvent.Cause.FIRST_LOAD) {
            BlockSoundPlugin.plugin.saveDefaultConfig();
            BlockSoundConfig.loadConfig();
            BlockSoundPlugin.plugin.extractSoundJson();

            Bukkit.getPluginManager().registerEvents(new CustomBlockSoundListener(), BlockSoundPlugin.plugin);
            Bukkit.getPluginManager().registerEvents(new VanillaBlockListener(), BlockSoundPlugin.plugin);
            new BlockSoundPackets().registerListener();
            BlockSoundPlugin.plugin.logSuccess("BlockSounds has been enabled!");
        } else {
            BlockSoundConfig.loadConfig();
            BlockSoundPlugin.plugin.logSuccess("BlockSounds has been reloaded!");
        }
    }
}
