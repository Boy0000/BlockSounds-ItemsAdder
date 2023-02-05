package com.boy0000.blocksounds;

import dev.lone.itemsadder.api.CustomFurniture;
import org.bukkit.GameEvent;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.GenericGameEvent;

public class CustomFurnitureListener implements Listener {

    @EventHandler
    public void onCustomFurnitureStep(GenericGameEvent event) {
        Entity entity = event.getEntity();
        GameEvent gameEvent = event.getEvent();
        if (gameEvent != GameEvent.HIT_GROUND && gameEvent != GameEvent.STEP) return;
        if (entity == null || !event.getLocation().isWorldLoaded()) return;
        if (!(entity instanceof LivingEntity)) return;
        if (entity instanceof Player player && player.isSneaking()) return;

        Block block = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
        CustomFurniture customFurniture = getFurnitureFromHitbox(block);
        if (customFurniture == null) return;

        BlockSounds blockSound = BlockSoundConfig.getBlockSounds().get(customFurniture.getNamespacedID());
        if (blockSound == null) return;
        if (gameEvent == GameEvent.STEP && !blockSound.hasStepSound()) return;
        if (gameEvent == GameEvent.HIT_GROUND && !blockSound.hasFallSound()) return;

        String sound = gameEvent == GameEvent.STEP ? blockSound.getStepSound() : blockSound.getFallSound();
        float volume = gameEvent == GameEvent.STEP ? blockSound.getStepVolume() : blockSound.getFallVolume();
        float pitch = gameEvent == GameEvent.STEP ? blockSound.getStepPitch() : blockSound.getFallPitch();

        block.getWorld().playSound(block.getLocation(), sound, SoundCategory.PLAYERS, volume, pitch);
    }

    public static CustomFurniture getFurnitureFromHitbox(Block block) {
        for (Entity e : block.getWorld().getNearbyEntities(block.getBoundingBox())) {
            if (CustomFurniture.byAlreadySpawned(e) != null) {
                return CustomFurniture.byAlreadySpawned(e);
            }
        }
        return null;
    }
}
