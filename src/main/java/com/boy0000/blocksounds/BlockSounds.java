package com.boy0000.blocksounds;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BlockSounds {
    public static String VANILLA_STONE_PLACE = "minecraft:required.stone.place";
    public static String VANILLA_STONE_BREAK = "minecraft:required.stone.break";
    public static String VANILLA_STONE_HIT = "minecraft:required.stone.hit";
    public static String VANILLA_STONE_STEP = "minecraft:required.stone.step";
    public static String VANILLA_STONE_FALL = "minecraft:required.stone.fall";

    public static String VANILLA_WOOD_PLACE = "minecraft:required.wood.place";
    public static String VANILLA_WOOD_BREAK = "minecraft:required.wood.break";
    public static String VANILLA_WOOD_HIT = "minecraft:required.wood.hit";
    public static String VANILLA_WOOD_STEP = "minecraft:required.wood.step";
    public static String VANILLA_WOOD_FALL = "minecraft:required.wood.fall";

    public static float VANILLA_PLACE_VOLUME = 1.0f;
    public static float VANILLA_PLACE_PITCH = 0.8f;
    public static float VANILLA_BREAK_VOLUME = 1.0f;
    public static float VANILLA_BREAK_PITCH = 0.8f;
    public static float VANILLA_HIT_VOLUME = 0.25f;
    public static float VANILLA_HIT_PITCH = 0.5f;
    public static float VANILLA_STEP_VOLUME = 0.15f;
    public static float VANILLA_STEP_PITCH = 1.0f;
    public static float VANILLA_FALL_VOLUME = 0.5f;
    public static float VANILLA_FALL_PITCH = 0.75f;

    private final String placeSound;
    private final String breakSound;
    private final String stepSound;
    private final String hitSound;
    private final String fallSound;
    private final float placeVolume;
    private final float breakVolume;
    private final float stepVolume;
    private final float hitVolume;
    private final float fallVolume;
    private final float placePitch;
    private final float breakPitch;
    private final float stepPitch;
    private final float hitPitch;
    private final float fallPitch;

    // For registering vanilla wood and stone type blocks
    public BlockSounds(String type) {
        if (type.equals("stone")) {
            placeSound = VANILLA_STONE_PLACE;
            breakSound = VANILLA_STONE_BREAK;
            hitSound = VANILLA_STONE_STEP;
            stepSound = VANILLA_STONE_HIT;
            fallSound = VANILLA_STONE_FALL;
        } else if (type.equals("wood")) {
            placeSound = VANILLA_WOOD_PLACE;
            breakSound = VANILLA_WOOD_BREAK;
            hitSound = VANILLA_WOOD_STEP;
            stepSound = VANILLA_WOOD_HIT;
            fallSound = VANILLA_WOOD_FALL;
        } else {
            placeSound = null;
            breakSound = null;
            hitSound = null;
            stepSound = null;
            fallSound = null;
        }

        placeVolume = VANILLA_PLACE_VOLUME;
        breakVolume = VANILLA_BREAK_VOLUME;
        hitVolume = VANILLA_HIT_VOLUME;
        stepVolume = VANILLA_STEP_VOLUME;
        fallVolume = VANILLA_FALL_VOLUME;

        placePitch = VANILLA_PLACE_PITCH;
        breakPitch = VANILLA_BREAK_PITCH;
        hitPitch = VANILLA_HIT_PITCH;
        stepPitch = VANILLA_STEP_PITCH;
        fallPitch = VANILLA_FALL_PITCH;
    }

    public BlockSounds(ConfigurationSection section) {
        placeSound = getSound(section, "place");
        breakSound = getSound(section, "break");
        stepSound = getSound(section, "step");
        hitSound = getSound(section, "hit");
        fallSound = getSound(section, "fall");

        placeVolume = getVolume(section, "place", VANILLA_PLACE_VOLUME);
        breakVolume = getVolume(section, "break", VANILLA_BREAK_VOLUME);
        stepVolume = getVolume(section, "step", VANILLA_STEP_VOLUME);
        hitVolume = getVolume(section, "hit", VANILLA_HIT_VOLUME);
        fallVolume = getVolume(section, "fall", VANILLA_FALL_VOLUME);

        placePitch = getPitch(section, "place", VANILLA_PLACE_PITCH);
        breakPitch = getPitch(section, "break", VANILLA_BREAK_PITCH);
        stepPitch = getPitch(section, "step", VANILLA_STEP_PITCH);
        hitPitch = getPitch(section, "hit", VANILLA_HIT_PITCH);
        fallPitch = getPitch(section, "fall", VANILLA_FALL_PITCH);
    }

    private String getSound(ConfigurationSection section, String key) {
        if (section == null) return null;
        ConfigurationSection soundSection = section.getConfigurationSection(key);
        String sound = soundSection != null && soundSection.isString("name") ? soundSection.getString("name") : null;
        // In-case people use ENUMS this should work
        if (Arrays.stream(Sound.values()).map(s -> s.getKey().toString()).collect(Collectors.toList()).contains(sound))
            sound = Sound.valueOf(sound).getKey().toString();
        return sound;
    }

    private float getVolume(ConfigurationSection section, String type, float defaultValue) {
        if (section == null) return defaultValue;
        ConfigurationSection soundSection = section.getConfigurationSection(type);
        return soundSection == null ? (float) section.getDouble("volume", defaultValue) : defaultValue;
    }

    private float getPitch(ConfigurationSection section, String type, float defaultValue) {
        if (section == null) return defaultValue;
        ConfigurationSection soundSection = section.getConfigurationSection(type);
        return soundSection != null ? (float) soundSection.getDouble("pitch", defaultValue) : defaultValue;
    }

    public boolean hasPlaceSound() {
        return placeSound != null;
    }

    public String getPlaceSound() {
        return validateSound(placeSound);
    }

    public boolean hasBreakSound() {
        return breakSound != null;
    }

    public String getBreakSound() {
        return validateSound(breakSound);
    }

    public boolean hasStepSound() {
        return stepSound != null;
    }

    public String getStepSound() {
        return validateSound(stepSound);
    }

    public boolean hasHitSound() {
        return hitSound != null;
    }

    public String getHitSound() {
        return validateSound(hitSound);
    }

    public boolean hasFallSound() {
        return fallSound != null;
    }

    public String getFallSound() {
        return validateSound(fallSound);
    }

    public float getPlaceVolume() {
        return placeVolume;
    }

    public float getBreakVolume() {
        return breakVolume;
    }

    public float getStepVolume() {
        return stepVolume;
    }

    public float getHitVolume() {
        return hitVolume;
    }

    public float getFallVolume() {
        return fallVolume;
    }

    public float getPlacePitch() {
        return placePitch;
    }

    public float getBreakPitch() {
        return breakPitch;
    }

    public float getStepPitch() {
        return stepPitch;
    }

    public float getHitPitch() {
        return hitPitch;
    }

    public float getFallPitch() {
        return fallPitch;
    }

    private static String validateSound(String sound) {
        return sound != null ? sound.replace("block.wood", "required.wood").replace("block.stone", "required.stone") : null;
    }
}
