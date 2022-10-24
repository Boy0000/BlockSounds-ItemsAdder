package com.boy0000.blocksounds;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.GameEvent;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.SoundGroup;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.GenericGameEvent;

public class VanillaBlockListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        BlockSounds blockSound = BlockSoundConfig.customSounds.get(block.getBlockData().getSoundGroup().toString());
        if (CustomBlock.byAlreadyPlaced(block) != null) return;
        if (blockSound == null || !blockSound.hasPlaceSound()) return;

        block.getWorld().playSound(block.getLocation(), getSound(block, SoundType.PLACE), SoundCategory.BLOCKS, blockSound.getPlaceVolume(), blockSound.getPlacePitch());

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        BlockSounds blockSound = BlockSoundConfig.customSounds.get(block.getBlockData().getSoundGroup().toString());
        if (CustomBlock.byAlreadyPlaced(block) != null) return;
        if (blockSound == null || !blockSound.hasBreakSound()) return;

        block.getWorld().playSound(block.getLocation(), getSound(block, SoundType.BREAK), SoundCategory.BLOCKS, blockSound.getBreakVolume(), blockSound.getBreakPitch());

    }

    // TODO Packet stuff
    /*@EventHandler
    public void onBlockHit(BlockDamageEvent event) {
        Block block = event.getBlock();
        BlockSounds blockSound = CustomBlockConfig.customSounds.get(block.getType().getKey().toString());
        if (CustomBlock.byAlreadyPlaced(block) != null || event.getInstaBreak()) return;
        if (blockSound == null || !blockSound.hasHitSound()) return;

        block.getWorld().playSound(block.getLocation(), getSound(block, SoundType.BREAK), SoundCategory.BLOCKS, blockSound.getBreakVolume(), blockSound.getBreakPitch());
    }*/

    @EventHandler
    public void onBlockStep(GenericGameEvent event) {
        Entity entity = event.getEntity();
        if (event.getEvent() != GameEvent.STEP || entity == null || !event.getLocation().isWorldLoaded()) return;

        Block block = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
        BlockSounds blockSound = BlockSoundConfig.customSounds.get(block.getBlockData().getSoundGroup().toString());
        if (CustomBlock.byAlreadyPlaced(block) != null) return;
        if (blockSound == null || !blockSound.hasStepSound()) return;
        block.getWorld().playSound(block.getLocation(), getSound(block, SoundType.STEP), SoundCategory.PLAYERS, blockSound.getStepVolume(), blockSound.getStepPitch());
    }

    @EventHandler
    public void onBlockFall(GenericGameEvent event) {
        Entity entity = event.getEntity();
        if (event.getEvent() != GameEvent.HIT_GROUND || entity == null || !event.getLocation().isWorldLoaded()) return;

        Block block = entity.getLocation().getBlock().getRelative(BlockFace.DOWN);
        BlockSounds blockSound = BlockSoundConfig.customSounds.get(block.getBlockData().getSoundGroup().toString());
        if (CustomBlock.byAlreadyPlaced(block) != null) return;
        if (blockSound == null || !blockSound.hasFallSound()) return;

        block.getWorld().playSound(block.getLocation(), getSound(block, SoundType.FALL), SoundCategory.PLAYERS, blockSound.getFallVolume(), blockSound.getFallPitch());
    }

    private String getSound(Block block, SoundType type) {
        SoundGroup group = block.getBlockData().getSoundGroup();
        boolean isWood = group == Material.OAK_LOG.createBlockData().getSoundGroup();
        switch (type) {
            case PLACE: return isWood ? BlockSounds.VANILLA_WOOD_PLACE : BlockSounds.VANILLA_STONE_PLACE;
            case BREAK: return isWood ? BlockSounds.VANILLA_WOOD_BREAK : BlockSounds.VANILLA_STONE_BREAK;
            case HIT: return isWood ? BlockSounds.VANILLA_WOOD_HIT : BlockSounds.VANILLA_STONE_HIT;
            case STEP: return isWood ? BlockSounds.VANILLA_WOOD_STEP : BlockSounds.VANILLA_STONE_STEP;
            case FALL: return isWood ? BlockSounds.VANILLA_WOOD_FALL : BlockSounds.VANILLA_STONE_FALL;
            default: return null;
        }
    }

    private enum SoundType {
        PLACE, BREAK, HIT, FALL, STEP
    }
}
