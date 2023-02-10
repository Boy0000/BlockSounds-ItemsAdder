package com.boy0000.blocksounds;

import dev.lone.itemsadder.api.CustomBlock;
import dev.lone.itemsadder.api.CustomFurniture;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.GenericGameEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class VanillaBlockListener implements Listener {

    @EventHandler // Since this is fired by ItemsAdder twice, make sure held item is CustomBlock
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack itemStack = event.getHand() == EquipmentSlot.HAND ? event.getPlayer().getInventory().getItemInMainHand() : event.getPlayer().getInventory().getItemInOffHand();
        Block block = event.getBlockPlaced();

        if (CustomFurniture.byItemStack(itemStack) != null) return;
        if (CustomBlock.byItemStack(itemStack) != null) return;
        if (CustomBlock.byAlreadyPlaced(block) != null) return;

        BlockSounds blockSound = BlockSoundConfig.getBlockSounds().get(block.getBlockData().getSoundGroup().toString());
        if (blockSound == null || !blockSound.hasPlaceSound()) return;

        block.getWorld().playSound(block.getLocation(), blockSound.getPlaceSound(), SoundCategory.BLOCKS, blockSound.getPlaceVolume(), blockSound.getPlacePitch());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        BlockSounds blockSound = BlockSoundConfig.getBlockSounds().get(block.getBlockData().getSoundGroup().toString());
        if (CustomBlock.byAlreadyPlaced(block) != null) return;
        if (blockSound == null || !blockSound.hasBreakSound()) return;

        block.getWorld().playSound(block.getLocation(), blockSound.getBreakSound(), SoundCategory.BLOCKS, blockSound.getBreakVolume(), blockSound.getBreakPitch());

    }

    @EventHandler
    public void onBlockStep(GenericGameEvent event) {
        Entity entity = event.getEntity();
        GameEvent gameEvent = event.getEvent();
        if (gameEvent != GameEvent.HIT_GROUND && gameEvent != GameEvent.STEP) return;
        if (entity == null || !event.getLocation().isWorldLoaded()) return;
        if (!(entity instanceof LivingEntity)) return;
        if (entity instanceof Player player && player.isSneaking()) return;

        Block currentBlock = entity.getLocation().getBlock();
        Block block = currentBlock.getType().isAir() ? currentBlock.getRelative(BlockFace.DOWN) : currentBlock;
        if (CustomBlock.byAlreadyPlaced(block) != null) return;

        BlockSounds blockSound = BlockSoundConfig.getBlockSounds().get(block.getBlockData().getSoundGroup().toString());
        if (blockSound == null) return;
        if (gameEvent == GameEvent.STEP && !blockSound.hasStepSound()) return;
        if (gameEvent == GameEvent.HIT_GROUND && !blockSound.hasFallSound()) return;

        String sound = gameEvent == GameEvent.STEP ? blockSound.getStepSound() : blockSound.getFallSound();
        float volume = gameEvent == GameEvent.STEP ? blockSound.getStepVolume() : blockSound.getFallVolume();
        float pitch = gameEvent == GameEvent.STEP ? blockSound.getStepPitch() : blockSound.getFallPitch();

        block.getWorld().playSound(block.getLocation(), sound, SoundCategory.PLAYERS, volume, pitch);
    }
}
