package com.glektarssza.expandedgamerules;

import net.minecraft.world.level.GameRules;
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
        return GameRules.register(name, category, defaultValue);
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
}
