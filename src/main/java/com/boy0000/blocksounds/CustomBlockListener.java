package com.boy0000.blocksounds;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.GameEvent;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.world.GenericGameEvent;


public class CustomBlockListener implements Listener {

    @EventHandler
    public void onCustomBlockStep(GenericGameEvent event) {
        Entity entity = event.getEntity();
        if (event.getEvent() != GameEvent.STEP || entity == null || !event.getLocation().isWorldLoaded()) return;
        if (!(entity instanceof LivingEntity)) return;
        if (entity instanceof Player player && player.isSneaking()) return;

        Block block = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
        CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);
        if (customBlock == null) return;

        BlockSounds blockSound = BlockSoundConfig.getBlockSounds().get(customBlock.getNamespacedID());
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

        BlockSounds blockSound = BlockSoundConfig.getBlockSounds().get(customBlock.getNamespacedID());
        if (blockSound == null || !blockSound.hasFallSound()) return;

        block.getWorld().playSound(block.getLocation(), blockSound.getFallSound(), SoundCategory.PLAYERS, blockSound.getFallVolume(), blockSound.getFallPitch());
    }
}
