package com.boy0000.blocksounds;

import org.bukkit.event.Listener;

public class CustomFurnitureListener implements Listener {
    // ItemsAdder plays this by default, so we don't need to play it again
    /*@EventHandler
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
    }*/
}
