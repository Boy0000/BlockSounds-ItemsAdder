package com.boy0000.blocksounds;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.EnumWrappers;
import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO This doesnt have a repeating scheduler so it will only play sounds ones
public class BlockSoundPackets {
    private final Map<Location, BukkitScheduler> blockBreakers = new HashMap<>();
    private final List<Block> blockWithActiveSound = new ArrayList<>();
    private final PacketAdapter listener = new PacketAdapter(BlockSoundPlugin.plugin, ListenerPriority.LOW, PacketType.Play.Client.BLOCK_DIG) {
        @Override
        public void onPacketReceiving(final PacketEvent event) {
            if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;

            final PacketContainer packet = event.getPacket();
            EnumWrappers.PlayerDigType type;
            try {
                type = packet.getEnumModifier(EnumWrappers.PlayerDigType.class, 2).getValues().get(0);
            } catch (IllegalArgumentException exception) {
                type = EnumWrappers.PlayerDigType.SWAP_HELD_ITEMS;
            }
            final BlockPosition pos = packet.getBlockPositionModifier().getValues().get(0);
            final Block block = event.getPlayer().getWorld().getBlockAt(pos.getX(), pos.getY(), pos.getZ());
            final CustomBlock customBlock = CustomBlock.byAlreadyPlaced(block);
            final Location location = block.getLocation();
            final BukkitScheduler scheduler = Bukkit.getScheduler();

            BlockSounds blockSounds;
            if (customBlock != null) { // Custom Block
                blockSounds = BlockSoundConfig.customSounds.get(customBlock.getNamespacedID());
            } else { // Vanilla Stone/Wood block
                blockSounds = BlockSoundConfig.customSounds.get(block.getBlockData().getSoundGroup().toString());
            }

            if (type == EnumWrappers.PlayerDigType.START_DESTROY_BLOCK) {
                blockWithActiveSound.remove(block);

                if (blockBreakers.containsKey(location)) {
                    blockBreakers.get(location).cancelTasks(BlockSoundPlugin.plugin);
                }

                blockBreakers.put(location, scheduler);
                scheduler.runTaskTimer(BlockSoundPlugin.plugin, () -> {
                    if (!blockWithActiveSound.contains(block)) {
                        blockWithActiveSound.add(block);

                        if (blockSounds != null && blockSounds.hasHitSound()) {
                            block.getWorld().playSound(block.getLocation(), blockSounds.getHitSound(), SoundCategory.BLOCKS, blockSounds.getHitVolume(), blockSounds.getHitPitch());
                        }

                        scheduler.runTaskLater(BlockSoundPlugin.plugin, () -> blockWithActiveSound.remove(block), 3);
                    }
                }, 3L, 4L);
            } else {
                scheduler.cancelTasks(BlockSoundPlugin.plugin);
                blockBreakers.remove(location);
            }
        }
    };

    public void registerListener() {
        BlockSoundPlugin.protocolManager.addPacketListener(listener);
    }

    public void unregisterListener() {
        BlockSoundPlugin.protocolManager.removePacketListener(listener);
    }

}
