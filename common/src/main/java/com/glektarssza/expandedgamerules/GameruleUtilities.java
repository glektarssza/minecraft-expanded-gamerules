package com.glektarssza.expandedgamerules;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.IntegerValue;
import net.minecraft.world.level.GameRules.Category;
import net.minecraft.world.level.GameRules.Key;
import net.minecraft.world.level.GameRules.Type;
import net.minecraft.world.level.GameRules.Value;

/**
 * A collection of utilities for interacting with Minecraft's gamerules.
 */
public final class GameruleUtilities {
    /**
     * A map containing the gamerule keys and their IDs.
     */
    private static final Map<String, Key<?>> ruleIDMap = new HashMap<>();

    /**
     * Register a new gamerule.
     *
     * @param name         The name of the gamerule to register. Must be unique.
     * @param category     The category to put the gamerule under.
     * @param defaultValue The value to set the gamerule to by default.
     *
     * @return A key that can be used to access the gamerule.
     */
    @SuppressWarnings("null")
    private static final <T extends Value<T>> Key<T> register(String name, Category category, Type<T> defaultValue) {
        var key = GameRules.register(name, category, defaultValue);
        ruleIDMap.put(name, key);
        return key;
    }

    /**
     * Register a new gamerule.
     *
     * @param name         The name of the gamerule to register. Must be unique.
     * @param category     The category to put the gamerule under.
     * @param defaultValue The value to set the gamerule to by default.
     *
     * @return A key that can be used to access the gamerule.
     */
    public static final Key<BooleanValue> register(String name, Category category, boolean defaultValue) {
        return register(name, category, BooleanValue.create(defaultValue));
    }

    /**
     * Register a new gamerule.
     *
     * @param name         The name of the gamerule to register. Must be unique.
     * @param category     The category to put the gamerule under.
     * @param defaultValue The value to set the gamerule to by default.
     *
     * @return A key that can be used to access the gamerule.
     */
    public static final Key<IntegerValue> register(String name, Category category, int defaultValue) {
        return register(name, category, IntegerValue.create(defaultValue));
    }

    /**
     * Get the value associated with a gamerule.
     *
     * @param <T>   The type of the value held in the gamerule.
     * @param level The level to retrieve the gamerule value from.
     * @param key   The key to retrieve the gamerule for.
     *
     * @return The value wrapper object for the gamerule.
     */
    @SuppressWarnings("null")
    public static final <T extends Value<T>> T getGamerule(Level level, Key<T> key) {
        return level.getGameRules().getRule(key);
    }

    /**
     * Get the value associated with a gamerule.
     *
     * @param <T>   The type of the value held in the gamerule.
     * @param level The level to retrieve the gamerule value from.
     * @param id    The id to retrieve the gamerule for.
     *
     * @return The value wrapper object for the gamerule.
     */
    @SuppressWarnings("null")
    public static final <T extends Value<T>> T getGamerule(Level level, String id) {
        @SuppressWarnings("unchecked")
        var key = (Key<T>) ruleIDMap.get(id);
        return level.getGameRules().getRule(key);
    }

    /**
     * Get the boolean value associated with a gamerule.
     *
     * @param level The level to retrieve the gamerule value from.
     * @param key   The key to retrieve the gamerule for.
     *
     * @return The boolean value for the gamerule.
     */
    public static final boolean getBooleanGamerule(Level level, Key<BooleanValue> key) {
        return getGamerule(level, key).get();
    }

    /**
     * Get the boolean value associated with a gamerule.
     *
     * @param level The level to retrieve the gamerule value from.
     * @param id    The id to retrieve the gamerule for.
     *
     * @return The boolean value for the gamerule.
     */
    public static final boolean getBooleanGamerule(Level level, String id) {
        return GameruleUtilities.<BooleanValue>getGamerule(level, id).get();
    }

    /**
     * Get the integer value associated with a gamerule.
     *
     * @param level The level to retrieve the gamerule value from.
     * @param key   The key to retrieve the gamerule for.
     *
     * @return The integer value for the gamerule.
     */
    public static final int getIntegerGamerule(Level level, Key<IntegerValue> key) {
        return getGamerule(level, key).get();
    }

    /**
     * Get the integer value associated with a gamerule.
     *
     * @param level The level to retrieve the gamerule value from.
     * @param id    The id to retrieve the gamerule for.
     *
     * @return The integer value for the gamerule.
     */
    public static final int getIntegerGamerule(Level level, String id) {
        return GameruleUtilities.<IntegerValue>getGamerule(level, id).get();
    }
}
