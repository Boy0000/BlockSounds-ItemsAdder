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
        GameEvent gameEvent = event.getEvent();
        if (gameEvent != GameEvent.HIT_GROUND && gameEvent != GameEvent.STEP) return;
        if (entity == null || !event.getLocation().isWorldLoaded()) return;
        if (!(entity instanceof LivingEntity)) return;
        if (entity instanceof Player player && player.isSneaking()) return;

        Block currentBlock = entity.getLocation().getBlock();
        Block block = currentBlock.getType().isAir() ? currentBlock.getRelative(BlockFace.DOWN) : currentBlock;
        CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);
        if (customBlock == null) return;

        BlockSounds blockSound = BlockSoundConfig.getBlockSounds().get(customBlock.getNamespacedID());
        if (blockSound == null) return;
        if (gameEvent == GameEvent.STEP && !blockSound.hasStepSound()) return;
        if (gameEvent == GameEvent.HIT_GROUND && !blockSound.hasFallSound()) return;

        String sound = gameEvent == GameEvent.STEP ? blockSound.getStepSound() : blockSound.getFallSound();
        float volume = gameEvent == GameEvent.STEP ? blockSound.getStepVolume() : blockSound.getFallVolume();
        float pitch = gameEvent == GameEvent.STEP ? blockSound.getStepPitch() : blockSound.getFallPitch();

        block.getWorld().playSound(block.getLocation(), sound, SoundCategory.PLAYERS, volume, pitch);
    }
}
