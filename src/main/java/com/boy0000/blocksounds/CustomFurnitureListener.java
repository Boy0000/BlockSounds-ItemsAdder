package com.boy0000.blocksounds;

import dev.lone.itemsadder.api.CustomFurniture;
import dev.lone.itemsadder.api.Events.FurnitureBreakEvent;
import dev.lone.itemsadder.api.Events.FurniturePlaceSuccessEvent;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomFurnitureListener implements Listener {

    @EventHandler
    public void onFurniturePlace(FurniturePlaceSuccessEvent event) {
        CustomFurniture furniture = event.getFurniture();
        if (furniture == null) return;
        Entity armorStand = furniture.getArmorstand();
        if (armorStand == null || !armorStand.getLocation().isWorldLoaded()) return;
        BlockSounds blockSound = BlockSoundConfig.customSounds.get(furniture.getNamespacedID());
        if (blockSound == null || !blockSound.hasPlaceSound()) return;
        armorStand.getWorld().playSound(armorStand.getLocation(), blockSound.getPlaceSound(), SoundCategory.BLOCKS, blockSound.getPlaceVolume(), blockSound.getPlacePitch());
    }

    @EventHandler
    public void onFurnitureBreak(FurnitureBreakEvent event) {
        CustomFurniture furniture = event.getFurniture();
        if (furniture == null) return;
        Entity armorStand = furniture.getArmorstand();
        if (armorStand == null || !armorStand.getLocation().isWorldLoaded()) return;
        BlockSounds blockSound = BlockSoundConfig.customSounds.get(furniture.getNamespacedID());
        if (blockSound == null || !blockSound.hasBreakSound()) return;

        armorStand.getWorld().playSound(armorStand.getLocation(), blockSound.getBreakSound(), SoundCategory.BLOCKS, blockSound.getBreakVolume(), blockSound.getBreakPitch());
    }
}
