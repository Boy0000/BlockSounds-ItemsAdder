package com.boy0000.blocksounds;

import dev.lone.itemsadder.api.CustomFurniture;
import org.bukkit.GameEvent;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.GenericGameEvent;

public class CustomFurnitureListener implements Listener {

    public CustomFurnitureListener() {
    }

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

    @EventHandler
    public void onCustomFurnitureStep(GenericGameEvent event) {
        Entity entity = event.getEntity();
        if (event.getEvent() != GameEvent.STEP || entity == null || !event.getLocation().isWorldLoaded()) return;

        Block block = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
        CustomFurniture customFurniture = getFurnitureFromHitbox(block);
        if (customFurniture == null) return;

        BlockSounds blockSound = BlockSoundConfig.customSounds.get(customFurniture.getNamespacedID());
        if (blockSound == null || !blockSound.hasStepSound()) return;

        block.getWorld().playSound(block.getLocation(), blockSound.getStepSound(), SoundCategory.PLAYERS, blockSound.getStepVolume(), blockSound.getStepPitch());
    }

    @EventHandler
    public void onCustomFurnitureFall(GenericGameEvent event) {
        Entity entity = event.getEntity();
        if (event.getEvent() != GameEvent.HIT_GROUND || entity == null || !event.getLocation().isWorldLoaded()) return;

        Block block = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
        CustomFurniture customFurniture = getFurnitureFromHitbox(block);
        if (customFurniture == null) return;

        BlockSounds blockSound = BlockSoundConfig.customSounds.get(customFurniture.getNamespacedID());
        if (blockSound == null || !blockSound.hasFallSound()) return;

        block.getWorld().playSound(block.getLocation(), blockSound.getFallSound(), SoundCategory.PLAYERS, blockSound.getFallVolume(), blockSound.getFallPitch());
    }

    private CustomFurniture getFurnitureFromHitbox(Block block) {
        for (Entity e : block.getWorld().getNearbyEntities(block.getBoundingBox())) {
            if (CustomFurniture.byAlreadySpawned(e) != null) {
                return CustomFurniture.byAlreadySpawned(e);
            }
        }
        return null;
    }
}
