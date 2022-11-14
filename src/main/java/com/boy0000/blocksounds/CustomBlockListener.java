package com.boy0000.blocksounds;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.GameEvent;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.world.GenericGameEvent;

public class CustomBlockListener implements Listener {
    // ItemsAdder plays this by default, so we don't need to play it again
    /*@EventHandler
    public void onCustomBlockPlace(CustomBlockPlaceEvent event) {
        BlockSounds blockSound = BlockSoundConfig.customSounds.get(event.getNamespacedID());
        if (blockSound == null || !blockSound.hasPlaceSound()) return;

        event.getBlock().getWorld().playSound(event.getBlock().getLocation(), blockSound.getPlaceSound(), SoundCategory.BLOCKS, blockSound.getPlaceVolume(), blockSound.getPlacePitch());
    }

    @EventHandler
    public void onCustomBlockBreak(CustomBlockBreakEvent event) {
        BlockSounds blockSound = BlockSoundConfig.customSounds.get(event.getNamespacedID());
        if (blockSound == null || !blockSound.hasBreakSound()) return;

        event.getBlock().getWorld().playSound(event.getBlock().getLocation(), blockSound.getBreakSound(), SoundCategory.BLOCKS, blockSound.getBreakVolume(), blockSound.getBreakPitch());
    }*/

    @EventHandler
    public void onCustomBlockStep(GenericGameEvent event) {
        Entity entity = event.getEntity();
        if (event.getEvent() != GameEvent.STEP || entity == null || !event.getLocation().isWorldLoaded()) return;
        if (!(entity instanceof LivingEntity)) return;

        Block block = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
        CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);
        if (customBlock == null) return;

        BlockSounds blockSound = BlockSoundConfig.customSounds.get(customBlock.getNamespacedID());
        if (blockSound == null || !blockSound.hasStepSound()) return;

        block.getWorld().playSound(block.getLocation(), blockSound.getStepSound(), SoundCategory.PLAYERS, blockSound.getStepVolume(), blockSound.getStepPitch());
    }

    @EventHandler
    public void onCustomBlockFall(GenericGameEvent event) {
        Entity entity = event.getEntity();
        if (event.getEvent() != GameEvent.HIT_GROUND || entity == null || !event.getLocation().isWorldLoaded()) return;
        EntityDamageEvent cause = entity.getLastDamageCause();
        if (!(entity instanceof LivingEntity) || cause == null || cause.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        Block block = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
        CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);
        if (customBlock == null) return;

        BlockSounds blockSound = BlockSoundConfig.customSounds.get(customBlock.getNamespacedID());
        if (blockSound == null || !blockSound.hasFallSound()) return;

        block.getWorld().playSound(block.getLocation(), blockSound.getFallSound(), SoundCategory.PLAYERS, blockSound.getFallVolume(), blockSound.getFallPitch());
    }
}
