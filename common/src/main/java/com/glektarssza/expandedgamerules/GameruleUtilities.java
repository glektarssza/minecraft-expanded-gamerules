package com.glektarssza.expandedgamerules;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.GameRules.Type;
import net.minecraft.world.level.GameRules.Value;

import com.glektarssza.expandedgamerules.api.v1.gamerule.CustomCategory;

/**
 * A collection of utilities for interacting with Minecraft's gamerules.
 */
public final class GameruleUtilities {
    /**
     * Register a new gamerule.
     *
     * @param name The name of the gamerule to register. Must be unique.
     * @param category The category to put the gamerule under.
     * @param defaultValue The value to set the gamerule to by default.
     *
     * @return A key that can be used to access the gamerule.
     */
    private static final <T extends Value<T>> Key<T> register(String name,
        Category category, Type<T> defaultValue) {
        return Constants.GAMERULE_REGISTRY.register(name, category,
            defaultValue);
    }

    /**
     * Register a new gamerule.
     *
     * @param name The name of the gamerule to register. Must be unique.
     * @param category The category to put the gamerule under.
     * @param defaultValue The value to set the gamerule to by default.
     *
     * @return A key that can be used to access the gamerule.
     */
    private static final <T extends Value<T>> Key<T> register(String name,
        CustomCategory category, Type<T> defaultValue) {
        return Constants.GAMERULE_REGISTRY.register(name, category,
            defaultValue);
    }

    /**
     * Register a new gamerule.
     *
     * @param name The name of the gamerule to register. Must be unique.
     * @param category The category to put the gamerule under.
     * @param defaultValue The value to set the gamerule to by default.
     *
     * @return A key that can be used to access the gamerule.
     */
    public static final Key<BooleanValue> register(String name,
        Category category, boolean defaultValue) {
        return register(name, category, BooleanValue.create(defaultValue));
    }

    /**
     * Register a new gamerule.
     *
     * @param name The name of the gamerule to register. Must be unique.
     * @param category The category to put the gamerule under.
     * @param defaultValue The value to set the gamerule to by default.
     *
     * @return A key that can be used to access the gamerule.
     */
    public static final Key<BooleanValue> register(String name,
        CustomCategory category, boolean defaultValue) {
        return register(name, category, BooleanValue.create(defaultValue));
    }

    /**
     * Register a new gamerule.
     *
     * @param name The name of the gamerule to register. Must be unique.
     * @param category The category to put the gamerule under.
     * @param defaultValue The value to set the gamerule to by default.
     *
     * @return A key that can be used to access the gamerule.
     */
    public static final Key<IntegerValue> register(String name,
        Category category, int defaultValue) {
        return register(name, category, IntegerValue.create(defaultValue));
    }

    /**
     * Register a new gamerule.
     *
     * @param name The name of the gamerule to register. Must be unique.
     * @param category The category to put the gamerule under.
     * @param defaultValue The value to set the gamerule to by default.
     *
     * @return A key that can be used to access the gamerule.
     */
    public static final Key<IntegerValue> register(String name,
        CustomCategory category, int defaultValue) {
        return register(name, category, IntegerValue.create(defaultValue));
    }

    /**
     * Get the boolean value associated with a gamerule.
     *
     * @param level The level to retrieve the gamerule value from.
     * @param key The key to retrieve the gamerule for.
     *
     * @return The boolean value for the gamerule.
     */
    public static final boolean getBooleanGamerule(Level level,
        Key<BooleanValue> key) {
        return Constants.GAMERULE_REGISTRY.getBooleanGamerule(level,
            key.getId());
    }

    /**
     * Get the boolean value associated with a gamerule.
     *
     * @param level The level to retrieve the gamerule value from.
     * @param id The id to retrieve the gamerule for.
     *
     * @return The boolean value for the gamerule.
     */
    public static final boolean getBooleanGamerule(Level level, String id) {
        return Constants.GAMERULE_REGISTRY.getBooleanGamerule(level, id);
    }

    /**
     * Get the integer value associated with a gamerule.
     *
     * @param level The level to retrieve the gamerule value from.
     * @param key The key to retrieve the gamerule for.
     *
     * @return The integer value for the gamerule.
     */
    public static final int getIntegerGamerule(Level level,
        Key<IntegerValue> key) {
        return Constants.GAMERULE_REGISTRY.getIntegerGamerule(level,
            key.getId());
    }

    /**
     * Get the integer value associated with a gamerule.
     *
     * @param level The level to retrieve the gamerule value from.
     * @param id The id to retrieve the gamerule for.
     *
     * @return The integer value for the gamerule.
     */
    public static final int getIntegerGamerule(Level level, String id) {
        return Constants.GAMERULE_REGISTRY.getIntegerGamerule(level, id);
    }
}
